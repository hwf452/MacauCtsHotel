package com.halong.macauctshotel.common;

import com.halong.macauctshotel.util.SharedPreferencesHelper;

import android.content.Context;
import android.util.Log;

public class SaveInfoUtil {

	public static final String CODE_KEY = "code";
	public static final String PASSWORD_KEY = "password";
	public static final String SAVELOGIN_KEY_STRING = "savelogin";
	public static final String AUTOLOGIN_KEY_STRING = "autologin";
	public static final String CUSTID_KEY_STRING = "custid";
	public static final String CARDNO_KEY_STRING = "cardno";
	public static final String EMAIL_KEY_STRING = "email";

	/**
	 * 清除保存的数据
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferencesHelper.clear(context);
	}

	/**
	 * 是否已登录
	 * 
	 * @param context
	 * @return true已登录 false未登录
	 */
	public static boolean isLogin(Context context) {
		long custid = SharedPreferencesHelper.getLongValue(context, SaveInfoUtil.CUSTID_KEY_STRING);
		Log.v("custid", custid+"");
		if (custid != -1) {
			return true;
		}
		return false;
	}
}
