/**
 * 
 */
package com.halong.macauctshotel.setting;

import android.os.Bundle;
import android.widget.TextView;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.entity.soap.CustSrvRecord;

/**
 * 
 */
public class CommentActivity2 extends BackActivity {

	private CustSrvRecord mCustSrvRecord;

	private TextView textView1, textView2, textView3, textView4;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment2);
		mCustSrvRecord = (CustSrvRecord) getIntent().getSerializableExtra("CustSrvRecord");

		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView4 = (TextView) findViewById(R.id.textView4);

		textView1.setText(textView1.getText() + mCustSrvRecord.getOrdernum());
		textView2.setText(textView2.getText() + mCustSrvRecord.getRmTypeName());
		String textView3String = "";
		switch (mCustSrvRecord.getLevel()) {
		case 0:
			textView3String = getResources().getString(R.string.good);
			break;
		case 1:
			textView3String = getResources().getString(R.string.normal);
			break;
		case 2:
			textView3String = getResources().getString(R.string.bad);
			break;
		default:
			break;
		}
		textView3.setText(textView3.getText() + textView3String);
		textView4.setText(textView4.getText() + mCustSrvRecord.getComment());
	}
}
