package com.halong.macauctshotel.util;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

/**
 * 利用堆栈把activity保存起来，方便进行处�?
 * 
 * @author lihua
 * 
 */
public class AppStackManager {
	private static Stack<Activity> activityStack;
	private static AppStackManager instance;

	private AppStackManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppStackManager getMyAppStackManager() {
		if (instance == null) {
			instance = new AppStackManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆�?
	 */
	public void addActivity(Activity activity) {

		// System.out.println("123456");
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中�?���?��压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中�?���?��压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);

			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 保留第一个activity
	 */
	public void retainFristActivity() {
		for (int i = activityStack.size() - 1; 0 < i; i--) {
			if (null != activityStack.get(i)) {
				Log.v("retainFristActivity", activityStack.size() + "------"
						+ activityStack.get(i).getLocalClassName());
				activityStack.get(i).finish();
				activityStack.remove(activityStack.get(i));
			}
		}
	}

	/**
	 * 结束activityStack内所有的Activity，
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * �?��应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}

	/**
	 * 重启APP时执行一次
	 */
	public void clear() {
		if (activityStack == null) {
			return;
		}
		activityStack.clear();
	}
}
