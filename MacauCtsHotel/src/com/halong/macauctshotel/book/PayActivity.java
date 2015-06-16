package com.halong.macauctshotel.book;

import java.util.List;

import com.halong.macauctshotel.BookFragment;
import com.halong.macauctshotel.ProvisionActivity;
import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.entity.soap.DcPublic;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.util.DateUtil;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.ApiLanguageUtil;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PayActivity extends BackActivity implements OnClickListener {

	private TextView textView1, textView3, tv_price;
	private EditText mCodeEditText, mDateEditText, mPasswordEditText;
	private RelativeLayout relativeLayout;
	private CheckBox checkBox;
	private Button button;

	private List<DcPublic> list;
	private String[] types;

	private String creditType = "";
	private String creditDate = "";
	private String creditCard = "";
	private String cvvCode = "";
	private Gres mGres;

	public static final int PAY_SUCCESS = 10001;
	public static final int PAY_FAILURE = 10002;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		mGres = (Gres) getIntent().getSerializableExtra("gres");
		setResult(PAY_FAILURE);
		initId();
		initView();
		getDcPublic();
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_price.setText(mGres.getPrice() + mGres.getCurrencyname());

		textView3.setOnClickListener(this);
		relativeLayout.setOnClickListener(this);
		button.setOnClickListener(this);
	}

	private void initId() {
		textView1 = (TextView) findViewById(R.id.textView1);
		textView3 = (TextView) findViewById(R.id.textView3);
		mCodeEditText = (EditText) findViewById(R.id.edt_code);
		mDateEditText = (EditText) findViewById(R.id.edt_date);
		mPasswordEditText = (EditText) findViewById(R.id.edt_password);
		relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
		checkBox = (CheckBox) findViewById(R.id.rtp);
		button = (Button) findViewById(R.id.OK);
		tv_price = (TextView) findViewById(R.id.tv_price);
	}

	/**
	 * 获取信用卡
	 */
	private void getDcPublic() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				list = ParseJson.getInstance().getDcPublic(PayActivity.this, content);
				if (list == null) {
					Toast.makeText(PayActivity.this, getResources().getString(R.string.network_toast2), Toast.LENGTH_SHORT).show();
					return;
				}
				types = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					types[i] = list.get(i).getName();
				}
			}
		});
		asyncAPIClient.getDcPublic("credittp");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.relativeLayout:
			showType();
			break;
		case R.id.OK:
			ok();
			break;
		case R.id.textView3:
			Intent intent2 = new Intent(PayActivity.this, ProvisionActivity.class);
			intent2.putExtra("num", ProvisionActivity.PAY);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

	private void ok() {
		// TODO Auto-generated method stub
		if (checkBox.isChecked()) {
			Toast.makeText(this, R.string.clause_toast, Toast.LENGTH_SHORT).show();
			return;
		}
		creditDate = mDateEditText.getText().toString().trim();
		creditCard = mCodeEditText.getText().toString().trim();
		cvvCode = mPasswordEditText.getText().toString().trim();
		if ("".equals(creditType) || "".equals(creditDate) || "".equals(creditCard) || "".equals(cvvCode)) {
			Toast.makeText(this, R.string.input_toast1, Toast.LENGTH_SHORT).show();
			return;
		}
		if (!isValid(creditCard)) {
			Toast.makeText(this, getString(R.string.credit_id_toast), Toast.LENGTH_SHORT).show();
			return;
		}
		addOrderInterface();
	}

	public void addOrderInterface() {
		mGres.setCreditCard(creditCard);
		mGres.setCreditDate(creditDate);
		mGres.setCreditType(creditType);
		mGres.setCvvCode(cvvCode);
		button.setClickable(false);
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				long accId = ParseJson.getInstance().addOrderInterface(PayActivity.this, content);
				if (accId > 0) {
					mGres.setAccId(Long.valueOf(accId));
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putSerializable("gres", mGres);
					intent.putExtras(bundle);
					setResult(PAY_SUCCESS, intent);
					finish();
				} else {

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
				button.setClickable(true);
			}
		});
		asyncAPIClient.addOrderInterface(mGres);
	}

	private void showType() {
		if (types == null || types.length <= 0) {
			Toast.makeText(this, getResources().getString(R.string.network_toast2), Toast.LENGTH_SHORT).show();
			return;
		}
		Dialog dialog = new Dialog(getApplicationContext());
		Builder build = new AlertDialog.Builder(this);
		build.setSingleChoiceItems(types, 0, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				textView1.setText(types[arg1]);
				creditType = list.get(arg1).getCode();
			}
		}).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
			}
		});
		dialog = build.create();
		dialog.show();
	};

	private String getDigitsOnly(String s) {
		StringBuffer digitsOnly = new StringBuffer();
		char c;
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (Character.isDigit(c)) {
				digitsOnly.append(c);
			}
		}
		return digitsOnly.toString();
	}

	/**
	 * 判断是否是信用卡
	 * @param cardNumber
	 * @return
	 */
	private boolean isValid(String cardNumber) {
		String digitsOnly = getDigitsOnly(cardNumber);
		int sum = 0;
		int digit = 0;
		int addend = 0;
		boolean timesTwo = false;
		for (int i = digitsOnly.length() - 1; i >= 0; i--) {
			digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
			if (timesTwo) {
				addend = digit * 2;
				if (addend > 9) {
					addend -= 9;
				}
			} else {
				addend = digit;
			}
			sum += addend;
			timesTwo = !timesTwo;
		}
		int modulus = sum % 10;
		return modulus == 0;
	}
}
