package com.halong.macauctshotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.halong.macauctshotel.book.HotelBookFragmentActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.common.ToOtherActivity;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

public class LoginActivity extends ToOtherActivity {
	private Button regiesterButton;
	private Button loginButton;
	private EditText mCodeEditText, mPasswordEditText;
	private CheckBox mRtpCheckBox1, mRtpCheckBox2;

	private String mCode, mPassword;

	public final static int LOGINSUCCESS = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login);
		mCodeEditText = (EditText) findViewById(R.id.userNameEditText);
		mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
		mRtpCheckBox1 = (CheckBox) findViewById(R.id.rtp);
		mRtpCheckBox2 = (CheckBox) findViewById(R.id.rtp2);

		findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		regiesterButton = (Button) findViewById(R.id.regiester);
		regiesterButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				register(arg0);
			}
		});

		loginButton = (Button) findViewById(R.id.OK);
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mCode = mCodeEditText.getText().toString().trim();
				mPassword = mPasswordEditText.getText().toString().trim();
				if ("".equals(mCode)) {
					Toast.makeText(LoginActivity.this, getResources().getString(R.string.code_toast), Toast.LENGTH_SHORT).show();
				} else if ("".equals(mPassword) || mPassword.length() < 6) {
					Toast.makeText(LoginActivity.this, getResources().getString(R.string.password_toast), Toast.LENGTH_SHORT).show();
				} else {
					loginButton.setClickable(false);
					login(arg0);
				}
			}
		});
	}

	public void register(View view) {
		toOtherActivity(RegisterActivity.class);
	}

	public void login(View view) {
		AsyncAPIClient client = new AsyncAPIClient(this);
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				if (ParseJson.getInstance().loginGetInterface(LoginActivity.this, content)) {

					if (!mRtpCheckBox1.isChecked()) {
						SharedPreferencesHelper.setIntValue(LoginActivity.this, SaveInfoUtil.SAVELOGIN_KEY_STRING, 1);
						SharedPreferencesHelper.setStringValue(LoginActivity.this, SaveInfoUtil.CODE_KEY, mCode);
						SharedPreferencesHelper.setStringValue(LoginActivity.this, SaveInfoUtil.PASSWORD_KEY, mPassword);
						if (!mRtpCheckBox2.isChecked()) {
							SharedPreferencesHelper.setIntValue(LoginActivity.this, SaveInfoUtil.AUTOLOGIN_KEY_STRING, 1);
						}
					}

					Intent intent = new Intent(LoginActivity.this, HotelBookFragmentActivity.class);
					setResult(LOGINSUCCESS, intent);
					finish();
				}
			}

			@Override
			public void onFailure(String content) {
				// TODO Auto-generated method stub
				super.onFailure(content);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				loginButton.setClickable(true);
			}
		});
		client.loginGetInterface(mCode, mPassword);

	}

}
