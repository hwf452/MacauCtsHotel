/**
 * 
 */
package com.halong.macauctshotel.common;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * 
 */
public class FragmentToOtherActivity extends Fragment {
	/**
 * 
 */
	public void toOtherActivity(Class<?> activity) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(getActivity(), activity);
		startActivity(intent);
	}
}
