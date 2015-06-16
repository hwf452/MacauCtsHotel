/**
 * 
 */
package com.halong.macauctshotel.common;

import android.app.Activity;
import android.content.Intent;

/**
 * 
 */
public class ToOtherActivity extends Activity {
	/**
	 * 
	 */
	public void toOtherActivity(Class<?> activity) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, activity);
		startActivity(intent);
	}
}
