package com.example.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.example.application.component.util.ComFactory;
import com.example.application.component.util.ComponentUtil;
import com.example.application.component.util.TabsUtil;
import com.example.application.views.AppLayoutAccordionView;
import com.example.entity.User;
import com.example.service.UserManageMentProcess;
import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty.Type;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
@Scope("prototype")
public class UserManageMentComponent extends VerticalLayout {

	private static final long serialVersionUID = 5187805936625809077L;
	private static final Logger logger = LoggerFactory.getLogger(UserManageMentComponent.class);
	private VerticalLayout workingAreaLayout;

	private Grid<User> userListTable;
	private Button addBtn;
	private Button removeBtn;
	private Button updateBtn;
	private Button searchButton;
	private TextField searchField;
	private UserManageMentProcess usermanageProcess;
	private String fuzzyValue;
	private ConfirmDialog confirmationDialog;
	private ConfirmDialog removeDialog;

	public UserManageMentComponent(@Autowired UserManageMentProcess usermanageProcess) {
		this.usermanageProcess = usermanageProcess;
		add(ComFactory.getTitle("用户列表"));
		add(ComFactory.getHorizontalLine());
		// 此处分为三部分layout；1、用户列表标题及搜索框；2、用户列表table；3、操作按钮区；
		initTitleAndSearchLayout();
		initUserListTableLayout();
		initContent();
		registerHandler();
	}

	private void initTitleAndSearchLayout() {
		HorizontalLayout layoutLeft = ComFactory.getResponsiveHorizontalLayoutLeft();
		add(layoutLeft);
		layoutLeft.add(ComFactory.getLabel("用户名："));
		
		searchField = ComFactory.getTextField();
		searchButton = ComFactory.getPrimaryBtn("搜索");
		searchButton.setIcon(new Icon(VaadinIcon.SEARCH));
		addBtn = ComFactory.getPrimaryBtn("新增");
		updateBtn = ComFactory.getPrimaryBtn("修改");
		removeBtn = ComFactory.getPrimaryBtn("删除");
		layoutLeft.add(searchField,searchButton,addBtn,updateBtn,removeBtn);
	}

	private void initUserListTableLayout() {
		userListTable = ComFactory.getGrid(User.class);
		userListTable.setHeight("600px");
		userListTable.addColumn(User::getUserId).setHeader("用户ID");
		userListTable.addColumn(User::getUserName).setHeader("用户名");
		userListTable.addColumn(User::getEmail).setHeader("邮箱");
		userListTable.addColumn(User::getOrganization).setHeader("机构");
		userListTable.addComponentColumn(p -> {
			Button b = ComFactory.getPrimaryBtn("删除");
			b.addClickListener(e -> {
				confirmationDialog = ComFactory.getConfirmationDialog(event -> {
					deleteUser(p.getUserId());
					ComFactory.notification("删除成功");
				}, cancel  ->{
					confirmationDialog.close();
				});
				confirmationDialog.open();
			});
			return b;
		}).setHeader("删除用户");
		add(userListTable);
	}

	public void initContent() {
		initContentTable();
	}

	public void initContentTable() {
		userListTable.setItems(new ArrayList<User>());
		// checkedAllUser 代表超级管理员，checkedInstitutionUser代表机构管理员
		if (SecurityUtils.getSubject().isPermitted("checkedAllUser")) {
			List<User> selectUserByPage = usermanageProcess.selectAllUser();
			updateUserList(selectUserByPage);
		} else if (SecurityUtils.getSubject().isPermitted("checkedInstitutionUser")) {
			List<User> userlistInstitution = usermanageProcess.selectUserByIdInstitution(ComponentUtil.getCurrentInstitution());
			updateUserList(userlistInstitution);
		} else {
			User userOnly = usermanageProcess.selectUserById(ComponentUtil.getCurrentUser().getUserId());
			ArrayList<User> arrayList = new ArrayList<User>();
			arrayList.add(userOnly);
			userListTable.setItems(arrayList);
		}
	}

	public void registerHandler() {

		searchButton.addClickListener(e ->{
			fuzzyValue = searchField.getValue();
			updateUserListFuzzyMode(fuzzyValue);
		});
		addBtn.addClickListener(e ->{
				if (!SecurityUtils.getSubject().isPermitted("userAdd")) {
					ComFactory.notification("权限不足，请联系管理员");
					return;
				}
				// String UserId = userListTable.getValue().toString();
//				userListTable.select(userListTable.firstItemId());
				addNewTab();
		});

		removeBtn.addClickListener(e ->{
				if (!SecurityUtils.getSubject().isPermitted("deleteUser")) {
					ComFactory.notification("权限不足，请联系管理员");
					return;
				}
				if (userListTable.getSelectedItems().isEmpty()) {
					ComFactory.notification("请选择一个用户");
					return;
				}
				
				removeDialog = ComFactory.getConfirmationDialog(conf ->{
					User user = ComponentUtil.getSelectedItemForGrid(userListTable);
					String userId = user.getUserId();
					deleteUser(userId);
					logger.info("用户管理,用户：," + ComponentUtil.getCurrentUser().getUserId() + "执行删除操作，删除用户：" + userId);
					boolean closeUpdateTab = TabsUtil.closeTargetTab(TabsUtil.CAPTION_MODIFY_USER);
					if (closeUpdateTab) {
						ComFactory.notification("删除用户成功");
					}
				}, cancel ->{
					removeDialog.close();
				});
				removeDialog.open();
		});

		updateBtn.addClickListener(e ->{
				if (!SecurityUtils.getSubject().isPermitted("updateUser")) {
					ComFactory.notification("权限不足，请联系管理员");
					return;
				}
				try {
					if (ComponentUtil.getSelectedItemForGrid(userListTable) == null) {
						ComFactory.notification("请选择一个用户");
						return;
					}
					User user = ComponentUtil.getSelectedItemForGrid(userListTable);
					addUpdateTab(user.getUserId());

				} catch (Exception ex) {
					ex.printStackTrace();
				}

		});
	}

	// 将新查询出来的用户list重新加载到表格中；
	private void updateUserList(List<User> userlist) {
		userListTable.setItems(userlist);
	}

	private void updateUserListFuzzyMode(String value) {
		// 模糊查找
		if (value.equals("") || value == null) {
			initContentTable();
			return;
		}
		//如果有则根据条件模糊查询
		if (SecurityUtils.getSubject().isPermitted("checkedAllUser")) {
			List<User> selectUserByPage = usermanageProcess.selectAllUser();
			selectUserByPage = selectUserByPage.stream().filter(e ->e.getUserName().contains(value)).collect(Collectors.toList());
			updateUserList(selectUserByPage);
		} else if (SecurityUtils.getSubject().isPermitted("checkedInstitutionUser")) {
			List<User> userlistInstitution = usermanageProcess.selectUserByIdInstitution(ComponentUtil.getCurrentInstitution());
			userlistInstitution = userlistInstitution.stream().filter(e ->e.getUserName().contains(value)).collect(Collectors.toList());
			updateUserList(userlistInstitution);
		}
	}

	/**
	 * Function Name : deleteUser Description : 删除按钮功能
	 * 
	 * @author : sunlong
	 * @param userId2 
	 */
	public void deleteUser(String userId2) {
		if (userId2.equals(ComponentUtil.getCurrentUser().getUserId())) {
			ComFactory.notification("不可以删除自己");
			return;
		}
		try {
			usermanageProcess.deleteUserById(userId2);
			initContentTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		userListTable.select(null);
	}

	public UserManageMentProcess getUsermanageProcess() {
		return usermanageProcess;
	}

	public void setUsermanageProcess(UserManageMentProcess usermanageProcess) {
		this.usermanageProcess = usermanageProcess;
	}

	public void addNewTab() {
//		AppLayoutAccordionView mainView = ComponentUtil.applicationContext.getBean(AppLayoutAccordionView.class);
		Tab tabByName = TabsUtil.getTabByName(UserManagementEnums.USER_ADD);
		if (tabByName != null) {
			TabsUtil.getTabsheet().setSelectedTab(tabByName);
			return;
		}
		if (!TabsUtil.checkTabExists(TabsUtil.CAPTION_ADD_USER)) {
			UserAddUpdateComponent addUpdatec = new UserAddUpdateComponent();
			addUpdatec.setFlag(true);//新增
			addUpdatec.setUsermanageProcess(usermanageProcess);
			addUpdatec.initLayout();
			addUpdatec.initContent();
			addUpdatec.registerHandler();
			addUpdatec.addNewUserSetting();
			TabsUtil.addComponentTab(TabsUtil.CAPTION_ADD_USER, addUpdatec);
		} else {
			TabsUtil.setSelectedTab(TabsUtil.CAPTION_ADD_USER);
		}
	}

	public void addUpdateTab(String userId) {
		AppLayoutAccordionView bean = ComponentUtil.applicationContext.getBean(AppLayoutAccordionView.class);
		if (!TabsUtil.checkTabExists(TabsUtil.CAPTION_MODIFY_USER)) {
			addUpdateTab(userId, bean);
		} else {
			TabsUtil.closeTargetTab(TabsUtil.CAPTION_MODIFY_USER);
			addUpdateTab(userId, bean);
		}
	}

	private void addUpdateTab(String userId, AppLayoutAccordionView bean) {
		UserAddUpdateComponent addUpdatec = new UserAddUpdateComponent();
		addUpdatec.setFlag(false);
		addUpdatec.setUsermanageProcess(usermanageProcess);
		addUpdatec.initLayout();
		addUpdatec.initContent();
		addUpdatec.registerHandler();
		addUpdatec.updateUserSetting(userId);
		TabsUtil.addComponentTab(TabsUtil.CAPTION_MODIFY_USER, addUpdatec);
	}

}
