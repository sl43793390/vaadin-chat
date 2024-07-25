package com.example.application.component.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.example.entity.Permission;
import com.example.entity.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.server.VaadinSession;

@Service
public class ComponentUtil implements ApplicationContextAware{
	
	private static Logger logger = LoggerFactory.getLogger(ComponentUtil.class);
	
	public static ApplicationContext applicationContext;
	
    private static final String CLASSPATH = "com.example.";


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	
	
	public static  User getCurrentUser(){
		VaadinSession current = VaadinSession.getCurrent();
		if (null == current || null == VaadinSession.getCurrent().getAttribute("user")) {
			return null;
		}
		return (User) VaadinSession.getCurrent().getAttribute("user");
	}
	
	public static  String getCurrentUserId(){
		VaadinSession current = VaadinSession.getCurrent();
		if (null == current || null == VaadinSession.getCurrent().getAttribute("user")) {
			return null;
		}
		return ((User) VaadinSession.getCurrent().getAttribute("user")).getUserId();
	}
	
	public static  String getCurrentInstitutionRole(){
		return VaadinSession.getCurrent().getAttribute("CURRENT_INST_ROLE").toString();
	}
	

    
    /**
     * createComponent:(工厂模式生成对应的CommonComponent)；<br/>
     * className；<br/>
     * CommonComponent)；<br/>
     * 
     * @author 
     */
    public static Component createComponent(String  className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(CLASSPATH + className);
        } catch (ClassNotFoundException e) {
            logger.error("className:ComponentFactory,methodName:createComponent,message:{}", e);
        }

        if (clazz == null) {
            return null;
        }

        Component bean = null;
        try {
            bean = (Component) applicationContext.getBean(clazz);
        } catch (BeansException e1) {
            try {
                bean = (Component) clazz.newInstance();
                return bean;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            e1.printStackTrace();
        }
        if (bean == null) {
            try {
                bean = (Component) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
   
	public static String getCurrentInstitution() {
		if (null == getCurrentUser()) {
			return "";
		}
		return getCurrentUser().getIdInstitution();
	}
	public static String getCurrentUserName() {
		return getCurrentUser().getUserName();
	}
	
	public static <T> T getSelectedItemForGrid(Grid<T> grid) {
		Set<T> selectedItems = grid.getSelectedItems();
		if (null == selectedItems || selectedItems.isEmpty()) {
			return null;
		}
		for (T t : selectedItems) {
			return t;
		}
		return null;
	}
	
	public static void populateTreeGridContent(TreeGrid<Permission> treeGrid,List<Permission> permissions) {
		TreeDataProvider<Permission> dataProvider = (TreeDataProvider<Permission>) treeGrid.getDataProvider();
		TreeData<Permission> treeData = dataProvider.getTreeData();
		for (Permission pid : permissions) {
			if (pid.getIdParent() == null) {
				treeData.addRootItems(pid);
				List<Permission> children = permissions.stream().filter(e -> e.getIdParent()!= null && e.getIdParent().equals(pid.getPermissionId())).collect(Collectors.toList());
				for (Permission cid : children) {
					treeData.addItem(pid, cid);
				}
				treeGrid.expand(pid);
			}
			else {
				List<Permission> children = permissions.stream().filter(e -> e.getIdParent()!= null && e.getIdParent().equals(pid.getPermissionId())).collect(Collectors.toList());
				for (Permission cid : children) {
					treeData.addItem(pid, cid);
				}
			}
		}
	}
	/**
	 * 生成树结构并带有选中的ID
	 * 如果要使用该方法需实现 StructuredEntity 接口重写两个方法即可
	 * @param treeGrid
	 * @param permissions
	 */
	public static void populateTreeGridContentWithSelectItems(TreeGrid<Permission> treeGrid,List<Permission> permissions,List<String> selectedIds) {
		TreeDataProvider<Permission> dataProvider = (TreeDataProvider<Permission>) treeGrid.getDataProvider();
		TreeData<Permission> treeData = dataProvider.getTreeData();
		treeData.clear();
		permissions.forEach(p -> treeGrid.deselect(p));//要先将原来选中的全部设置为未选中，treeData.clear方法只能清除数据但是不能取消选择，可能是个bug;
		for (Permission pid : permissions) {
			if (pid.getIdParent() == null) {
				treeData.addItem(null, pid);
				if (selectedIds.contains(pid.getId())) {
					treeGrid.select(pid);
				}
				List<Permission> children = permissions.stream().filter(e -> e.getIdParent()!= null && e.getIdParent().equals(pid.getId())).collect(Collectors.toList());
				for (Permission cid : children) {
					treeData.addItem(pid, cid);
					if (selectedIds.contains(cid.getId())) {
						treeGrid.select(cid);
					}
				}
			}else {
				List<Permission> children = permissions.stream().filter(e -> e.getIdParent()!= null && e.getIdParent().equals(pid.getId())).collect(Collectors.toList());
				for (Permission cid : children) {
					treeData.addItem(pid, cid);
					if (selectedIds.contains(cid.getId())) {
						treeGrid.select(cid);
					}
				}
			}
		}
//		treeGrid.expand(permissions);
	}
//	public static void populateTreeGridtWithSelectItems(TreeGrid<Permission> treeGrid,List<Permission> permissions,List<String> selectedIds) {
//		 TreeData<Permission> treeData = new TreeData<>();
//		 treeGrid.setTreeData(treeData);
//		permissions.forEach(p -> treeGrid.deselect(p));//要先将原来选中的全部设置为未选中，treeData.clear方法只能清除数据但是不能取消选择，可能是个bug;
//		for (Permission pid : permissions) {
//			if (pid.getIdParent() == null) {
//				treeData.addItem(null, pid);
//				if (selectedIds.contains(pid.getId())) {
//					treeGrid.select(pid);
//				}
//				List<Permission> children = permissions.stream().filter(e -> e.getIdParent()!= null && e.getIdParent().equals(pid.getId())).collect(Collectors.toList());
//				for (Permission cid : children) {
//					treeData.addItem(pid, cid);
//					if (selectedIds.contains(cid.getId())) {
//						treeGrid.select(cid);
//					}
//				}
//			}else {
//				List<Permission> children = permissions.stream().filter(e -> e.getIdParent()!= null && e.getIdParent().equals(pid.getId())).collect(Collectors.toList());
//				for (Permission cid : children) {
//					treeData.addItem(pid, cid);
//					if (selectedIds.contains(cid.getId())) {
//						treeGrid.select(cid);
//					}
//				}
//			}
//		}
////		treeGrid.expand(permissions);
//	}
	
	/**
	 * 生成树结构并带有选中的ID
	 * @param treeGrid
	 * @param permissions
	 */
	public static void populateGridContentWithSelectItems(Grid<User> grid,List<User> users,List<String> selectedIds) {
		grid.setItems(users);
		List<User> sel = users.stream().filter(e ->selectedIds.contains(e.getUserId())).collect(Collectors.toList());
		for (User u : sel) {
			grid.select(u);
		}
	}

	public static void initComponent(CommonComponent com) {
		com.initLayout();
		com.initContent();
		com.registerHandler();
	}
    /**
     * 传入要保留的列，名字是泛型对象的属性名；
     * @param grid
     * @param visiableColumns
     */
	public static void filterGridColumns(GridPro<?> grid,List<String> visiableColumns) {
		grid.getColumns().forEach(column -> {
			String key = column.getKey();
			if (null != key && !visiableColumns.contains(key)) {
				grid.removeColumnByKey(key);
			}
		});
	}
	/**
	 * 移除不是null的列，注意：自定义的列没有key，column.getKey()是null
	 * @param grid
	 */
	public static void removeNotNullColumns(GridPro<?> grid) {
		grid.getColumns().forEach(column -> {
			String key = column.getKey();
			if (null != key) {
				grid.removeColumnByKey(key);
			}
		});
	}
	
	public static void filterCurdColumns(Grid<?> grid,List<String> visiableColumns) {
		grid.getColumns().forEach(column -> {
			String key = column.getKey();
			if (!visiableColumns.contains(key)) {
				grid.removeColumnByKey(key);
			}
		});
	}
}
