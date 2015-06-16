/**
 * 
 */
package com.halong.macauctshotel.setting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 
 */
public class ChangePasswordActivity extends BackActivity {

	private EditText mNewPasswordEditText, mConfirmPasswordEditText, mOldPasswordEditText;
	private Button mOKButton;

	private String mPassword, mPassword2;
	private String mOldPassword;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		mOKButton = (Button) findViewById(R.id.OK);
		mOldPasswordEditText = (EditText) findViewById(R.id.oldPasswordEditText);
		mNewPasswordEditText = (EditText) findViewById(R.id.newPasswordEditText);
		mConfirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);

		mOKButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOldPassword = mOldPasswordEditText.getText().toString().trim();
				mPassword = mNewPasswordEditText.getText().toString().trim();
				mPassword2 = mConfirmPasswordEditText.getText().toString().trim();
				if ("".equals(mPassword) || "".equals(mOldPassword)) {
					Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.password_toast), Toast.LENGTH_SHORT).show();
				} else if ("".equals(mPassword2)) {
					Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.password2_toast), Toast.LENGTH_SHORT).show();
				} else if (!mPassword.equals(mPassword2)) {
					Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.password3_toast), Toast.LENGTH_SHORT).show();
				} else {
					mOKButton.setClickable(false);
					modifyPassword();
				}
			}
		});
	}

	public void modifyPassword() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				if (ParseJson.getInstance().modifyPassword(ChangePasswordActivity.this, content)) {
					Toast.makeText(ChangePasswordActivity.this, getString(R.string.action_success_toast), Toast.LENGTH_SHORT).show();
					finish();
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				mOKButton.setClickable(true);
			}
		});
		asyncAPIClient.modifyPassword(SharedPreferencesHelper.getStringValue(ChangePasswordActivity.this, SaveInfoUtil.CARDNO_KEY_STRING), mOldPassword,
				mPassword);
	}

	/**
	 * 
	 */
	public void changePasswordOnclick(View view) {
		// TODO Auto-generated method stub
		finish();
	}

}
