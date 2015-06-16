package com.halong.macauctshotel;

import android.os.Bundle;
import android.os.Handler;

import com.halong.macauctshotel.common.ToOtherActivity;

public class WelcomeActivity extends ToOtherActivity {


		/**
		 * 等待时间
		 */
		private int DELAYMILLI = 2500;
		
		public static String mAppid;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_welcome);

			initWait();
		}

		/**
		 * loading状态，后台判断是否登录等操作
		 */
		private void initWait() {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					toOtherActivity(MainActivity.class);
					finish();
				}
			}, DELAYMILLI);
		}
	}
