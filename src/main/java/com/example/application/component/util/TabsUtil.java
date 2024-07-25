package com.example.application.component.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.application.views.AppLayoutAccordionView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.Element;

import cn.hutool.core.collection.CollectionUtil;

public class TabsUtil {

	
	private static final Logger log = LoggerFactory.getLogger(TabsUtil.class);

	public static final String CAPTION_ADD_ROLE = "新增角色";
	public static final String CAPTION_ADD_USER = "新增用户";
	public static final String CAPTION_MODIFY_USER = "修改用户";

	public static final String CAPTION_USER_MANAGEMENT = "用户管理";

	public static final String CAPTION_ROLE_MANAGEMENT = "角色管理";

	public static void removeAllTabs(TabSheet tabsheet) {
		Stream<Component> children = tabsheet.getChildren();
		children.forEach(e -> tabsheet.remove(e));
	}
	public static Component getComponentByName(TabSheet tabsheet,String name) {
		Stream<Component> children = tabsheet.getChildren();
		List<Component> filter = children.filter(e ->{
			if (!(e instanceof Tabs)) {
				if(e.getId().get().equals(name)){
					return true;
				}
				return false;
			}
			return false;
		}).collect(Collectors.toList());
		if (CollectionUtil.isNotEmpty(filter)) {
			return filter.get(0);
		}
		return null;
	}
	public static Component getComponentByName(String name) {
		TabSheet tabsheet = getTabsheet();
		Stream<Component> children = tabsheet.getChildren();
		List<Component> filter = children.filter(e ->{
			if (!(e instanceof Tabs)) {
				if(e.getId().get().equals(name)){
					return true;
				}
				return false;
			}
			return false;
		}).collect(Collectors.toList());
		if (CollectionUtil.isNotEmpty(filter)) {
			return filter.get(0);
		}
		return null;
	}
	public static boolean checkTabExists(TabSheet tabsheet,String name) {
		Map<Tab,Element> content = tabsheet.getContent();
		for (Entry<Tab, Element> entry : content.entrySet()) {
			Tab key = entry.getKey();
			if (key.getId().equals(name)) {
				return true;
			}
		}
		return false;
	}
	public static void setSelectedTab(String name) {
		Tab tabByName = getTabByName(name);
		getTabsheet().setSelectedTab(tabByName);
	}
	public static boolean checkTabExists(String name) {
		Map<Tab,Element> content = getTabsheet().getContent();
		for (Entry<Tab, Element> entry : content.entrySet()) {
			Tab key = entry.getKey();
			if (key.getId().equals(name)) {
				return true;
			}
		}
		return false;
	}
	public static void removeAllTabs() {
		TabSheet tabsheet = getTabsheet();
		Stream<Component> children = tabsheet.getChildren();
		children.forEach(e -> tabsheet.remove(e));
	}
	public static Tab getTabByName(String name) {
		TabSheet tabsheet = getTabsheet();
		Map<Tab,Element> content = tabsheet.getContent();
		for (Entry<Tab, Element> entry : content.entrySet()) {
			Tab key = entry.getKey();
			if (key.getId().equals(name)) {
				return key;
			}
		}
		return null;
	}
//	public static boolean checkTabExists(String name) {
//		MyTabsheet tabsheet = getTabsheet();
//		Component tabByName = getComponentByName(tabsheet,name);
//		if (null != tabByName) {
//			return true;
//		}
//		return false;
//	}
	
	public static TabSheet getTabsheet() {
		TabSheet mainTabsheet = ComponentUtil.applicationContext.getBean(AppLayoutAccordionView.class).getMainTabsheet();
		return mainTabsheet;
	}
	
	/**
	 * add tab
	 * @param tabs
	 * @param text
	 */
	public static void addTab(TabSheet tabs, String text){
		Tab t = new Tab(new TabHeader(tabs, text));
		tabs.add(t,new Label("无内容"));
		tabs.setSelectedTab(t);
		t.setId(text);
	}
	public static void addComponentTab(TabSheet tabs, String text,Component com){
		Tab t = new Tab(new TabHeader(tabs, text));
		t.setId(text);//必须放在tabsheet的add方法之前，否则会有奇怪的bug；
		com.setId(text);//必须放在tabsheet的add方法之前，否则会有奇怪的bug；
		tabs.add(t,com);
		tabs.setSelectedTab(t);
	}
	public static void addComponentTab(String text,Component com){
		TabSheet tabsheet = getTabsheet();
		Tab t = new Tab(new TabHeader(tabsheet, text));
		t.setId(text);//必须放在tabsheet的add方法之前，否则会有奇怪的bug；
		com.setId(text);//必须放在tabsheet的add方法之前，否则会有奇怪的bug；
		tabsheet.add(t,com);
		tabsheet.setSelectedTab(t);
	}
	public static void addComponentTab(TabSheet tabs, String text,Component com,boolean closeable){
		Tab t = new Tab(new TabHeader(tabs, text,closeable));
		t.setId(text);//必须放在tabsheet的add方法之前，否则会有奇怪的bug；
		com.setId(text);//必须放在tabsheet的add方法之前，否则会有奇怪的bug；
		tabs.add(t,com);
		tabs.setSelectedTab(t);
	}
	public static boolean closeTargetTab(String title) {
		try {
			Tab tabByName = getTabByName(title);
			getTabsheet().remove(tabByName);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public static void closeCurrentTab() {
		TabSheet tab = getTabsheet();
		tab.remove(tab.getSelectedTab());
	}
	
}
