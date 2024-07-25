package com.example.application.views;

import java.util.List;

import com.example.application.component.util.ComFactory;
import com.example.application.component.util.ComponentUtil;
import com.example.application.component.util.TabsUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
/**
 * 左侧是accordion可展开的类似手风琴样式，可隐藏。右侧是tabsheet;
 * @author Administrator
 *
 */
@SpringComponent
@Route("userView")
@PreserveOnRefresh
@UIScope
public class AppLayoutAccordionView extends VerticalLayout {

	private static final long serialVersionUID = 5988417052518848565L;
	private TabSheet mainTabsheet;
	 
	public AppLayoutAccordionView() {
			if (null == ComponentUtil.getCurrentUser()) {
			return;
		}
		H1 appTitle = new H1("MyApp");
		Image img = new Image("img/w5.png", "");//项目图标
		img.getStyle().set("margin-left", "20px");
		appTitle.getStyle().set("font-size", "var(--lumo-font-size-l)").set("line-height", "var(--lumo-size-l)")
				.set("margin", "0 var(--lumo-space-m)");

		TabSheet subViews = getSecondaryNavigation();
		//添加头像
		Avatar namedAvatar = ComFactory.getNamedAvatar(ComponentUtil.getCurrentUserName(), "img/my.png");
		namedAvatar.getStyle().set("margin-left", "60%").set("margin-top", "10px");
		
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
		wrapper.add(label,standardButton);
//    			wrapper.setAlignItems(FlexComponent.Alignment.START);
		wrapper.getStyle().set("height", "40px");
		wrapper.setSpacing(false);
		add(wrapper,subViews);
		Component user = ComponentUtil.createComponent("user.UserManageMentComponent");
		Component role = ComponentUtil.createComponent("user.UserRoleManagementComponent");
		TabsUtil.addComponentTab(mainTabsheet, "角色管理", role,false);
		TabsUtil.addComponentTab(mainTabsheet, "用户管理", user,false);
	}


	private TabSheet getSecondaryNavigation() {
		mainTabsheet = ComFactory.getTabSheet();
		mainTabsheet.getStyle().set("margin-top", "15px");
		return mainTabsheet;
	}

	private VerticalLayout createContent(List<Button> buttons) {
		VerticalLayout content = new VerticalLayout();
		content.setPadding(true);
		content.setSpacing(true);
		buttons.forEach(e -> content.add(e));
		return content;
	}

	public TabSheet getMainTabsheet() {
		return mainTabsheet;
	}

	public void setMainTabsheet(TabSheet mainTabsheet) {
		this.mainTabsheet = mainTabsheet;
	}

	
}
