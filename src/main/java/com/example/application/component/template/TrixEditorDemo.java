package com.example.application.component.template;

import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.application.component.util.ComFactory;
import com.vaadin.componentfactory.EnhancedRichTextEditor;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Service
@Scope("prototype")
public class TrixEditorDemo extends VerticalLayout {

	private static final long serialVersionUID = 4512289455462777038L;

	public TrixEditorDemo() {
		super();
		setHeight("700px");
		 setDefaultHorizontalComponentAlignment(Alignment.START); 
		initHeaderLayout();
	}

	private void initHeaderLayout() {
		add(ComFactory.getTitle("富文本2示例"));
		EnhancedRichTextEditor rte = new EnhancedRichTextEditor();
		rte.setHeight("600px");
		rte.setValue(ResourceBundle.getBundle("editorcontent").getString("content"));
		add(rte);
//		rte.setReadOnly(true);
		//隐藏某些按钮
//		EnhancedRichTextEditor rte = new EnhancedRichTextEditor();
//		Map<EnhancedRichTextEditor.ToolbarButton, Boolean> buttons = new HashMap<>();
//		buttons.put(EnhancedRichTextEditor.ToolbarButton.CLEAN, false);
//		buttons.put(EnhancedRichTextEditor.ToolbarButton.BLOCKQUOTE, false);
//		buttons.put(EnhancedRichTextEditor.ToolbarButton.CODE_BLOCK, false);
//		buttons.put(EnhancedRichTextEditor.ToolbarButton.IMAGE, false);
//		buttons.put(EnhancedRichTextEditor.ToolbarButton.LINK, false);
//		buttons.put(EnhancedRichTextEditor.ToolbarButton.STRIKE, false);
//		rte.setToolbarButtonsVisibility(buttons);
//		add(rte);
		
		
//		自定义按钮和行为
		Button textButton1 = new Button("自定义");
		textButton1.setIcon(VaadinIcon.AIRPLANE.create());
		textButton1.addClickShortcut(Key.F8);
		textButton1.getElement().setProperty("title", "Airplanes are flying machines.");
		textButton1.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		textButton1.addClickListener(event -> {
		    rte.addText("Airplanes are flying machines. ");
		});

		Button textButton2 = new Button("");
		textButton2.setIcon(VaadinIcon.DENTAL_CHAIR.create());
		textButton2.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		textButton2.getElement().setProperty("title", "Dentists are drilling people.");
		textButton2.addClickShortcut(Key.F9);
		textButton2.addClickListener(event -> {
		    String value = rte.getValue();
		    System.out.println(value);
		});

		rte.addCustomButtons(textButton1,textButton2);
		
	}

	
}
