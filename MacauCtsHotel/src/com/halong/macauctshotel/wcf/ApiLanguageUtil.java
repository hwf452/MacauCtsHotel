package com.halong.macauctshotel.wcf;
 
import java.util.Locale;

import android.content.Context;

import com.halong.macauctshotel.MainActivity;
import com.halong.macauctshotel.R;
import com.halong.macauctshotel.util.SharedPreferencesHelper;

public class ApiLanguageUtil  {
	
	private static Context mContext;
	public static String CN="CN";
	public static String EN="EN";
	
	public static void setmContext(Context mContext) {
		ApiLanguageUtil.mContext = mContext;
	}
 
	/**
	 * 获取语言
	 * 
	 * @return R.id.XXXXX
	 */
	public static int getLanguage() {
		int languaeChoiceID = SharedPreferencesHelper.getIntValue(mContext,
				MainActivity.CHOICELANGUAGESTRING, R.id.defaultRdo);

		if (languaeChoiceID != R.id.defaultRdo) {
			return languaeChoiceID;
		}

		Locale locale = mContext.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh")) {
			return R.id.rcnRdo;
		} else if (language.endsWith("en")) {
			return R.id.enRdo;
		} else {
			return R.id.rcnRdo;
		}
	}
	
	
	/**
	 * 获取语言
	 * 
	 * @return String 
	 */
	public static String getStringLanguage() {
		int languaeChoiceID = SharedPreferencesHelper.getIntValue(mContext,
				MainActivity.CHOICELANGUAGESTRING, R.id.defaultRdo);
		
		if (languaeChoiceID != R.id.defaultRdo) {
			if (languaeChoiceID == R.id.rcnRdo) {
				return CN;
			} else if (languaeChoiceID == R.id.enRdo) {
				return EN;
			}
		}
		
		Locale locale = mContext.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh")) {
			return CN;
		} else if (language.endsWith("en")) {
			return EN;
		} else {
			return CN;
		}
	}
	
	
}
