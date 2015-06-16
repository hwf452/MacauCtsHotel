package com.halong.macauctshotel.ver;


import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/***
 * MyApplication
 * 
 * @author zhangjia
 * 
 */
public class MyApplication extends Application {

	public static int localVersion = 0;// 本地安装版本

	public static int serverVersion = 2;// 服务器版本

	public static String downloadDir = "jj/";// 安装目录

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			PackageInfo packageInfo = getApplicationContext()
					.getPackageManager().getPackageInfo(getPackageName(), 0);
			localVersion = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

}
