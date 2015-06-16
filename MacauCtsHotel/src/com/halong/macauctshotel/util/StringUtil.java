package com.halong.macauctshotel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 将格式2014-08-14T12:00:00+08:00 转为 2014-08-14 12:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate(String date) {
		date = date.replace("T", " ");
		int i = date.indexOf("+");
		date = date.substring(0, i);
		return date;
	}

	public static boolean isEmail(String email) {
		Pattern p = Pattern.compile("[a-zA-Z0-9-_]+@+[a-zA-Z0-9]+.+[a-zA-Z0-9]");
		Matcher m = p.matcher(email);// 这种也是可以的！
		boolean b = m.matches();
		if (b) {
			return true;
		} else
			return false;
	}
	
	/**
	 * 手机号验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) { 
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches(); 
		return b;
	}
	
	/**
	 * 电话号码验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) { 
		Pattern p1 = null,p2 = null;
		Matcher m = null;
		boolean b = false;  
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
		if(str.length() >9)
		{	m = p1.matcher(str);
 		    b = m.matches();  
		}else{
			m = p2.matcher(str);
 			b = m.matches(); 
		}  
		return b;
	}
	
	/**
	 * 字符全角化
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		   char[] c = input.toCharArray();
		   for (int i = 0; i< c.length; i++) {
		       if (c[i] == 12288) {
		         c[i] = (char) 32;
		         continue;
		       }if (c[i]> 65280&& c[i]< 65375)
		          c[i] = (char) (c[i] - 65248);
		       }
		   return new String(c);
		}

}
