/**
 * 
 */
package com.halong.macauctshotel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.halong.macauctshotel.common.FragmentToOtherActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 
 */
public class LoginFragment extends FragmentToOtherActivity {
	private Button regiesterButton;
	private Button loginButton;
	private EditText mCodeEditText, mPasswordEditText;
	private CheckBox mRtpCheckBox1, mRtpCheckBox2;

	private String mCode, mPassword;

	/*
	 * ddfddd (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_login, null);
		mCodeEditText = (EditText) view.findViewById(R.id.userNameEditText);
		mPasswordEditText = (EditText) view.findViewById(R.id.passwordEditText);
		mRtpCheckBox1 = (CheckBox) view.findViewById(R.id.rtp);
		mRtpCheckBox2 = (CheckBox) view.findViewById(R.id.rtp2);
		checkLogin();

		// 自动保存账号密码
		if (SharedPreferencesHelper.getIntValue(getActivity(), SaveInfoUtil.SAVELOGIN_KEY_STRING, 0) == 1) {
			mCodeEditText.setText(SharedPreferencesHelper.getStringValue(getActivity(), SaveInfoUtil.CODE_KEY));
			mPasswordEditText.setText(SharedPreferencesHelper.getStringValue(getActivity(), SaveInfoUtil.PASSWORD_KEY));
			mRtpCheckBox1.setChecked(false);
		}

		mRtpCheckBox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					mRtpCheckBox1.setChecked(false);
				}
			}
		});

		view.findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		regiesterButton = (Button) view.findViewById(R.id.regiester);
		regiesterButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				register(arg0);
			}
		});

		loginButton = (Button) view.findViewById(R.id.OK);
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				mCode = mCodeEditText.getText().toString().trim();
				mPassword = mPasswordEditText.getText().toString().trim();
				if ("".equals(mCode)) {
					Toast.makeText(getActivity(), getResources().getString(R.string.code_toast), Toast.LENGTH_SHORT).show();
				} else if ("".equals(mPassword) || mPassword.length() < 6) {
					Toast.makeText(getActivity(), getResources().getString(R.string.password_toast), Toast.LENGTH_SHORT).show();
				} else {
					loginButton.setClickable(false);
					login(arg0);
				}
			}
		});
		return view;
	}

	public void checkLogin() {
		if (SaveInfoUtil.isLogin(getActivity())) {
			successLogin();
		}
	}

	public void register(View view) {
		toOtherActivity(RegisterActivity.class);
	}

	public void login(View view) {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(getActivity());
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				if (ParseJson.getInstance().loginGetInterface(getActivity(), content)) {
					if (!mRtpCheckBox1.isChecked()) {
						SharedPreferencesHelper.setStringValue(getActivity(), SaveInfoUtil.CODE_KEY, mCode);
						SharedPreferencesHelper.setStringValue(getActivity(), SaveInfoUtil.PASSWORD_KEY, mPassword);
						SharedPreferencesHelper.setIntValue(getActivity(), SaveInfoUtil.SAVELOGIN_KEY_STRING, 1);
						SharedPreferencesHelper.setIntValue(getActivity(), SaveInfoUtil.AUTOLOGIN_KEY_STRING, 0);
						if (!mRtpCheckBox2.isChecked()) {
							SharedPreferencesHelper.setIntValue(getActivity(), SaveInfoUtil.AUTOLOGIN_KEY_STRING, 1);
						}
					}
					successLogin();
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				loginButton.setClickable(true);
			}
		});
		asyncAPIClient.loginGetInterface(mCode, mPassword);
	}

	private void successLogin() {
		Fragment newFragment = new SettingFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.loginLayout, newFragment);
		transaction.commit();
	}

}
