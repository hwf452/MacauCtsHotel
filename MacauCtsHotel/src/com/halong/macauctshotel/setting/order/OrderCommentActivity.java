/**
 * 
 */
package com.halong.macauctshotel.setting.order;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;

/**
 * 
 */
public class OrderCommentActivity extends BackActivity {

	private TextView textView, textView1;
	private RadioGroup radioGroup;
	private EditText editText;

	private Gres mGres;

	private Button button;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_comment);
		mGres = (Gres) getIntent().getSerializableExtra("gres");
		textView = (TextView) findViewById(R.id.textView2);
		textView1 = (TextView) findViewById(R.id.textView1);
		editText = (EditText) findViewById(R.id.editText);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		button = (Button) findViewById(R.id.OK);

		textView.setText(textView.getText().toString().trim() + mGres.getAccId());
		textView1.setText(textView1.getText().toString().trim() + mGres.getRmTypeName());
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("".equals(editText.getText().toString().trim()) || radioGroup.getCheckedRadioButtonId() == -1) {
					Toast.makeText(OrderCommentActivity.this, getResources().getString(R.string.input_toast1), Toast.LENGTH_SHORT).show();
				}
				button.setClickable(false);
				AsyncAPIClient client = new AsyncAPIClient(OrderCommentActivity.this);
				client.setOnReturnListener(new OnReturnListener() {

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						Toast.makeText(OrderCommentActivity.this, getResources().getString(R.string.action_success_toast), Toast.LENGTH_SHORT).show();
						OrderCommentActivity.this.finish();
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						button.setClickable(true);
					}
				});
				int level = -1;
				switch (radioGroup.getCheckedRadioButtonId()) {
				case R.id.radioButton:
					level = 0;
					break;
				case R.id.radioButton1:
					level = 1;
					break;
				case R.id.radioButton2:
					level = 2;
					break;
				default:
					return;
				}
				client.orderCommentInput(SharedPreferencesHelper.getLongValue(OrderCommentActivity.this, SaveInfoUtil.CUSTID_KEY_STRING) + "", mGres.getResId()
						+ "", editText.getText().toString().trim(), level + "", mGres.getHotelId() + "", mGres.getRmTypeName());
			}

		});
	}

}
