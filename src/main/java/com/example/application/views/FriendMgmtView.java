package com.example.application.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.component.util.ComFactory;
import com.example.application.component.util.ComponentUtil;
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
 * 好友管理
 * 
 * @author Administrator
 *
 */
@Route("friends")
public class FriendMgmtView extends VerticalLayout {
	private static final long serialVersionUID = 698608225102328803L;
	private UserFriendProcess userFriendProcess;
	private UserMessageProcess userMessageProcess;
	private TextField userField;
	private Button searchBtn;
	private Grid<User> grid;

	public FriendMgmtView(@Autowired UserFriendProcess userFriendProcess,@Autowired UserMessageProcess userMessageProcess) {
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
		Label lb = new Label("搜索用户");
		userField = new TextField();
		searchBtn = ComFactory.getPrimaryBtn("搜索");
		searchBtn.addClickListener(e ->{
			if (StrUtil.isEmpty(userField.getValue())) {
				ComFactory.notification("请先输入用户ID");
				return;
			}
			List<User> searchUser = userFriendProcess.searchUser(userField.getValue());
			grid.setItems(searchUser);
		});
		searchLayout.add(lb,userField,searchBtn);
		add(searchLayout);
		
        Span stats = new Span("联系人"); 
        stats.addClassNames("text-xl", "mt-m");
        add(stats);
        
        grid = new Grid<User>();
        grid.addColumn(User::getUserId).setHeader("用户ID");
        grid.addColumn(User::getUserName).setHeader("用户名");
        grid.addColumn(User::getEmail).setHeader("邮箱");
        grid.addComponentColumn(com ->{
        	//判断如果已经是好友不显示
        	boolean userFriend = userFriendProcess.checkUserFriendExists(ComponentUtil.getCurrentUser().getUserId(), com.getUserId());
        	if (userFriend) {
				return null;
			}
				Button btn = ComFactory.getTertriayBtn("添加好友");
				btn.addClickListener(e ->{
					userFriendProcess.insertUserFriend(com.getUserId());
					ComFactory.notification("添加成功", Position.MIDDLE);
					refreshGridContent();
				});
				return btn;
        }).setHeader("添加好友");
        grid.addComponentColumn(com ->{
//        	判断如果不是好友不展示
        	boolean userFriend = userFriendProcess.checkUserFriendExists(ComponentUtil.getCurrentUser().getUserId(), com.getUserId());
        	if (!userFriend) {
				return null;
			}
        	Button btn = ComFactory.getTertriayBtn("删除好友");
        	btn.addClickListener(e -> {
        		Dialog dialog = ComFactory.getDialog("提示：请确认是否删除好友："+com.getUserId());
        		Button saveBtn = ComFactory.getPrimaryBtn("确认");
        		Button cancelBtn = ComFactory.getPrimaryBtn("取消");
        		saveBtn.addClickListener(e1 ->{
        			userFriendProcess.deletetUserFriend(com.getUserId());
        			ComFactory.notification("删除成功", Position.MIDDLE);
					refreshGridContent();
					dialog.close();
        		});
        		cancelBtn.addClickListener(ca -> dialog.close());
        		dialog.getFooter().add(saveBtn,cancelBtn);
        		dialog.open();
        	});
        	return btn;
        }).setHeader("删除好友");
        add(grid);
		List<User> searchUser = userFriendProcess.getUserByFrendIds();
		grid.setItems(searchUser);
    }

	private void refreshGridContent() {
		List<User> searchUser = userFriendProcess.getUserByFrendIds();
		grid.setItems(searchUser);
	}


}
