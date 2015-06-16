package com.halong.macauctshotel.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelper {

	public final static String SHAREPREFENCENAME = "MacHotel";

	public static Boolean setStringValue(Context context, String key,
			String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFENCENAME, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	public static String getStringValue(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFENCENAME, 0);
		return sharedPreferences.getString(key, "");
	}

	public static Boolean setIntValue(Context context, String key,
			int defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFENCENAME, defaultValue);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, defaultValue);
		return editor.commit();
	}

	public static int getIntValue(Context context, String key, int defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFENCENAME, 0);
		return sharedPreferences.getInt(key, defaultValue);
	}

	public static int getIntValue(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFENCENAME, 0);
		return sharedPreferences.getInt(key, 0);
	}

	public static Boolean setLongValue(Context context, String key, Long value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFENCENAME, 0);
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	public static Long getLongValue(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFENCENAME, -1);
		return sharedPreferences.getLong(key, -1);
	}

	public static boolean clear(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFENCENAME, -1);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		return editor.commit();
	}

}
