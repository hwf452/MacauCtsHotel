/**
 * 
 */
package com.halong.macauctshotel.setting;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 */
public class HelpActivity extends BackActivity {

	private String name, phone, email, content;
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
		setContentView(R.layout.activity_help);
		button=(Button) findViewById(R.id.OK);
	}

	public void submit(View view) {
		name = ((TextView) findViewById(R.id.nameEditText)).getText().toString().trim();
		phone = ((TextView) findViewById(R.id.phoneEditText)).getText().toString().trim();
		email = ((TextView) findViewById(R.id.emailEditText)).getText().toString().trim();
		content = ((TextView) findViewById(R.id.suggestionEditText)).getText().toString().trim();

		if ("".equals(name) || "".equals(phone) || "".equals(email) || "".equals(content)) {
			Toast.makeText(this, getString(R.string.input_toast1), Toast.LENGTH_SHORT).show();
			return;
		}
		post();
	}

	private void post() {
		// TODO Auto-generated method stub
		button.setClickable(false);
		AsyncAPIClient apiClient = new AsyncAPIClient(this);
		apiClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				HelpActivity.this.finish();
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				button.setClickable(true);
			}
			
		});
		apiClient.addFeedback(SharedPreferencesHelper.getLongValue(this, SaveInfoUtil.CUSTID_KEY_STRING) + "", name, phone, email, content);
	}
}
