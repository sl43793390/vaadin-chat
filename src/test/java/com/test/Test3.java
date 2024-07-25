package com.test;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

import com.example.application.component.template.YearMonthInputFields;
import com.example.util.Util;

import cn.hutool.core.date.DateUtil;

public class Test3 {

	public static void main(String[] args) {

		Month[] values = Month.values();
		for (int i = 0; i < values.length; i++) {
			Month month = values[i];
			System.out.println(values[i]);
			String displayName = month.getDisplayName(TextStyle.FULL, Locale.getDefault());
			System.out.println(displayName);
		}
		
		YearMonthInputFields f = new YearMonthInputFields();
		System.out.println(f.getYear());
		System.out.println(f.getMonthLabel());
		
		Date date1 = DateUtil.parse("2033-06-29");
		Date date2 = DateUtil.parse("2023-06-29");
		  int betweenDays = (int) ((date1.getTime() - date2.getTime()) / (1000L*3600L*24L));
		  System.out.println(betweenDays);
		System.out.println(betweenDays/365.9);
	}

}
