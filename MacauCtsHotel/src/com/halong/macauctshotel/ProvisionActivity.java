package com.halong.macauctshotel;

import com.halong.macauctshotel.common.BackActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * 条款界面
 * @author Administrator
 *
 */
public class ProvisionActivity extends BackActivity{

	private TextView textView;
	private int num;
	
	public static final int USER=0;
	public static final int PAY=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_provision);
		textView=(TextView) findViewById(R.id.textview1);
		num=getIntent().getIntExtra("num", -1);
		if (num == PAY) {
			textView.setText(getResources().getString(R.string.pay_provision));
		}else if(num==USER) {
			String str = "中璟荟会员章程";
			textView.setText(str);
		}
		
	}
}
