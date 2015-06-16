/**
 * 
 */
package com.halong.macauctshotel.book;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.entity.soap.GetPaymentDTO;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.setting.OrderFragmentAcitivity;
import com.halong.macauctshotel.util.AppStackManager;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DialerFilter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 */
public class ScuccessOrderFragment extends Fragment implements OnClickListener {
	private Dialog dialog;

	private TextView textView, textView1, textView2;
	private Button button, button1;
	private RadioGroup radioGroup;
	private RelativeLayout relativeLayout, relativeLayout1;

	private Gres mGres;

	private final int PAY = 0;
	private HotelBookFragmentActivity activity;

	private String tips = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = (HotelBookFragmentActivity) getActivity();
		mGres = activity.getmGres();
		View view = inflater.inflate(R.layout.fragment_scuucess_order, null);
		textView = (TextView) view.findViewById(R.id.textView);
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
		relativeLayout1 = (RelativeLayout) view.findViewById(R.id.relativeLayout1);
		view.findViewById(R.id.button).setOnClickListener(this);
		button1 = (Button) view.findViewById(R.id.payBtn);
		button1.setOnClickListener(this);
		infotips();

		textView1.setText(getActivity().getResources().getString(R.string.all_price) + mGres.getPrice() + mGres.getCurrencyname());
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.payBtn:
			dialog = new Dialog(getActivity(), R.style.MyDialog);
			dialog.setContentView(R.layout.dialog_pay);
			dialog.findViewById(R.id.OK).setOnClickListener(this);
			dialog.findViewById(R.id.cancelButton).setOnClickListener(this);
			radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
			dialog.show();
			break;
		case R.id.OK:
			ok();
			break;
		case R.id.cancelButton:
			dialog.dismiss();
			break;
		case R.id.button:
			getActivity().finish();
			break;
		default:
			break;
		}
	}

	private void ok() {
		if (radioGroup.getCheckedRadioButtonId() <= 0) {
			Toast.makeText(getActivity(), getResources().getString(R.string.pay_toast), Toast.LENGTH_SHORT).show();
			return;
		}
		switch (radioGroup.getCheckedRadioButtonId()) {
		case R.id.radioButton:
			mGres.setRestype("G");
			mGres.setPayMthd("3");
			radioButton();
			break;
		case R.id.radioButton1:
			mGres.setRestype("T");
			mGres.setPayMthd("4");
			addOrderInterface();
			break;

		default:
			break;
		}
	}

	private void radioButton() {
		Intent intent = new Intent(getActivity(), PayActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("gres", activity.getmGres());
		intent.putExtras(bundle);
		startActivityForResult(intent, PAY);
		dialog.dismiss();
	}

	private void addOrderInterface() {
		mGres.setCreditCard("");
		mGres.setCreditType("");
		mGres.setCvvCode("");
		mGres.setCreditDate("");
		button1.setClickable(false);
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(activity);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				long accId = ParseJson.getInstance().addOrderInterface(activity, content);
				if (accId > 0) {
					mGres.setAccId(Long.valueOf(accId));
					success();
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
				button1.setClickable(true);
			}
		});
		asyncAPIClient.addOrderInterface(mGres);
		dialog.dismiss();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PAY) {
			if (resultCode == PayActivity.PAY_SUCCESS) {
				mGres = (Gres) data.getSerializableExtra("gres");
				activity.setmGres(mGres);
				success();
			}
		}
	}

	private void infotips() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(activity);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				tips = ParseJson.getInstance().infotips(activity, content);
			}
		});
		asyncAPIClient.infotips();
	}

	private void success() {
		Toast.makeText(activity, getString(R.string.pay_success) + "\n" + tips, Toast.LENGTH_SHORT).show();
		relativeLayout.setVisibility(View.VISIBLE);
		relativeLayout1.setVisibility(View.GONE);
		textView2.setVisibility(View.VISIBLE);
		textView.setText(getActivity().getResources().getString(R.string.order_number) + mGres.getAccId());

		Intent intent = new Intent(getActivity(), OrderFragmentAcitivity.class);
		startActivity(intent);
		AppStackManager.getMyAppStackManager().retainFristActivity();
	}
}
