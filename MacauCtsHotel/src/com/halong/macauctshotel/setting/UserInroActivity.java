/**
 * 
 */
package com.halong.macauctshotel.setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.CustomerInfo;
import com.halong.macauctshotel.entity.soap.RegisterDTO;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.util.StringUtil;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 
 */
public class UserInroActivity extends BackActivity {
	private boolean sex = false;

	private EditText mUserNameEditText, mNameEditText, mPhoneEditText, mPasswordEditText, mEmailEditText, mIDNumberEditText, mEditText;
	private Button mOKButton;

	private CustomerInfo mCustomerInfo;
	private RegisterDTO mRegisterDTO;
	private ImageButton imageButton;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		mRegisterDTO = new RegisterDTO();

		mUserNameEditText = (EditText) findViewById(R.id.userNameEditText);
		mNameEditText = (EditText) findViewById(R.id.nameEditText);
		mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);
		mEmailEditText = (EditText) findViewById(R.id.emailEditText);
		mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
		mIDNumberEditText = (EditText) findViewById(R.id.IDNumberEditText);
		mEditText = (EditText) findViewById(R.id.editText);
		imageButton = (ImageButton) findViewById(R.id.imageButton);

		mOKButton = (Button) findViewById(R.id.OK);
		mOKButton.setClickable(false);
		customerGetInterface();
	}

	private void initInfo() {
		// 设置初始化内容
		mUserNameEditText.setText(mCustomerInfo.getCode());
		mNameEditText.setText(mCustomerInfo.getNickname());
		mPhoneEditText.setText(mCustomerInfo.getMobile());
		mEmailEditText.setText(mCustomerInfo.getEmail());
		mIDNumberEditText.setText(mCustomerInfo.getCardno());
		mEditText.setText(mCustomerInfo.getPointaccid());
		String gender = mCustomerInfo.getGender();
		if ("F".equals(gender)) {
			sex = false;
			changeSex(null);
		} else {
			sex = true;
			changeSex(null);
		}
	}

	OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String code = mUserNameEditText.getText().toString().trim();
			String password = mPasswordEditText.getText().toString().trim();
			String nickname = mNameEditText.getText().toString().trim();
			String email = mEmailEditText.getText().toString().trim();
			String mobile = mPhoneEditText.getText().toString().trim();

			Log.v("onClick", code + password + nickname + email + mobile);
			if ("".equals(code) || "".equals(email) || "".equals(mobile)) {
				Toast.makeText(UserInroActivity.this, getResources().getString(R.string.input_toast), Toast.LENGTH_SHORT).show();
				return;
			} else if (code.equals(mCustomerInfo.getCustid()) && code.equals(mCustomerInfo.getEmail()) && code.equals(mCustomerInfo.getMobile())) {
				Toast.makeText(UserInroActivity.this, getResources().getString(R.string.update_toast), Toast.LENGTH_SHORT).show();
				return;
			}else if(!StringUtil.isEmail(email)){
				Toast.makeText(UserInroActivity.this, getResources().getString(R.string.email_toast), Toast.LENGTH_SHORT).show();
				return;
			}
			
			mRegisterDTO.setCode(code);
			mRegisterDTO.setNickname(nickname);
			mRegisterDTO.setEmail(email);
			mRegisterDTO.setMobile(mobile);
			mRegisterDTO.setCustid(mCustomerInfo.getCustid());
			mRegisterDTO.setMemberstatus(mCustomerInfo.getMemberstatus());

			if ("F".equals(mCustomerInfo.getGender()) || "M".equals(mCustomerInfo.getGender())) {
				if (sex) {
					mRegisterDTO.setGender("F");
				} else {
					mRegisterDTO.setGender("M");
				}
			}
			modifyMember();
		}

	};

	private void customerGetInterface() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				mCustomerInfo = ParseJson.getInstance().customerGetInterface(UserInroActivity.this, content);
				if (mCustomerInfo == null) {
					return;
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				initInfo();
				mOKButton.setOnClickListener(onClickListener);
				mOKButton.setClickable(true);
			}
		});
		asyncAPIClient.customerGetInterface(SharedPreferencesHelper.getLongValue(UserInroActivity.this, SaveInfoUtil.CUSTID_KEY_STRING));
	}

	private void modifyMember() {
		mOKButton.setClickable(false);
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				if (ParseJson.getInstance().modifyMember(UserInroActivity.this, content)) {
					mCustomerInfo.setNickname(mRegisterDTO.getNickname());
					mCustomerInfo.setEmail(mRegisterDTO.getEmail());
					mCustomerInfo.setMobile(mRegisterDTO.getMobile());
					mCustomerInfo.setGender(mRegisterDTO.getGender());
					mCustomerInfo.setCode(mRegisterDTO.getCode());
					initInfo();
					Toast.makeText(UserInroActivity.this, getResources().getString(R.string.action_success_toast), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				mOKButton.setClickable(true);
			}
		});
		asyncAPIClient.modifyMember(mRegisterDTO);
	}

	/**
	 * 
	 */
	public void changePasswordOnclick(View view) {
		// TODO Auto-generated method stub
		finish();
	}

	/**
	 * 
	 */
	public void changeSex(View view) {
		if (sex == false) {
			imageButton.setImageResource(R.drawable.button10);
			mRegisterDTO.setGender("F");
			sex = true;
		} else {
			imageButton.setImageResource(R.drawable.button10_on);
			mRegisterDTO.setGender("M");
			sex = false;
		}

	}
}
