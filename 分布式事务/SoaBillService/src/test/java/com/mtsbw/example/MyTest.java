package com.mtsbw.example;

import java.text.MessageFormat;
import java.util.regex.Matcher;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String stringFormat = "提示：生成{0}所需的{1}不能为空，请核实";
//		
//		// System.out.println(MessageFormat.format(stringFormat, 123, 100));
		
//		int a = Integer.parseInt("1111",2);
//		int b = Integer.parseInt("0100",2);
//		// System.out.println(   a+","+ b + "," + (a&b) );
//		// System.out.println(      Integer.toString(a&b,2));
		
		String a = "0111";
		boolean flag1 = a.matches("1*");
		boolean flag2 = a.matches("[0-1]1*");
		boolean flag3 = a.matches("[0-1]{2}1*");
		boolean flag4 = a.matches("[0-1]{3}1*");
		// System.out.println(flag1);
		// System.out.println(flag2);
		// System.out.println(flag3);
		// System.out.println(flag4);
	}

}
