package com.example.application.component.util;

import java.time.LocalDate;

import com.vaadin.componentfactory.EnhancedRichTextEditor;
import com.vaadin.componentfactory.TreeComboBox;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog.CancelEvent;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog.ConfirmEvent;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.data.provider.AbstractDataProvider;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.function.ValueProvider;

public class ComFactory {
	
	/**
	 * 创建一个水平布局layout ，该layout放入多个组合默认靠左侧依顺序放置
	 * 注意：水平布局的layout，内部组件需要设置宽度，否则每个就会占据所有宽度，效果和verticalLayout一样了。
	 * @return
	 */
	public static HorizontalLayout  getResponsiveHorizontalLayoutLeft(){
		HorizontalLayout layout = new HorizontalLayout();
		layout.setJustifyContentMode(JustifyContentMode.START);
		layout.addClassName("list-view-horizontalLayout");
		layout.setWidth("100%");
		return layout;
	}
	/**
	 * 创建一个水平布局layout ，该layout放入多个组合默认靠右侧侧依顺序放置
	 * 注意：水平布局的layout，内部组件需要设置宽度，否则每个就会占据所有宽度，效果和verticalLayout一样了。
	 * @return
	 */
	public static HorizontalLayout  getResponsiveHorizontalLayoutRight(){
		HorizontalLayout layout = new HorizontalLayout();
		layout.setJustifyContentMode(JustifyContentMode.END);
		layout.addClassName("list-view-horizontalLayout");
		layout.setWidth("100%");
		return layout;
	}
	/**
	 * 创建一个水平布局layout ，该layout放入多个组合默认居中侧侧依顺序放置
	 * 注意：水平布局的layout，内部组件需要设置宽度，否则每个就会占据所有宽度，效果和verticalLayout一样了。
	 * @return
	 */
	public static HorizontalLayout  getResponsiveHorizontalLayoutMiddle(){
		HorizontalLayout layout = new HorizontalLayout();
		layout.setJustifyContentMode(JustifyContentMode.CENTER);
		layout.addClassName("list-view-horizontalLayout");
		layout.setWidth("100%");
		return layout;
	}
	
	public static FormLayout getFormLayout() {
		FormLayout form = new FormLayout();
		return form;
	}
	/**
	 * 创建formlayout并加入component
	 * @param com
	 * @return
	 */
	public static FormLayout getFormLayout(Component... com) {
		FormLayout form = new FormLayout(com);
		return form;
	}
	
	public static VerticalLayout getVerticalLayout() {
		VerticalLayout form = new VerticalLayout();
		form.setJustifyContentMode(JustifyContentMode.START);
		return form;
	}
	
	public static VerticalLayout getVerticalLayout(JustifyContentMode align) {
		VerticalLayout form = new VerticalLayout();
		form.setJustifyContentMode(align);
		return form;
	}
	/**
	 * 
	 * @param master
	 * @param detail
	 * @param masterPercent 第一个占比，如70
	 * @return
	 */
	public static SplitLayout getVerticalSplitLayout(Component master,Component detail,int masterPercent) {
		SplitLayout splitLayout = new SplitLayout(master, detail);
		splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
		// Sets the width for the first child to 70%, giving
		// the second child the remaining width of 30%
		splitLayout.setSplitterPosition(masterPercent);
		return splitLayout;
	}
	/**
	 * 
	 * @param master
	 * @param detail
	 * @param masterPercent 第一个占比，如70
	 * @return
	 */
	public static SplitLayout getVerticalSplitLayout2(Component master,Component detail) {
		SplitLayout splitLayout = new SplitLayout(master, detail);
		splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
		return splitLayout;
	}
	/**
	 * 
	 * @param master
	 * @param detail
	 * @param masterPercent 第一个占比，如70
	 * @return
	 */
	public static SplitLayout getHoriSplitLayout(Component master,Component detail,int masterPercent) {
		SplitLayout splitLayout = new SplitLayout(master, detail);
		splitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
		// Sets the width for the first child to 70%, giving
				// the second child the remaining width of 30%
				splitLayout.setSplitterPosition(masterPercent);
		return splitLayout;
	}
	
	public static Button getPrimaryBtn() {
		Button btn = new Button();
		btn.addClassName("vaadin-button-back");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		return btn;
	}
	
	public static Button getPrimaryBtn(VaadinIcon icon) {
		Button btn = new Button();
		btn.setIcon(new Icon(icon));
		btn.addClassName("vaadin-button-back");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		return btn;
	}
	
	public static Button getPrimaryBtn(String caption) {
		Button btn = new Button(caption);
		btn.addClassName("vaadin-button-back");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		return btn;
	}
	public static Button getPrimaryBtn(String caption,String width) {
		Button btn = new Button(caption);
		btn.setWidth(width);
		btn.addClassName("vaadin-button-back");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		return btn;
	}
	
	public static NativeButton getNativeButton(String caption) {
		NativeButton btn = new NativeButton(caption);
		return btn;
	}
	
	public static Button getTertriayBtn(String caption) {
		Button btn = new Button(caption);
		btn.addClassName("vaadin-button-back");
		btn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		return btn;
	}
	
	public static Button getTertriayBtn(String caption,VaadinIcon icon) {
		Button btn = new Button(caption);
		btn.setIcon(new Icon(icon));
		btn.addClassName("vaadin-button-back");
		btn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		return btn;
	}
	
	public static Button getImageBtn(String src,String width) {
		Image img = new Image(src, "");
		img.setWidth(width);

		Button imgButton = new Button(img);
		imgButton.addThemeVariants(ButtonVariant.LUMO_ICON);
		return imgButton;
	}
	/**
	 * 白底蓝字
	 * @param caption
	 * @return
	 */
	public static Button getSmallBtn(String caption) {
		Button smallButton = new Button(caption);
		smallButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		return smallButton;
	}
	/**
	 * 白底蓝字
	 * @param caption
	 * @return
	 */
	public static Button getLargeBtn(String caption) {
		Button smallButton = new Button(caption);
		smallButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
		return smallButton;
	}
	
	public static Checkbox getCheckbox(String caption) {
		Checkbox ck = new Checkbox(caption);
		return ck;
	}
	
	public static HorizontalLayout getTitle(String title) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setHeight("40px");
		Label lb = new Label(title);
		lb.addClassName("lb-title");
		layout.add(lb);
		return layout;
	}
	
	public static Label getTitleLabel(String title) {
		Label lb = new Label(title);
		lb.addClassName("lb-title");
		return lb;
	}
	public static Label getTitleLabel() {
		Label lb = new Label();
		lb.addClassName("lb-title");
		return lb;
	}
	
	public static Label getHorizontalLine() {
		Label lb = new Label();
		lb.getStyle().set("height", "1px").set("width", "90%").set("background-color", "gray");
		return lb;
	}
	
	public static Label getHorizontalLine(String widthPercent) {
		Label lb = new Label();
		lb.getStyle().set("height", "1px").set("width", widthPercent).set("background-color", "gray");
		return lb;
	}
	
	public static Label getLabel(String text) {
		Label lb = new Label(text);
		lb.setHeight("40px");
		lb.addClassName("lb-width-common");
		return lb;
	}
	
	public static Label getLabel() {
		Label lb = new Label();
		lb.setHeight("40px");
		lb.addClassName("lb-width-common");
		return lb;
	}
	
	public static Label getLabel(String text,String width) {
		Label lb = new Label(text);
		lb.setHeight("40px");
		lb.setWidth(width);
		lb.addClassName("lb-width-common");
		return lb;
	}
	
	public static Label getBlankLabel(String width) {
		Label lb = new Label();
		lb.setHeight("40px");
		lb.setWidth(width);
		return lb;
	}
	
	public static TabSheet getTabSheet() {
		TabSheet tabs = new TabSheet();
		tabs.setWidthFull();
		return tabs;
	}
	public static void addTab(TabSheet tabs,String text){
		Tab t = new Tab(new TabHeader(tabs, text));
		tabs.add(t,new Label(text));
		tabs.setSelectedTab(t);
	}
	public static void addComponentTab(TabSheet tabs,String text,Component com){
		Tab t = new Tab(new TabHeader(tabs, text));
		tabs.add(t,com);
		tabs.setSelectedTab(t);
	}
	public static void addComponentTab(TabSheet tabs,String text,Component com,boolean closeable){
		Tab t = new Tab(new TabHeader(tabs, text,closeable));
		tabs.add(t,com);
		tabs.setSelectedTab(t);
	}
	
	public static <T> Grid<T> getGrid(Class<T> clazz) {
		Grid<T> grid = new Grid<T>(clazz);
		grid.removeAllColumns();
		return grid;
	}
	
	public static <T> TreeGrid<T> getTreeGrid(Class<T> clazz,SelectionMode mode) {
		TreeGrid<T> grid = new TreeGrid<T>(clazz);
		grid.removeAllColumns();
		grid.setSelectionMode(mode);
		return grid;
	}
	/**
	 * 
	 * @param <T>因为grid 默认展示泛型对象的所不是null的列，一般使用都是指定展示那些列，所以从该处默认删除了所有默认的列；
	 * 若需要保留则调用另一个方法@getGridProWithAllColumn，若需要保留部分列可调用componentUtil里的方法@filterGridColumns
	 * @param clazz 存放类型
	 * @param editable 是否可编辑
	 * @return
	 */
	public static <T> GridPro<T> getGridPro(Class<T> clazz,boolean editable) {
		GridPro<T> grid = new GridPro<T>(clazz);
		grid.setEditOnClick(editable);//设置可编辑
		grid.removeAllColumns();
		return grid;
	}
	/**
	 * 保留默认泛型对象中的所有列，不做删除，需要删除后的grid 见另一个方法@getgridPro
	 * @param <T>
	 * @param clazz
	 * @param editable
	 * @return
	 */
	public static <T> GridPro<T> getGridProWithAllColumn(Class<T> clazz,boolean editable) {
		GridPro<T> grid = new GridPro<T>(clazz);
		grid.setEditOnClick(editable);//设置可编辑
		return grid;
	}
	
	public static <T> Crud<T> getCrud(Class<T> clazz,CrudEditor<T> editor) {
		Crud<T> crud = new Crud<T>(clazz,editor);
		return crud;
	}
	
	public static <T> Crud<T> getCrud(Class<T> clazz,CrudEditor<T> editor,AbstractDataProvider<T, ?> dataProvider) {
		Crud<T> crud = new Crud<T>(clazz,editor);
		crud.setDataProvider(dataProvider);
		return crud;
	}
	
	public static TextField getTextFieldWithClearBtn(String caption) {
		TextField com1 = new TextField(caption);
		com1.setClearButtonVisible(true);
		return com1;
	}
	public static TextField getTextField(String caption) {
		TextField com1 = new TextField(caption);
		return com1;
	}
	
	public static PasswordField getPasswordField() {
		PasswordField field = new PasswordField();
		return field;
	}
	public static PasswordField getPasswordField(String caption) {
		PasswordField field = new PasswordField(caption);
		return field;
	}
	
	public static TextField getTextField() {
		TextField com1 = new TextField();
		return com1;
	}
	
	public static ComboBox<String> getComboboxWithItems(String caption,String... items){
		ComboBox<String> com2 = new ComboBox<>(caption);
		com2.setItems(items);
		return com2;
	}
	public static ComboBox<String> getCombobox(String caption){
		ComboBox<String> com2 = new ComboBox<>(caption);
		return com2;
	}
	public static ComboBox<String> getCombobox(){
		ComboBox<String> com2 = new ComboBox<>();
		return com2;
	}
	public static MultiSelectComboBox<String> getComboboxMultiSelect(){
		MultiSelectComboBox<String> com2 = new MultiSelectComboBox<>();
		return com2;
	}
	public static MultiSelectComboBox<String> getComboboxMultiSelect(String caption){
		MultiSelectComboBox<String> com2 = new MultiSelectComboBox<>(caption);
		return com2;
	}
	
	public static <T> MultiSelectComboBox<T> getComboboxMultiSelect(Class<T> clazz){
		MultiSelectComboBox<T> com2 = new MultiSelectComboBox<T>();
		return com2;
	}
	public static <T> MultiSelectComboBox<T> getComboboxMultiSelect(String caption,Class<T> clazz){
		MultiSelectComboBox<T> com2 = new MultiSelectComboBox<T>(caption);
		return com2;
	}
	
	public static MultiSelectListBox<String> getListBox(String... items) {
		MultiSelectListBox<String> listBox = new MultiSelectListBox<>();
        listBox.setItems(items);
        return listBox;
	}
	
	public static MultiSelectListBox<String> getListBox() {
		MultiSelectListBox<String> listBox = new MultiSelectListBox<>();
		return listBox;
	}
	
	public static <T> ComboBox<T> getCombobox(Class<T> clazz){
		ComboBox<T> com2 = new ComboBox<T>();
		return com2;
	}
	/**
	 * 树结构的下拉
	 * @param <T>
	 * @param treeData
	 * @param valueProvider
	 * @return
	 */
	public static <T> TreeComboBox<T> getTreeCombobox(TreeData<T> treeData, ValueProvider<T, String> valueProvider){
		TreeComboBox<T> com = new TreeComboBox<T>(treeData,valueProvider);
		return com;
	}
	
	public static TextArea getTextArea() {
		TextArea area = new TextArea();
		area.setWidth("100%");
		return area;
	}
	
	public static TextArea getTextAreaWithHeight(String height) {
		TextArea area = new TextArea();
		area.setWidth("100%");
		area.setHeight(height);
		return area;
	}
	public static TextArea getTextArea(String caption) {
		TextArea area = new TextArea(caption);
		area.setWidthFull();
		return area;
	}
	//vaadin自带的富文本编辑器没有自动换行功能
	public static RichTextEditor getRichTextEditor(String width,String height) {
		RichTextEditor editor = new RichTextEditor();
		editor.getStyle().set("border", "solid 1px #dadada");
		editor.setHeight(height);
		editor.setWidth(width);
		return editor;
	}
	
	public static EnhancedRichTextEditor getTrixEditor(String height) {
		EnhancedRichTextEditor rte = new EnhancedRichTextEditor();
		rte.setWidth("620px");//620正好按钮分布两行
		rte.setHeight(height);
		return rte;
	}
	/**
	 * 默认宽度620px
	 * @return
	 */
	public static EnhancedRichTextEditor getTrixEditor() {
		EnhancedRichTextEditor rte = new EnhancedRichTextEditor();
		rte.setWidth("620px");//620正好按钮分布两行
		return rte;
	}
	
	public static RichTextEditor getRichTextEditor() {
		RichTextEditor editor = new RichTextEditor();
		editor.getStyle().set("border", "solid 1px #dadada");
		editor.setWidthFull();
		return editor;
	}
	
	public static RichTextEditor getRichTextArea(String width,String height) {
		return getRichTextEditor(width, height);
	}
	
	public static RichTextEditor getRichTextArea() {
		return getRichTextEditor();
	}
	/**
	 * 增强的富文本编辑器
	 * @param width
	 * @param height
	 * @return
	 */
	public static EnhancedRichTextEditor getEnhancedRichTextEditor(String width,String height) {
		EnhancedRichTextEditor editor = new EnhancedRichTextEditor();
		editor.setHeight(height);
		editor.setWidth(width);
		return editor;
	}
	/**
	 * 可传入保存取消按钮的对话框
	 * @param title
	 * @param saveButton
	 * @param cancelButton
	 * @return
	 */
	public static Dialog getDialog(String title,Button saveButton,Button cancelButton) {
		Dialog dialog = new Dialog();
		dialog.setCloseOnOutsideClick(false);
		dialog.setHeaderTitle(title);
		dialog.getFooter().add(saveButton);
		dialog.getFooter().add(cancelButton);
		return dialog;
	}
	/**
	 * 
	 * @param title 标题
	 * @param layout 内容布局
	 * @param saveButton 保存
	 * @param cancelButton 取消
	 * @return
	 */
	public static Dialog getDialog(String title,Component layout,Button saveButton,Button cancelButton) {
		Dialog dialog = new Dialog();
		dialog.setCloseOnOutsideClick(false);
		dialog.setHeaderTitle(title);
		dialog.add(layout);
		dialog.getFooter().add(saveButton);
		dialog.getFooter().add(cancelButton);
		return dialog;
	}
	
	/**
	 * 确认对话框,传入确认、取消事件实现即可
	 * @return 
	 */
	public static ConfirmDialog getConfirmationDialog(ComponentEventListener<ConfirmEvent> confirmListener,ComponentEventListener<CancelEvent> cancelListener) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("提示");
        dialog.setText("请确认是否要删除所选内容？");

        dialog.setConfirmText("确认");
        dialog.setConfirmButton("确认", confirmListener);
        dialog.setCancelable(true);
        dialog.setCancelButton("取消", cancelListener);
        
        return dialog;
	}
	public static ConfirmDialog getConfirmationDialog(String prompt,ComponentEventListener<ConfirmEvent> confirmListener,ComponentEventListener<CancelEvent> cancelListener) {
		ConfirmDialog dialog = new ConfirmDialog();
		dialog.setHeader("提示");
		dialog.setText(prompt);
		
		dialog.setConfirmText("确认");
		dialog.setConfirmButton("确认", confirmListener);
		dialog.setCancelable(true);
		dialog.setCancelButton("取消", cancelListener);
		
		return dialog;
	}
	
	public static Dialog getDialog(String title) {
		Dialog dialog = new Dialog();
		dialog.setCloseOnOutsideClick(false);
		dialog.setHeaderTitle(title);
		return dialog;
	}
	
	public static Avatar getBasicAvatar() {
		 Avatar avatarBasic = new Avatar();
		return avatarBasic;
	}
	public static Avatar getNamedAvatar(String name) {
        Avatar avatarName = new Avatar(name);
		return avatarName;
	}
	
	public static Avatar getNamedAvatar(String name,String pictureUrl) {
		Avatar avatarImage = new Avatar(name);
        avatarImage.setImage(pictureUrl);
        avatarImage.setTooltipEnabled(true);
		return avatarImage;
	}
	
	public static AvatarGroup getAvatarGroup() {
		 AvatarGroup avatarGroup = new AvatarGroup();
		return avatarGroup;

//	        for (Person person : people) {
//	            String name = person.getFirstName() + " " + person.getLastName();
//	            AvatarGroupItem avatar = new AvatarGroupItem(name);
//	            avatar.setColorIndex(colorIndex++);
//	            avatarGroup.add(avatar);
//	        }
	}
	/**
	 * 一个绿色对钩
	 * @return
	 */
	public static Icon getConfirmIcon() {
		Icon confirmed = createIcon(VaadinIcon.CHECK, "Confirmed");
		confirmed.getElement().getThemeList().add("badge success");
		return confirmed;
	}
	/**
	 * 一个红色的叉叉
	 * @return
	 */
	public static Icon getCancelIcon() {
		Icon cancelled = createIcon(VaadinIcon.CLOSE_SMALL, "Cancelled");
		cancelled.getElement().getThemeList().add("badge error");
		return cancelled;
	}
	
	private static Icon createIcon(VaadinIcon vaadinIcon, String label) {
	    Icon icon = vaadinIcon.create();
	    icon.getStyle().set("padding", "var(--lumo-space-xs");
	    // Accessible label
	    icon.getElement().setAttribute("aria-label", label);
	    // Tooltip
	    icon.getElement().setAttribute("title", label);
	    return icon;
	}
	/**
	 * 一个蓝色字体的标签，浅蓝色底色，看着像button。
	 * @param caption
	 * @return
	 */
	public static Span getNormalBadge(String caption) {
		Span pending = new Span(caption);
		pending.getElement().getThemeList().add("badge");
		return pending;
	}
	/**
	 * 一个绿色字体的标签，浅绿色底色，看着像button。
	 * @param caption
	 * @return
	 */
	public static Span getConfirmBadge(String caption) {
		Span confirmed = new Span(caption);
		confirmed.getElement().getThemeList().add("badge success");
		return confirmed;
	}
	/**
	 * 一个红色字体的标签，浅红色底色，看着像button。
	 * @param caption
	 * @return
	 */
	public static Span getErrorBadge(String caption) {
		Span denied = new Span(caption);
		denied.getElement().getThemeList().add("badge error");
		return denied;
	}
	/**
	 * 一个灰色字体的标签，浅灰色底色，看着像button。
	 * @param caption
	 * @return
	 */
	public static Span getGrayBadge(String caption) {
		Span onHold = new Span(caption);
		onHold.getElement().getThemeList().add("badge contrast");
		return onHold;
	}
	
	/**
	 * board 是一个响应式的布局，一行有四列，缩小会自动换行
	 * @return
	 */
	public static Board getBoard() {
		Board board = new Board();
		return board;
	}
	
	public static DatePicker getDateField(String caption,LocalDate initialDate) {
		DatePicker datep = new DatePicker(caption);
		datep.setValue(initialDate);
		return datep;
	}
	
	public static DatePicker getDatePicker(String caption,LocalDate initialDate) {
		return getDateField(caption, initialDate);
	}
	public static DatePicker getDatePicker(String caption) {
		DatePicker datep = new DatePicker(caption);
		return datep;
	}
	
	public static Board getBoardWithFourColumn(Component com1,Component com2,Component com3,Component com4) {
		Board board = new Board();
		Row row1 = new Row();// 一行放四个列
		row1.add(com1,com2,com3,com4);
		return board;
	}
	
	public static void addBoardRow(Board bo,Component com1,Component com2,Component com3,Component com4) {
		Row row = new Row();
		row.add(com1,com2,com3,com4);
		bo.add(row);
	}
	
	/**
	 * 用户自己写拒绝回调函数：传入保存目录
	 * 上传完成后默认不能继续上传，需要调用		upload.clearFileList();方法清空缓存
	 * //失败添加监听器
	        upload.addFileRejectedListener(event -> {
	            String errorMessage = event.getErrorMessage();
	            Notification notification = Notification.show(errorMessage, 5000, Notification.Position.MIDDLE);
	            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
	        });
	 * @param uploadDir
	 * @return
	 */
	public static Upload getUpload(String uploadDir) {
		MultiFileBuffer multiFileBuffer = new MultiFileBuffer();
		Upload upload = new Upload(multiFileBuffer);
	    upload.setAutoUpload(true); //自动开始上传
	    UploadExamplesI18N i18n = new UploadExamplesI18N();
        upload.setI18n(i18n);
        upload.setMaxFiles(1);//默认只能同时上传一个文件
        //限制文件类型
//        upload.setAcceptedFileTypes("application/pdf", ".pdf",".jpg",".png","docx","xls","xlsx","zip");
//        int maxFileSizeInBytes = 100* 1024 * 1024; // 100MB/注意此处设置不一定起作用，spring本身会有大小拦截，需要配置在application.properties中
//        upload.setMaxFileSize(maxFileSizeInBytes);
        FileReceiverImpl fileReceiverImpl = new FileReceiverImpl(uploadDir);
        upload.addFinishedListener(fileReceiverImpl);
        upload.setReceiver(fileReceiverImpl);
        upload.addFailedListener(e -> Notification.show("上传文件失败", 5000, Notification.Position.MIDDLE));
        upload.addFileRejectedListener(e -> Notification.show("上传文件大小或类型不符合要求，请重新上传", 5000, Notification.Position.MIDDLE));
        return upload;
	}
	/**
	 * create an enpty upload without listener
	 * @param uploadDir
	 * @return
	 */
	public static Upload getUploadEmpty(String uploadDir) {
		MultiFileBuffer multiFileBuffer = new MultiFileBuffer();
		Upload upload = new Upload(multiFileBuffer);
		upload.setAutoUpload(true); //自动开始上传
		UploadExamplesI18N i18n = new UploadExamplesI18N();
		upload.setI18n(i18n);
		upload.setMaxFiles(1);//默认只能同时上传一个文件
		//限制文件类型
//        upload.setAcceptedFileTypes("application/pdf", ".pdf",".jpg",".png","docx","xls","xlsx","zip");
//        int maxFileSizeInBytes = 100* 1024 * 1024; // 100MB/注意此处设置不一定起作用，spring本身会有大小拦截，需要配置在application.properties中
//        upload.setMaxFileSize(maxFileSizeInBytes);
		upload.addFailedListener(e -> Notification.show("上传文件失败", 5000, Notification.Position.MIDDLE));
		upload.addFileRejectedListener(e -> Notification.show("上传文件大小或类型不符合要求，请重新上传", 5000, Notification.Position.MIDDLE));
		return upload;
	}
	
	public static ProgressBar getProgressBar() {
		ProgressBar progressBar = new ProgressBar();
		return progressBar;
	}
	
	public static Chart getChart(ChartType charttype) {
		Chart chart = new Chart(charttype);
		return chart;
	}
	public static TextField getStandardTextField(String string) {
		return getTextField(string);
	}
	public static ComboBox<String> getStandardComboBox(String string) {
		return getCombobox(string);
	}

	/**
	 * 默认灰底黑字的中间提示3秒
	 */
	public static void notification(String text) {
		Notification.show(text, 3000, Position.MIDDLE);
	}
	/**
	 * 传入提示位置提示3秒
	 * @param text
	 * @param position
	 */
	public static void notification(String text,Position position) {
		Notification.show(text, 3000, position);
	}
	/**
	 * 成功是绿底色的中间提示3秒
	 */
	public static void notificationSuccess(String text) {
		Notification show = Notification.show(text, 3000, Position.MIDDLE);
		show.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
	}
	/**
	 * 失败是红色的中间提示，且需手动关闭
	 */
	public static void notificationError(String text) {
		Notification notification = new Notification();
		notification.add(text);
		notification.setPosition(Position.MIDDLE);
		notification.setDuration(5000);
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		notification.open();
	}
	/**
	 * primary蓝色提示3秒
	 * @param text
	 */
	public static void notificationPrimary(String text) {
		Notification show = Notification.show(text, 3000, Position.MIDDLE);
		show.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
	}
	/**
	 * 自定义提示信息、提示时间、提示类型
	 * @param text
	 * @param duration
	 * @param variant
	 */
	public static void notificationWithVariant(String text,int duration,NotificationVariant variant) {
		Notification show = Notification.show(text, duration, Position.MIDDLE);
		show.addThemeVariants(variant);
	}
}

	