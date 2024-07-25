package com.example.application.views;

import com.example.application.component.util.ComFactory;
import com.example.application.component.util.ComponentUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

public class Header extends VerticalLayout {

	private static final long serialVersionUID = -6106463380631469363L;

	public Header() {
		Button groupBtn = ComFactory.getTertriayBtn("群组管理");
		groupBtn.addClickListener(e ->{
			getUI().ifPresent(ui -> ui.navigate("/group"));
		});
		groupBtn.getStyle().set("margin-top", "20px");
    	
    	Button friendBtn = ComFactory.getTertriayBtn("好友管理");
    	friendBtn.addClickListener(e ->{
    		getUI().ifPresent(ui -> ui.navigate("/friends"));
    	});
    	friendBtn.getStyle().set("margin-top", "20px");
    	
    	Button userBtn = ComFactory.getTertriayBtn("用户管理");
    	userBtn.addClickListener(e ->{
    		getUI().ifPresent(ui -> ui.navigate("/userView"));
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
		wrapper.add(groupBtn,friendBtn,userBtn,label,standardButton);
//    			wrapper.setAlignItems(FlexComponent.Alignment.START);
		wrapper.getStyle().set("height", "40px");
		wrapper.setSpacing(false);
		add(wrapper);
	}

}
