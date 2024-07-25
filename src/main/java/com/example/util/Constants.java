package com.example.util;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	public static List<String> STATUS_LIST = new ArrayList<>() {
		private static final long serialVersionUID = 7633519757282814511L;
		{
			add("未开始");
			add("进行中");
			add("已结束");
			add("已中断");
			add("已废弃");
			add("未分配");
		}
	};
	
}
