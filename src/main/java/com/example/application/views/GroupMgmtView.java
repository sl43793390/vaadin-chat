package com.example.application.views;

import java.util.List;
import java.util.Set;

import cn.hutool.core.collection.CollectionUtil;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.component.util.ComFactory;
import com.example.application.component.util.ComponentUtil;
import com.example.entity.GroupInfo;
import com.example.entity.User;
import com.example.service.UserFriendProcess;
import com.example.service.UserMessageProcess;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import cn.hutool.core.util.StrUtil;

/**
 * 群组管理
 * 
 * @author Administrator
 *
 */
@Route("group")
public class GroupMgmtView extends VerticalLayout {
	
	private static final long serialVersionUID = -1054288663515499003L;
	private UserFriendProcess userFriendProcess;
	private UserMessageProcess userMessageProcess;
	private TextField groupField;
	private Button searchBtn;
	private Grid<GroupInfo> grid;
	private Button createGroupBtn;
	private Grid<User> popGrid;
	private Dialog createDialog;
	private Grid<User> transferGrid;
	private Dialog transferDialog;
	private GroupInfo currentSelectedGroup;

	public GroupMgmtView(@Autowired UserFriendProcess userFriendProcess,@Autowired UserMessageProcess userMessageProcess) {
		this.userFriendProcess = userFriendProcess;
		this.userMessageProcess = userMessageProcess;
		if (null == ComponentUtil.getCurrentUser()) {
			return;
		}
		
    	Button userBtn = ComFactory.getTertriayBtn("返回");
    	userBtn.addClickListener(e ->{
    		getUI().ifPresent(ui -> ui.navigate("/chat"));
    	});
    	userBtn.getStyle().set("margin-top", "20px").set("margin-left", "20px");

    	//添加用户名label
		Label label = ComFactory.getLabel(ComponentUtil.getCurrentUser().getUserName());
		label.getStyle().set("margin-top", "20px").set("margin-left", "20px");
		
		Button standardButton = ComFactory.getTertriayBtn("退出");
		standardButton.addClickListener(e ->{
			getUI().ifPresent(ui -> ui.navigate(""));
			VaadinSession.getCurrent().getSession().invalidate();
		});
		standardButton.getStyle().set("margin-top", "20px").set("margin-left", "20px");
		
		HorizontalLayout wrapper = ComFactory.getResponsiveHorizontalLayoutRight();
		wrapper.add(userBtn,label,standardButton);
//    			wrapper.setAlignItems(FlexComponent.Alignment.START);
		wrapper.getStyle().set("height", "40px");
		wrapper.setSpacing(false);

        setMargin(true);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        add(wrapper);
        addGrid();
	}

	private void addGrid() {
		HorizontalLayout searchLayout = ComFactory.getResponsiveHorizontalLayoutLeft();
		searchLayout.setHeight("40px");
		Label lb = new Label("我创建或加入的群");
		groupField = new TextField();
		searchBtn = ComFactory.getPrimaryBtn("搜索");
		createGroupBtn = ComFactory.getPrimaryBtn("创建群");
		createGroupBtn.addClickListener(c ->{
			createDialog.open();
		});
		searchBtn.addClickListener(e ->{
			if (StrUtil.isEmpty(groupField.getValue())) {
				ComFactory.notification("请先输入群组名称");
				return;
			}
			List<GroupInfo> searchUser = userFriendProcess.searchGroup(groupField.getValue());
			grid.setItems(searchUser);
		});
		searchLayout.add(lb,groupField,searchBtn,createGroupBtn);
		add(searchLayout);
		
        Span stats = new Span("联系人"); 
        stats.addClassNames("text-xl", "mt-m");
        add(stats);
        
        grid = new Grid<GroupInfo>();
        grid.addColumn(GroupInfo::getGroupName).setHeader("群名称");
        grid.addColumn(GroupInfo::getGroupOwner).setHeader("当前群主");
        grid.addColumn(GroupInfo::getGroupAnnouncement).setHeader("群公告");
        grid.addComponentColumn(com ->{
        	//判断是不是群主
        	boolean isOwver = userFriendProcess.checkIsGroupOwner(com.getGroupId());
        	if (!isOwver) {//不是群主不能操作
				return null;
			}
				Button btn = ComFactory.getTertriayBtn("解散群");
			   	btn.addClickListener(e -> {
	        		Dialog dialog = ComFactory.getDialog("提示：请确认是否解散该群："+com.getGroupName());
	        		Button saveBtn = ComFactory.getPrimaryBtn("确认");
	        		Button cancelBtn = ComFactory.getPrimaryBtn("取消");
	        		saveBtn.addClickListener(e1 ->{
	        			userFriendProcess.dissolveGroup(com.getGroupId());
	        			ComFactory.notification("解散成功", Position.MIDDLE);
	        			dialog.close();
						List<GroupInfo> groupInfoByUserId = userFriendProcess.getGroupInfoByUserId();
						grid.setItems(groupInfoByUserId);
	        		});
	        		cancelBtn.addClickListener(ca -> dialog.close());
	        		dialog.getFooter().add(saveBtn,cancelBtn);
	        		dialog.open();
	        	});
	        	return btn;
        }).setHeader("解散群");
        grid.addComponentColumn(com ->{
        	Button btn = ComFactory.getTertriayBtn("退出群");
        	btn.addClickListener(e -> {
        		Dialog dialog = ComFactory.getDialog("提示：请确认是否退出该群："+com.getGroupName());
        		Button saveBtn = ComFactory.getPrimaryBtn("确认");
        		Button cancelBtn = ComFactory.getPrimaryBtn("取消");
        		saveBtn.addClickListener(e1 ->{
					//判断是不是群主,群主需要先转让群主后才能退群
					boolean isOwver = userFriendProcess.checkIsGroupOwner(com.getGroupId());
					if (!isOwver) {
						userFriendProcess.leaveGroup(com.getGroupId(),ComponentUtil.getCurrentUserId());
						ComFactory.notification("退出成功", Position.MIDDLE);
					}else{
						ComFactory.notification("群主需要先转让群主后才能退群", Position.MIDDLE);
						return;
					}
        			dialog.close();
        		});
        		cancelBtn.addClickListener(ca -> dialog.close());
        		dialog.getFooter().add(saveBtn,cancelBtn);
        		dialog.open();
        	});
        	return btn;
        }).setHeader("退出群");
        grid.addComponentColumn(com ->{
        	//判断是不是群主
        	boolean isOwver = userFriendProcess.checkIsGroupOwner(com.getGroupId());
        	if (!isOwver) {//不是群主不能操作
				return null;
			}
        	Button btn = ComFactory.getTertriayBtn("转让群主");
        	btn.addClickListener(e -> {
				currentSelectedGroup = com;
				List<User> userInGroup = userFriendProcess.getUserInGroup(com.getGroupId());
				transferGrid.setItems(userInGroup);
				transferDialog.open();
        	});
        	return btn;
        }).setHeader("转让群主");
        grid.addComponentColumn(com ->{
			//判断是不是群主
			boolean isOwver = userFriendProcess.checkIsGroupOwner(com.getGroupId());
			if (!isOwver) {//不是群主不能操作
				return null;
			}
        	Button btn = ComFactory.getTertriayBtn("编辑");
        	btn.addClickListener(e -> {
				createEditGroupDialog(com);
        	});
        	return btn;
        }).setHeader("编辑");
		List<GroupInfo> groupInfoByUserId = userFriendProcess.getGroupInfoByUserId();
		grid.setItems(groupInfoByUserId);
		add(grid);
        createDialog();
		createTransferDialog();
    }

	private Dialog createEditGroupDialog(GroupInfo info) {
		Dialog editDialog = new Dialog();
		editDialog.setResizable(true);
		editDialog.setMinWidth("300px");
		editDialog.setCloseOnOutsideClick(true);
		editDialog.setMaxWidth("500px");
		editDialog.setMaxHeight("600px");
		editDialog.setHeaderTitle("修改群信息");

		FormLayout formLayout = new FormLayout();
		formLayout.setHeight("450px");
		FormLayout.ResponsiveStep step = new FormLayout.ResponsiveStep("300px", 1, FormLayout.ResponsiveStep.LabelsPosition.ASIDE);
		formLayout.setResponsiveSteps(step);
		TextField textField = new TextField("群名称");
		textField.setRequired(true);
		textField.setClearButtonVisible(true);
		textField.setErrorMessage("必填");
		textField.setValue(info.getGroupName());
		formLayout.add(textField);

		TextArea area = ComFactory.getTextArea("群公告");
		area.setValue(info.getGroupAnnouncement()==null?"":info.getGroupAnnouncement());
		area.setHeight("300px");
		formLayout.add(area);
		editDialog.add(formLayout);

		Button saveButton = new Button("保存", e -> {
			String name = textField.getValue();
			String announce = area.getValue();
			if (StrUtil.isEmpty(name)){
				ComFactory.notificationError("名称必须填写后才能保存");
				return;
			}
			userFriendProcess.updateGroupInfo(currentSelectedGroup,name,announce);
			List<GroupInfo> groupInfoByUserId = userFriendProcess.getGroupInfoByUserId();
			grid.setItems(groupInfoByUserId);
			editDialog.close();
		});
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Button cancelButton = new Button("取消", e -> editDialog.close());
		editDialog.getFooter().add(cancelButton);
		editDialog.getFooter().add(saveButton);
		editDialog.open();
		return editDialog;
	}

	private void createTransferDialog() {
		Button saveBtn = ComFactory.getPrimaryBtn("确认");
		Button cancelBtn = ComFactory.getPrimaryBtn("取消");
		transferGrid = new Grid<User>();
		transferGrid.addColumn(User::getUserName).setHeader("用户名");
		transferGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
		transferDialog =  ComFactory.getDialog("转让群主", transferGrid, saveBtn, cancelBtn);
		transferDialog.setWidth("300px");

		saveBtn.addClickListener(e1 ->{
			Set<User> selectedItems = transferGrid.getSelectedItems();
			if (CollectionUtil.isNotEmpty(selectedItems)){
				userFriendProcess.transferGroupOwner(currentSelectedGroup,selectedItems);
				ComFactory.notification("转让成功", Position.MIDDLE);
				List<GroupInfo> groupInfoByUserId = userFriendProcess.getGroupInfoByUserId();
				grid.setItems(groupInfoByUserId);
			}else {
				ComFactory.notification("请选择转让的用户");
				return;
			}
			transferDialog.close();
		});
		cancelBtn.addClickListener(ca -> transferDialog.close());
		transferDialog.getFooter().add(saveBtn,cancelBtn);
	}

	private void createDialog() {
		Button confirmBtn = ComFactory.getPrimaryBtn("确认");
		Button cancelBtn = ComFactory.getPrimaryBtn("取消");
		popGrid = new Grid<User>();
		VerticalLayout verticalLayout = ComFactory.getVerticalLayout();
		createDialog = ComFactory.getDialog("创建组", verticalLayout, confirmBtn, cancelBtn);
		cancelBtn.addClickListener(c -> createDialog.close());
		createDialog.setWidth("450px");
		createDialog.setHeight("600px");
		popGrid.setHeight("430px");
		createDialog.add(verticalLayout);

		HorizontalLayout searchLayout = ComFactory.getResponsiveHorizontalLayoutLeft();
		verticalLayout.add(searchLayout);
		Label groupName = ComFactory.getLabel("群名称");
		TextField groupField = ComFactory.getTextField();
		searchLayout.add(groupName, groupField);
		verticalLayout.add(popGrid);
		confirmBtn.addClickListener(con ->{
			String value = groupField.getValue();
			if (StrUtil.isEmpty(value)){
				ComFactory.notification("请先输入组名称");
				return;
			}
			Set<User> selectedItems = popGrid.getSelectedItems();
			if (CollectionUtil.isEmpty(selectedItems)){
				ComFactory.notification("请先输入组名称");
				return;
			}
			userFriendProcess.createGroup(value,selectedItems);
			ComFactory.notification("创建成功", Position.MIDDLE);
			createDialog.close();
			List<GroupInfo> groupInfoByUserId = userFriendProcess.getGroupInfoByUserId();
			grid.setItems(groupInfoByUserId);
		});
		popGrid.addColumn(User::getUserName).setHeader("用户名");
		popGrid.setSelectionMode(Grid.SelectionMode.MULTI);
		//加载grid数据
		List<User> userByFrendIds = userFriendProcess.getUserByFrendIds();
		 popGrid.setItems(userByFrendIds);
	}
	
}
