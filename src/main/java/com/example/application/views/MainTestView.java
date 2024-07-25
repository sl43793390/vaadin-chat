package com.example.application.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.component.util.ComFactory;
import com.example.application.component.util.ComponentUtil;
import com.example.application.component.util.UploadExamplesI18N;
import com.example.entity.Emoji;
import com.example.entity.GroupMessage;
import com.example.entity.UserFriend;
import com.example.entity.UserMessage;
import com.example.service.UserFriendProcess;
import com.example.service.UserMessageProcess;
import com.example.util.MinioUtils;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.FinishedEvent;
import com.vaadin.flow.component.upload.MultiFileReceiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
@PageTitle("Main")
@Route(value = "chat")
public class MainTestView extends VerticalLayout implements MultiFileReceiver,ComponentEventListener<FinishedEvent>{

	
	private static final Logger log = LoggerFactory.getLogger(MainTestView.class);

    private static final long serialVersionUID = 817463156628734901L;
    private UserFriendProcess userFriendProcess;
    private UserMessageProcess userMessageProcess;
//    private MinioTemplate minioTemplate;
	private HorizontalLayout chatLayout;
	private VerticalLayout messageLayout;
	private VerticalLayout inputLayout;
	private TextArea textArea;
	protected String currentSelectedUserId;
	private Scroller sc;
	private FeederThread thread;
	private VerticalLayout userListLayout;
	public static Long currentMaxMessageId;

	private static Long currentMaxGroupMessageId;
	private String fileName;

	private String saveDir = ResourceBundle.getBundle("application").getString("upload.custom.dir");

	private File fileUploaded;

	private Upload upload;

	private Dialog dialog;

	protected boolean groupFlag = false;
	
    public MainTestView(@Autowired UserFriendProcess userFriendProcess, @Autowired UserMessageProcess userMessageProcess) {
    	this.userFriendProcess = userFriendProcess;
    	this.userMessageProcess = userMessageProcess;
//    	this.minioTemplate = minioTemplate;
    	if (null == ComponentUtil.getCurrentUser()) {
    		return;
    	}
    	setMargin(true);
    	setDefaultHorizontalComponentAlignment(Alignment.START);
    	
    	Header header = new Header();
        add(header);
        
        chatLayout = ComFactory.getResponsiveHorizontalLayoutLeft();
        chatLayout.addClassName("main-chat-layout");
        add(chatLayout);
        addClassName("main-chat-backgroud");
        //加载左侧列表
        addleftUserListLayout();
        //加载右侧窗口
        addRightChatLayout();
//        加载最大ID
        currentMaxMessageId = userMessageProcess.getMaxMessageId(ComponentUtil.getCurrentUser().getUserId());
        currentMaxGroupMessageId = userMessageProcess.getCurrentMaxGroupMessageId(ComponentUtil.getCurrentUser().getUserId());
    }

    /**
     * 左侧用户列表展示
     */
    private void addleftUserListLayout() {
    	userListLayout = ComFactory.getVerticalLayout(JustifyContentMode.START);
    	chatLayout.add(userListLayout);
    	userListLayout.addClassName("main-chat-listlayout");
    	
    	List<UserFriend> userFriendById = userFriendProcess.getUserFriendById(ComponentUtil.getCurrentUser().getUserId());
    	//添加用户好友列表
    	for (UserFriend userFriend : userFriendById) {
    		Button btn = ComFactory.getTertriayBtn(userFriendProcess.getUserById(userFriend.getFriendId()).getUserName());
    		btn.addClassName("main-left-user-btn");
    		btn.setIcon(new Icon(VaadinIcon.USER));
    		btn.setId(userFriend.getFriendId());
    		btn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
				
				private static final long serialVersionUID = -7908366682546180586L;

				@Override
				public void onComponentEvent(ClickEvent<Button> event) {
					groupFlag  = false;
					currentSelectedUserId = userFriend.getFriendId();
					updateMessageLayout(currentSelectedUserId);
					//移除所有颜色
					List<Component> list = userListLayout.getChildren().toList();
					for (Component btn2 : list) {
						Button bt = (Button)btn2;
						bt.removeThemeVariants(ButtonVariant.LUMO_SUCCESS);
					}
					btn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
					userFriend.setDtUpdate(new Date());
					userFriendProcess.updateUserFriend(userFriend);
				}
			});
    		userListLayout.add(btn);
		}
    	//添加用户加入的组
    	List<String> groupByUserId = userMessageProcess.getGroupByUserId(ComponentUtil.getCurrentUser().getUserId());
    	if (CollectionUtil.isNotEmpty(groupByUserId)) {
    		for (String groupId : groupByUserId) {
    			Button btn = ComFactory.getTertriayBtn(userFriendProcess.getGroupInfoById(groupId).getGroupName());
        		btn.addClassName("main-left-user-btn");
        		btn.setIcon(new Icon(VaadinIcon.USERS));
        		btn.setId(groupId);
        		btn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
    				
    				private static final long serialVersionUID = -7908366682546180586L;

    				@Override
    				public void onComponentEvent(ClickEvent<Button> event) {
    					groupFlag  = true;
    					currentSelectedUserId = groupId;
    					updateMessageLayout(currentSelectedUserId);
    					//移除所有颜色
    					List<Component> list = userListLayout.getChildren().toList();
    					for (Component btn2 : list) {
    						Button bt = (Button)btn2;
    						bt.removeThemeVariants(ButtonVariant.LUMO_SUCCESS);
    					}
    					btn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
    				}
    			});
        		userListLayout.add(btn);
			}
			
		}
    	if (CollectionUtil.isNotEmpty(userFriendById)) {
    		currentSelectedUserId = userFriendById.get(0).getFriendId();
		}
    	
    }

    /**
     * 右侧聊天信息展示
     */
    private void addRightChatLayout() {
    	VerticalLayout rightLayout = ComFactory.getVerticalLayout();
    	rightLayout.addClassName("right-message-layout");
    	messageLayout = ComFactory.getVerticalLayout(JustifyContentMode.START);
    	sc = new Scroller(messageLayout);
    	sc.setScrollDirection(ScrollDirection.VERTICAL);
    	sc.addClassName("main-message-toplayout");
      	inputLayout = ComFactory.getVerticalLayout(JustifyContentMode.START);
      	inputLayout.addClassName("main-message-input-layout");
      	inputLayout.setSpacing(false);
      	
      	Button sendBtn = ComFactory.getPrimaryBtn("发送");
    	sendBtn.addClickListener(e ->{
    		String content = textArea.getValue();
    		if (StrUtil.isEmpty(content)) {
				ComFactory.notification("不能发送空内容", Position.MIDDLE);
				return;
			}
    		if (groupFlag) {
				GroupMessage gm = new GroupMessage();
				gm.setUserId(ComponentUtil.getCurrentUser().getUserId());
				gm.setDtSend(new Date());
				gm.setGroupId(currentSelectedUserId);
				gm.setMessage(content);
				gm.setMessageId(IdUtil.getSnowflakeNextId());
				userMessageProcess.insertGroupMessage(gm);
				displayMessage(convertToUserMessage(gm), Alignment.END);
			}else {
				//insert message and update messageLayout
				UserMessage message = new UserMessage();
				message.setUserId(ComponentUtil.getCurrentUser().getUserId());
				message.setReceiveUserId(currentSelectedUserId);
				message.setMessage(content);
				message.setMessageId(IdUtil.getSnowflakeNextId());
				userMessageProcess.insertUserMessage(message);
				displayMessage(message, Alignment.END);
			}
    		textArea.clear();
    		sc.getElement().executeJs("this.scrollTop = this.scrollHeight;");
    	});
    	chatLayout.add(rightLayout);
    	HorizontalLayout uploadLayout = ComFactory.getResponsiveHorizontalLayoutLeft();
    	upload = ComFactory.getUploadEmpty(saveDir );
    	UploadExamplesI18N i18N = new UploadExamplesI18N();
    	i18N.getUploading().getError().setUnexpectedServerError("文件类型不支持");
    	upload.setI18n(i18N);
    	upload.setUploadButton(ComFactory.getPrimaryBtn("上传文件"));
    	upload.setHeight("50px");
    	upload.addFinishedListener(this);
    	upload.setReceiver(this);
    	uploadLayout.add(upload);
    	uploadLayout.setSpacing(false);
    	
    	rightLayout.add(sc,uploadLayout,inputLayout,sendBtn);
    	rightLayout.setHorizontalComponentAlignment(Alignment.END, sendBtn);
    	textArea = ComFactory.getTextAreaWithHeight("145px");
    	inputLayout.add(textArea);
    	//添加emoji
    	Button emoBtn = ComFactory.getTertriayBtn("表情");
    	uploadLayout.add(emoBtn);
    	
    	dialog = new Dialog();
		dialog.setCloseOnOutsideClick(true);
		dialog.setHeaderTitle("表情");
		dialog.add(getEmoLayout());
		emoBtn.addClickListener(emo ->{
			dialog.open();
		});
    }
    
    private HorizontalLayout getEmoLayout() {
    	HorizontalLayout emoLayout = ComFactory.getResponsiveHorizontalLayoutLeft();
    	emoLayout.setWidth("300px");
    	emoLayout.setHeight("300px");
    	List<Emoji> allEmoji = userFriendProcess.getAllEmoji();
    	if (CollectionUtil.isNotEmpty(allEmoji)) {
    		for (Emoji emoji : allEmoji) {
    			Span s = new Span();
    			s.addClassName("file-message-emoji");
    			emoLayout.add(s);
    	    	s.add(emoji.getEmoji());
    	    	s.addClickListener(e ->{
    	    		if (groupFlag) {
    	    			GroupMessage gm = new GroupMessage();
    					gm.setUserId(ComponentUtil.getCurrentUser().getUserId());
    					gm.setDtSend(new Date());
    					gm.setGroupId(currentSelectedUserId);
    					gm.setMessage(emoji.getEmoji());
    					gm.setMessageId(IdUtil.getSnowflakeNextId());
    					userMessageProcess.insertGroupMessage(gm);
    					displayMessage(convertToUserMessage(gm), Alignment.END);
					}else {
						UserMessage message = new UserMessage();
						message.setUserId(ComponentUtil.getCurrentUser().getUserId());
						message.setReceiveUserId(currentSelectedUserId);
						message.setMessage(emoji.getEmoji());
						message.setMessageId(IdUtil.getSnowflakeNextId());
						userMessageProcess.insertUserMessage(message);
						displayMessage(message, Alignment.END);
					}
    	    		textArea.clear();
    	    		sc.getElement().executeJs("this.scrollTop = this.scrollHeight;");
    	    		dialog.close();
    	    	});
			}
		}
    	
    	return emoLayout;
    }
    /**
     * 初始化消息记录
     * @param selectedUserId
     */
    private void updateMessageLayout(String selectedUserId) {
    	messageLayout.removeAll();
    	//load the latest message
    	if (groupFlag) {
			List<GroupMessage> groupMessageByUserId = userMessageProcess.getGroupMessageByUserId(selectedUserId);
			for (GroupMessage groupMessage : groupMessageByUserId) {
				if (groupMessage.getUserId().equals(ComponentUtil.getCurrentUser().getUserId())) {
					if (groupMessage.getFileName() != null) {
						addFileMessageToRight(convertToUserMessage(groupMessage));
					}else {
						displayMessage(convertToUserMessage(groupMessage),Alignment.END);
					}
				}else {
					if (groupMessage.getFileName() != null) {
						addFileMessageToLeft(convertToUserMessage(groupMessage));
					}else {
						displayMessage(convertToUserMessage(groupMessage),Alignment.START);
					}
				}
			}
		}else {
			List<UserMessage> userMessageByUserId = userMessageProcess.getUserMessageByUserId(selectedUserId);
			for (UserMessage userMessage : userMessageByUserId) {
				if (userMessage.getUserId().equals(ComponentUtil.getCurrentUser().getUserId())) {
					if (userMessage.getFileName() != null) {
						addFileMessageToRight(userMessage);
					}else {
						displayMessage(userMessage,Alignment.END);
					}
				}else {
					if (userMessage.getFileName() != null) {
						addFileMessageToLeft(userMessage);
					}else {
						displayMessage(userMessage,Alignment.START);
					}
				}
			}
		}
    	sc.getElement().executeJs("this.scrollTop = this.scrollHeight;");
    }
	private UserMessage convertToUserMessage(GroupMessage groupMessage) {
		UserMessage message = new UserMessage();
		message.setUserId(groupMessage.getUserId());
//		message.setReceiveUserId(currentSelectedUserId);
		message.setMessage(groupMessage.getMessage());
		message.setMessageId(groupMessage.getMessageId());
		message.setDtSend(groupMessage.getDtSend());
		message.setFileName(groupMessage.getFileName());
		message.setFileUrl(groupMessage.getFileUrl());
		return message;
	}

	/**
	 * 添加一条消息到右侧
	 * @param userMessage
	 */
	private void displayMessage(UserMessage userMessage,Alignment ali) {
		HorizontalLayout rightMessageLayout = ComFactory.getResponsiveHorizontalLayoutLeft();
		rightMessageLayout.addClassName("main-message-layout");
		Span sp = new Span(userMessage.getMessage());
		Avatar avatarName = new Avatar(userMessage.getUserId());
		avatarName.addClassName("main-avatar");
//				avatarName.setImage(userMessage.getImgUrl());
		if (ali.equals(Alignment.END)) {
			sp.addClassName("main-span-right-style");
			if (userMessage.getMessage().length() >30){
				sp.setWidth("300px");
			}
			rightMessageLayout.add(sp,avatarName);
			rightMessageLayout.setJustifyContentMode(JustifyContentMode.END);
		}else {
			sp.addClassName("main-span-left-style");
			if (userMessage.getMessage().length() >30){
				sp.setWidth("350px");
			}
			rightMessageLayout.add(avatarName,sp);
		}
		messageLayout.add(rightMessageLayout);
		messageLayout.setHorizontalComponentAlignment(ali,rightMessageLayout);
	}
	
	private void addFileMessageToLeft(UserMessage userMessage) {
		HorizontalLayout rightMessageLayout = ComFactory.getResponsiveHorizontalLayoutLeft();
		rightMessageLayout.addClassName("main-message-layout");
		Span sp = new Span(userMessage.getFileName());
		Image img = getFileIcon(userMessage);
		img.addClickListener(im ->{
//			文件下载只需要访问对应接口，发送制定文件名即可${pageContext.request.contextPath}/file/download?fileName=aa.txt
			UI.getCurrent().getPage().open(userMessage.getFileUrl(), userMessage.getFileName());
		});
		Avatar avatarName = new Avatar(userMessage.getUserId());
		avatarName.addClassName("main-avatar");
//				avatarName.setImage(userMessage.getImgUrl());
		sp.addClassName("main-span-left-style");
		
		rightMessageLayout.add(avatarName,sp,img);
		messageLayout.add(rightMessageLayout);
		messageLayout.setHorizontalComponentAlignment(Alignment.START,rightMessageLayout);
	}
	
	private void addFileMessageToRight(UserMessage userMessage) {
		HorizontalLayout rightMessageLayout = ComFactory.getResponsiveHorizontalLayoutLeft();
		rightMessageLayout.addClassName("main-message-layout");
		Span sp = new Span(userMessage.getFileName());
		Image img = getFileIcon(userMessage);
		img.addClickListener(im ->{
//			文件下载只需要访问对应接口，发送制定文件名即可${pageContext.request.contextPath}/file/download?fileName=aa.txt
			UI.getCurrent().getPage().open(userMessage.getFileUrl(), userMessage.getFileName());
		});
		Avatar avatarName = new Avatar(userMessage.getUserId());
		avatarName.addClassName("main-avatar");
//				avatarName.setImage(userMessage.getImgUrl());
		sp.addClassName("main-span-right-style");
		rightMessageLayout.setJustifyContentMode(JustifyContentMode.END);
		rightMessageLayout.add(img,sp,avatarName);
		messageLayout.add(rightMessageLayout);
		messageLayout.setHorizontalComponentAlignment(Alignment.END,rightMessageLayout);
	}

	private Image getFileIcon(UserMessage userMessage) {
		Image img = null;
		if (userMessage.getFileName().endsWith("pdf")) {
			img = new Image("file/img/pdf.png",userMessage.getFileName());
		}else if(userMessage.getFileName().endsWith("doc") || userMessage.getFileName().endsWith("docx")){
			img = new Image("file/img/word.png",userMessage.getFileName());
		}else if (userMessage.getFileName().endsWith("xls") || userMessage.getFileName().endsWith("xlsx")) {
			img = new Image("file/img/excel.png",userMessage.getFileName());
		}else if (userMessage.getFileName().endsWith("png") || userMessage.getFileName().endsWith("jpeg") ||
				userMessage.getFileName().endsWith("jpg")) {
			img = new Image("file/img/image.png",userMessage.getFileName());
		}else {
			img = new Image("file/img/file.png",userMessage.getFileName());
		}
		img.addClassName("file-message-icon-style");
		return img;
	}
	/**
	 * 1.拉取大于上次时间之后的所有消息
	 * 2. 从左侧列表中遍历用户，判断消息是否是某个用户发送的。
	 * 3. 如果是当前遍历的用户，判断是否选中，选中则直接将消息追加到消息栏，没有选中则增加css样式
	 * 4. 遍历中还需要判断当前用户上次是否已经增加过css样式，如果增加过仍然保留。
	 * @param view 
	 * 
	 */
    public static void refreshMessage(MainTestView view) {
    	UserMessageProcess messageProcess = ComponentUtil.applicationContext.getBean(UserMessageProcess.class);
    	Long maxMessageId = messageProcess.getMaxMessageId(ComponentUtil.getCurrentUser().getUserId());
    	if (null != currentMaxMessageId && null != maxMessageId && maxMessageId > currentMaxMessageId) {
    		List<UserMessage> userMessageGtCurrent = messageProcess.getUserMessageGtCurrent(currentMaxMessageId);
    		Map<String,List<UserMessage>> collect = userMessageGtCurrent.stream().collect(Collectors.groupingBy(UserMessage::getUserId));
    		
    		List<Component> children = view.userListLayout.getChildren().toList();
    		children.forEach( e ->{
    			if (e instanceof Button) {
    				Button btn = (Button)e;
    				Optional<String> id = btn.getId();
					List<UserMessage> list = collect.get(id.get());
					Icon icon = (Icon)btn.getIcon();
    				if ("vaadin:user".equals(icon.getElement().getAttribute("icon"))) {//用户
    					if(!CollectionUtil.isEmpty(list)) {//当前用户有发给我的消息
    						if(id.get().equals(view.currentSelectedUserId) || children.size() == 1) {//直接追加到消息栏
								for (UserMessage u : list) {
									if (u.getFileName() != null){
										view.addFileMessageToLeft(u);
									}else{
										view.displayMessage(u,Alignment.START);
									}
								}
    							view.sc.getElement().executeJs("this.scrollTop = this.scrollHeight;");
    						}else {
    							//添加样式
    							btn.addClassName("btn-message-notification");
    							btn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
    						}
    					}
    				}
				}
    		});
    	currentMaxMessageId = messageProcess.getMaxMessageId(ComponentUtil.getCurrentUser().getUserId());
		}
    	//更新组消息
    	Long maxGroupId = messageProcess.getCurrentMaxGroupMessageId(ComponentUtil.getCurrentUser().getUserId());
    	if (null != currentMaxGroupMessageId && null != maxGroupId && maxGroupId > currentMaxGroupMessageId) {
    		List<GroupMessage> groupMessages = messageProcess.getGroupMessageGtCurrent(currentMaxGroupMessageId);
    		Map<String, List<GroupMessage>> collect = groupMessages.stream().collect(Collectors.groupingBy(GroupMessage::getGroupId));
    		List<Component> children = view.userListLayout.getChildren().toList();
    		children.forEach( e ->{
    			if (e instanceof Button) {
    				Button btn = (Button)e;
    				Optional<String> id = btn.getId();
    				Icon icon = (Icon)btn.getIcon();
    				if ("vaadin:users".equals(icon.getElement().getAttribute("icon"))) {//组
    					List<GroupMessage> list = collect.get(id.get());
    					if(!CollectionUtil.isEmpty(list)) {//当前用户有发到群的消息
							if(id.get().equals(view.currentSelectedUserId)) {//直接追加到消息栏
								for (GroupMessage gm : list) {
									if(!gm.getUserId().equals(ComponentUtil.getCurrentUser().getUserId())) {
										if (gm.getFileName() != null){
											view.addFileMessageToLeft(view.convertToUserMessage(gm));
										}else{
											view.displayMessage(view.convertToUserMessage(gm),Alignment.START);
										}
									}
								}
								view.sc.getElement().executeJs("this.scrollTop = this.scrollHeight;");
							}else {
								//添加样式
								btn.addClassName("btn-message-notification");
								btn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
							}
						}
					}
				}
    		});
    	currentMaxGroupMessageId = messageProcess.getCurrentMaxGroupMessageId(ComponentUtil.getCurrentUser().getUserId());
    		
		}
    }
    @Override
	protected void onAttach(AttachEvent attachEvent) {
    	thread = new FeederThread(attachEvent.getUI(), this);
		thread.start();
    }
    
	@Override
	protected void onDetach(DetachEvent detachEvent) {
		// Cleanup
		System.out.println("线程结束。。。");
		thread.interrupt();
		thread = null;
	}
	private static class FeederThread extends Thread {
		private final UI ui;
		private final MainTestView view;

		private int count = 0;

		public FeederThread(UI ui, MainTestView view) {
			this.ui = ui;
			this.view = view;
		}

		@Override
		public void run() {
			try {
				// Update the data for a while
				while (count < 600) {
					// Sleep to emulate background work
					Thread.sleep(3000);
					String message = "This is update " + count++;
					ui.access(() -> refreshMessage(view));
//					ui.access(() -> view.label.setText(message));
				}

				// Inform that we're done
				ui.access(() -> {
					view.add(new Span("Done updating"));
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void onComponentEvent(FinishedEvent event) {
		if (groupFlag) {
			GroupMessage gm = new GroupMessage();
			gm.setUserId(ComponentUtil.getCurrentUser().getUserId());
			gm.setDtSend(new Date());
			gm.setGroupId(currentSelectedUserId);
			gm.setMessage("文件发送");
			gm.setMessageId(IdUtil.getSnowflakeNextId());
			gm.setFileName(fileName);
			String fileUrl = MinioUtils.uploadFile(fileUploaded);
			gm.setFileUrl(fileUrl);
			userMessageProcess.insertGroupMessage(gm);
		}else {
			UserMessage message = new UserMessage();
			message.setUserId(ComponentUtil.getCurrentUser().getUserId());
			message.setReceiveUserId(currentSelectedUserId);
			message.setMessage("文件发送");
//		message.setFileUrl(saveDir);
			message.setFileName(fileName);
			message.setMessageId(IdUtil.getSnowflakeNextId());
			addFileMessageToRight(message);
			String fileUrl = MinioUtils.uploadFile(fileUploaded);
			message.setFileUrl(fileUrl);
			userMessageProcess.insertUserMessage(message);
		}
		sc.getElement().executeJs("this.scrollTop = this.scrollHeight;");
		upload.clearFileList();
	}

	@Override
	public OutputStream receiveUpload(String fileName, String mimeType) {
		log.warn("开始接收文件。。。"+fileName);
		this.fileName = fileName;
		fileUploaded = new File(saveDir + File.separator + fileName);
		FileOutputStream fileStream = null;
		try {
			fileStream = new FileOutputStream(fileUploaded);
		} catch (FileNotFoundException e) {
			log.error(ExceptionUtils.getStackTrace(e));
			return null;
		}
		return fileStream;
	}
}
