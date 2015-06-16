/**
 * 
 */
package com.halong.macauctshotel;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.book.CitySearchActivity;
import com.halong.macauctshotel.book.HotelDetailsActivity;
import com.halong.macauctshotel.book.HotelSearchActivity;
import com.halong.macauctshotel.book.KCalendarActivity;
import com.halong.macauctshotel.book.SearchActivity;
import com.halong.macauctshotel.common.FragmentToOtherActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.entity.soap.RoomRateSearchDTO;
import com.halong.macauctshotel.setting.FavoritesActivity;
import com.halong.macauctshotel.util.DateUtil;

/**
 * 
 */
@SuppressLint("NewApi")
public class BookFragment extends FragmentToOtherActivity implements OnClickListener {
	private Button favoritesButton;
	private RelativeLayout cityLayout;
	private RelativeLayout hotelLayout;
	private RelativeLayout enterTime, leaveTime;
	private TextView tvEnterTime, tvLeaveTime, tvCity, tvHotel;

	// REQUESTCODE1入住、REQUESTCODE2离店、REQUESTCODE3城市、REQUESTCODE4酒店、REQUESTCODE5收藏、LOGINSUCCESS登录
	private static final int REQUESTCODE1 = 1, REQUESTCODE2 = 2, REQUESTCODE3 = 3, REQUESTCODE4 = 4, REQUESTCODE5 = 5;

	private Button oKButton;

	private List<HotelDTO> mHotelDTOs;
	private String mCityCode;
	private int mHotelNum = -1;
	public static int mNumRoom = 1; // 房间数

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

		View view = inflater.inflate(R.layout.fragment_book, null);
		view.findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		favoritesButton = (Button) view.findViewById(R.id.myFavorites);
		favoritesButton.setOnClickListener(this);

		cityLayout = (RelativeLayout) view.findViewById(R.id.cityLayout);
		cityLayout.setOnClickListener(this);
		hotelLayout = (RelativeLayout) view.findViewById(R.id.hotelLayout);
		hotelLayout.setOnClickListener(this);
		enterTime = (RelativeLayout) view.findViewById(R.id.liveTimeLayout);
		leaveTime = (RelativeLayout) view.findViewById(R.id.leaveTimeLayout);
		tvEnterTime = (TextView) view.findViewById(R.id.tv_enter);
		tvLeaveTime = (TextView) view.findViewById(R.id.tv_leave);
		enterTime.setOnClickListener(this);
		leaveTime.setOnClickListener(this);

		tvCity = (TextView) view.findViewById(R.id.cityText);
		tvHotel = (TextView) view.findViewById(R.id.hotelText);
		oKButton = (Button) view.findViewById(R.id.OK);
		oKButton.setOnClickListener(onClickListener);

		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION);
		filter.setPriority(Integer.MAX_VALUE);
		getActivity().registerReceiver(myReceiver, filter);

		return view;
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.OK:
				if (isNull(tvEnterTime.getText().toString().trim())) {
					Toast.makeText(getActivity(), getResources().getString(R.string.entertime_toast), Toast.LENGTH_SHORT).show();
				} else if (isNull(tvLeaveTime.getText().toString().trim())) {
					Toast.makeText(getActivity(), getResources().getString(R.string.leavetime_toast), Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = null;
					RoomRateSearchDTO roomRateSearchDTO = new RoomRateSearchDTO();
					roomRateSearchDTO.setPersonNum(1 + "");
					roomRateSearchDTO.setApprDate(tvEnterTime.getText().toString().trim());
					roomRateSearchDTO.setDepDate(tvLeaveTime.getText().toString().trim());
					roomRateSearchDTO.setRoomNum(mNumRoom + "");
					if (mHotelNum == -1) {
						intent = new Intent(getActivity(), SearchActivity.class);

						Bundle bundle = new Bundle();
						bundle.putSerializable("mRoomRateSearchDTO", roomRateSearchDTO);
						intent.putExtras(bundle);
						intent.putExtra("mCityCode", mCityCode);
						getActivity().startActivity(intent);
					} else {
						intent = new Intent(getActivity(), HotelDetailsActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("mHotelDTO", mHotelDTOs.get(mHotelNum));
						bundle.putSerializable("mRoomRateSearchDTO", roomRateSearchDTO);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				}
				break;
			default:
				break;
			}
		}
	};

	private boolean isNull(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.liveTimeLayout:
			Intent intent = new Intent(getActivity(), KCalendarActivity.class);
			DateUtil dateUtil = new DateUtil();
			intent.putExtra("dateFormat", dateUtil.getDateOfYesterday());
			intent.putExtra("titleText", R.string.live_time2);
			startActivityForResult(intent, REQUESTCODE1);
			break;
		case R.id.leaveTimeLayout:
			String dateFormat = tvEnterTime.getText().toString().trim();
			if (dateFormat == null || "".equals(dateFormat)) {
				Toast.makeText(getActivity(), getResources().getString(R.string.entertime_toast), Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent1 = new Intent(getActivity(), KCalendarActivity.class);
			intent1.putExtra("dateFormat", dateFormat);
			intent1.putExtra("titleText", R.string.leave_time2);
			startActivityForResult(intent1, REQUESTCODE2);
			break;
		case R.id.cityLayout:
			Intent cityIntent = new Intent(getActivity(), CitySearchActivity.class);
			startActivityForResult(cityIntent, REQUESTCODE3);
			break;
		case R.id.hotelLayout:
			Intent hotelIntent = new Intent(getActivity(), HotelSearchActivity.class);
			hotelIntent.putExtra("code", mCityCode);
			startActivityForResult(hotelIntent, REQUESTCODE4);
			break;
		case R.id.myFavorites:
			if (!SaveInfoUtil.isLogin(getActivity())) {
				Intent intent2 = new Intent(getActivity(), LoginActivity.class);
				startActivityForResult(intent2, LoginActivity.LOGINSUCCESS);
				return;
			}
			Intent myIntent = new Intent(getActivity(), FavoritesActivity.class);
			startActivityForResult(myIntent, REQUESTCODE5);
			break;
		default:

			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE1) {
			if (resultCode == KCalendarActivity.RESULT_CODE1) {
				String string = data.getStringExtra("dateFormat");
				String string2 = data.getStringExtra("nextDateString");
				tvEnterTime.setText(string);
				tvLeaveTime.setText(string2);
			}
		}
		if (requestCode == REQUESTCODE2) {
			if (resultCode == KCalendarActivity.RESULT_CODE1) {
				String string = data.getStringExtra("dateFormat");
				tvLeaveTime.setText(string);
			}

		}
		if (requestCode == REQUESTCODE3) {
			if (resultCode == CitySearchActivity.REQUESTCODE3) {
				String string = data.getStringExtra("dateFormat");
				mCityCode = data.getStringExtra("code");
				tvCity.setText(string);
				tvHotel.setText("");
				mHotelNum = -1;
				mHotelDTOs = null;
			}
		}
		if (requestCode == REQUESTCODE4) {
			if (resultCode == HotelSearchActivity.REQUESTCODE4) {
				String string = data.getStringExtra("dateFormat");
				tvHotel.setText(string);
				mHotelDTOs = (List<HotelDTO>) data.getSerializableExtra("hotelDTOs");
				mHotelNum = data.getIntExtra("hotelNum", -1);
			}
		}
		if (requestCode == REQUESTCODE5) {
			if (resultCode == FavoritesActivity.REQUESTCODE5) {
				String string = data.getStringExtra("dateFormat");
				tvHotel.setText(string);
				mHotelDTOs = (List<HotelDTO>) data.getSerializableExtra("hotelDTOs");
				mHotelNum = data.getIntExtra("hotelNum", -1);
			}
		}
		if (requestCode == LoginActivity.LOGINSUCCESS) {
			if (resultCode == FavoritesActivity.REQUESTCODE5) {
				favoritesButton.callOnClick();
			}
		}
	}

	public static final String ACTION = "cn.etzmico.broadcastreceiverregister.SENDBROADCAST";
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String string = intent.getStringExtra("dateFormat");
			tvHotel.setText(string);
			mHotelDTOs = (List<HotelDTO>) intent.getSerializableExtra("hotelDTOs");
			mHotelNum = intent.getIntExtra("hotelNum", -1);
		}

	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(myReceiver);
		super.onDestroy();
	}

}
