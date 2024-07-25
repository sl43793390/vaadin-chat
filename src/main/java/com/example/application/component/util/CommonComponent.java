package com.example.application.component.util;

import com.vaadin.flow.component.Component;


public abstract class CommonComponent extends Component{
	
		private static final long serialVersionUID = -1489796323160495508L;
		

		public CommonComponent(){
			super();
		}
		
		public abstract void initLayout();
		
		public abstract void initContent();
		
		public abstract void registerHandler();
		
		
		
}
