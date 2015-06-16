/**
 * 
 */
package com.halong.macauctshotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.entity.soap.RegisterDTO;
import com.halong.macauctshotel.util.StringUtil;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 
 */
public class RegisterActivity extends BackActivity {

	private TextView textView1;
	private EditText mUserNameEditText, mNameEditText, mEmailEditText, mPasswordEditText, mConfirmPasswordEditText;
	private CheckBox checkBox;
	private Button mOkButton;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initView();

		initLinstener();
	}

	private void initLinstener() {
		// TODO Auto-generated method stub
		textView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,ProvisionActivity.class);
				intent.putExtra("num", ProvisionActivity.USER);
				startActivity(intent);
			}
		});
		
		mOkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkBox.isChecked()) {
					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.clause_toast), Toast.LENGTH_SHORT).show();
					return;
				}
				
				String code = mUserNameEditText.getText().toString().trim();
				String name = mNameEditText.getText().toString().trim();
				String email = mEmailEditText.getText().toString().trim();
				String password = mPasswordEditText.getText().toString().trim();
				String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

				if ("".equals(code) || code.length()<3) {
					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.code_toast), Toast.LENGTH_SHORT).show();
				} else if ("".equals(name)) {
					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.name_toast), Toast.LENGTH_SHORT).show();
				} else if ("".equals(email) || (!StringUtil.isEmail(email))) {
					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.email_toast), Toast.LENGTH_SHORT).show();
				} else if ("".equals(password) || password.length() < 6) {
					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.password_toast), Toast.LENGTH_SHORT).show();
				} else if ("".equals(confirmPassword) || confirmPassword.length() < 6) {
					Toast.makeText(RegisterActivity.this, getResources().getString(R.string.password2_toast), Toast.LENGTH_SHORT).show();
				} else {
					if (!password.equals(confirmPassword)) {
						Toast.makeText(RegisterActivity.this, getResources().getString(R.string.password3_toast), Toast.LENGTH_SHORT).show();
					} else {
						RegisterDTO dto = new RegisterDTO();
						dto.setCode(code);
						dto.setPassword(password);
						dto.setNickname(name);
						dto.setEmail(email);
						dto.setMobile(code);
						
						mOkButton.setClickable(false);
						getPostRegister(dto);
					}
				}
			}
		});
	}

	private void getPostRegister(final RegisterDTO dto) {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				if (ParseJson.getInstance().registerMemberInterface(RegisterActivity.this, content)) {
					finish();
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				mOkButton.setClickable(true);
			}

		});
		asyncAPIClient.registerMemberInterface(dto);
	}

	// private void getPostRegister(final RegisterDTO dto) {
	// new Thread() {
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// super.run();
	// Message message = mHandler.obtainMessage();
	// try {
	// ApiClient client = new ApiClient(RegisterActivity.this);
	// boolean result = client.registerMemberInterface(dto);
	// if (result) {
	// message.what = 1;
	// } else {
	// message.what = 0;
	// }
	// mHandler.sendMessage(message);
	// } catch (Exception e) {
	//
	// }
	//
	// }
	// }.start();
	// }
	//
	// private Handler mHandler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// mOkButton.setClickable(true);
	// if (msg.what == 1) {
	// Toast.makeText(
	// RegisterActivity.this,
	// getResources().getString(
	// R.string.register_success_toast),
	// Toast.LENGTH_SHORT).show();
	// finish();
	// } else {
	// Toast.makeText(RegisterActivity.this, ApiClient.msg,
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// }
	// };

	private void initView() {
		// TODO Auto-generated method stub
		textView1 = (TextView) findViewById(R.id.textView1);
		mUserNameEditText = (EditText) findViewById(R.id.userNameEditText);
		mNameEditText = (EditText) findViewById(R.id.nameEditText);
		mEmailEditText = (EditText) findViewById(R.id.emailEditText);
		mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
		mConfirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
		checkBox = (CheckBox) findViewById(R.id.rtp);
		mOkButton = (Button) findViewById(R.id.OK);
	}

	/**
	 * 
	 */
	public void submit(View view) {
		// TODO Auto-generated method stub
		finish();
	}
}
