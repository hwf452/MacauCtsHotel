/**
 * 
 */
package com.halong.macauctshotel.common;


/**
 * @author 锘 创建时间：2013-12-29 下午11:21:59
 * 
 */
public class ToDBC {

	public static String toDBC(String string) {
		char[] c = string.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}

			if (c[i] > 65280 && c[i] < 65375) {
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}

}
