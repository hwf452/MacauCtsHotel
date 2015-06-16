/**
 * 
 */
package com.halong.macauctshotel;

import java.io.Serializable;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.halong.macauctshotel.common.HotelStrings;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.util.AppStackManager;

/**
 * 
 */
public class TabActivity extends FragmentActivity {
	private TabHost tabHost;
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private RelativeLayout likeSpinnerLayout;
	private TextView likeSpinnerTextView;
	private String selectedRooms;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		AppStackManager.getMyAppStackManager().clear();
		AppStackManager.getMyAppStackManager().addActivity(this);
		initTabView();

	}

	/*
	 * (non-Javadoc) tabhost
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private void initTabView() {
		radioGroup = (RadioGroup) findViewById(R.id.radioGroip);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec(HotelStrings.BOOK).setContent(R.id.bookLyout).setIndicator(HotelStrings.BOOK));
		tabHost.addTab(tabHost.newTabSpec(HotelStrings.SALE).setContent(R.id.saleLayout).setIndicator(HotelStrings.SALE));
		tabHost.addTab(tabHost.newTabSpec(HotelStrings.INTRO).setContent(R.id.introLayout).setIndicator(HotelStrings.INTRO));

		tabHost.addTab(tabHost.newTabSpec(HotelStrings.LOGIN).setContent(R.id.loginLayout).setIndicator(HotelStrings.LOGIN));

		Intent intent = getIntent();
		int viewId = intent.getExtras().getInt(HotelStrings.TAB);
		switch (viewId) {
		case R.id.bookButton:
			tabHost.setCurrentTab(0);
			showDialog();
			radioButton = (RadioButton) radioGroup.findViewById(R.id.bookRadioButton);

			break;
		case R.id.saleButton:
			tabHost.setCurrentTab(1);
			radioButton = (RadioButton) radioGroup.findViewById(R.id.saleRadioButton);
			break;
		case R.id.introButton:
			tabHost.setCurrentTab(2);
			radioButton = (RadioButton) radioGroup.findViewById(R.id.introRadioButton);
			break;
		case R.id.settingButton:
			tabHost.setCurrentTab(3);
			radioButton = (RadioButton) radioGroup.findViewById(R.id.settingRadioButton);
			break;
		default:
			radioButton = (RadioButton) radioGroup.findViewById(R.id.bookRadioButton);
			tabHost.setCurrentTab(0);
			break;
		}
		radioButton.setChecked(true);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.bookRadioButton:
					tabHost.setCurrentTab(0);
					showDialog();
					break;
				case R.id.saleRadioButton:
					tabHost.setCurrentTab(1);
					break;
				case R.id.introRadioButton:
					tabHost.setCurrentTab(2);
					break;
				case R.id.settingRadioButton:
					try {
						LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.loginLayout);
						if (loginFragment != null) {
							loginFragment.checkLogin();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

					tabHost.setCurrentTab(3);
					break;
				default:
					break;
				}
			}
		});
	}

	public void toBookFragment(String hotelName, List<HotelDTO> list, int num) {
		tabHost.setCurrentTab(0);
		Intent intent = new Intent(BookFragment.ACTION);
		intent.putExtra("dateFormat", hotelName);
		Bundle bundle = new Bundle();
		bundle.putSerializable("hotelDTOs", (Serializable) list);
		intent.putExtras(bundle);
		intent.putExtra("hotelNum", num);
		sendBroadcast(intent);
	}

	/**
 * 
 */
	public void exitApp() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	/**
 * 
 */
	private void showDialog() {
		// TODO Auto-generated method stub
		View view = tabHost.getCurrentView();
		likeSpinnerLayout = (RelativeLayout) view.findViewById(R.id.likeSpinnerLayout);
		likeSpinnerTextView = (TextView) view.findViewById(R.id.likeSpinnerTextView);

		likeSpinnerLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog2(likeSpinnerTextView);
			}
		});

	}

	private void showDialog2(final TextView textView) {
		Dialog dialog = new Dialog(getApplicationContext());
		Builder build = new AlertDialog.Builder(this);
		build.setSingleChoiceItems(R.array.rooms_number_spinner, 0, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				selectedRooms = getResources().getStringArray(R.array.rooms_number_spinner)[arg1];
				BookFragment.mNumRoom = arg1 + 1;
			}
		}).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

				textView.setText(selectedRooms);
			}
		});
		dialog = build.create();
		dialog.show();
	};

}
