/**
 * 
 */
package com.halong.macauctshotel.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.util.SharedPreferencesHelper;

/**
 * 
 */
public class HotelBookFragment extends Fragment implements OnClickListener {
	private RelativeLayout enterTime, leaveTime;
	private TextView tvEnterTime, tvLeaveTime;
	private static final int REQUESTCODE1 = 1, REQUESTCODE2 = 2;

	private TextView liveEditText, tv_enter, tv_leave, textView;
	private EditText custmersNameEditText;
	private EditText bookCustomerEditText;
	private EditText bookCustomerPhoneEditText;
	private EditText suggestionEditText;
	private EditText bookCustomerEmailEditText;

	public interface HBFCallBack {
		public String textViewStringNumber();
	}

	private Gres mGres;
	private HotelBookFragmentActivity activity;

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

		View view = inflater.inflate(R.layout.fragment_hotel_book, null);

		bookCustomerPhoneEditText = (EditText) view.findViewById(R.id.bookCustomerPhoneEditText);
		custmersNameEditText = (EditText) view.findViewById(R.id.custmersNameEditText);
		bookCustomerEditText = (EditText) view.findViewById(R.id.bookCustomerEditText);
		suggestionEditText = (EditText) view.findViewById(R.id.suggestionEditText);
		suggestionEditText = (EditText) view.findViewById(R.id.suggestionEditText);
		bookCustomerEmailEditText = (EditText) view.findViewById(R.id.bookCustomerEmailEditText);
		liveEditText = (TextView) view.findViewById(R.id.liveEditText);
		tv_enter = (TextView) view.findViewById(R.id.tv_enter);
		tv_leave = (TextView) view.findViewById(R.id.tv_leave);
		textView = (TextView) view.findViewById(R.id.textView);

		bookCustomerEmailEditText.setText(SharedPreferencesHelper.getStringValue(getActivity(), SaveInfoUtil.EMAIL_KEY_STRING));
		liveEditText.setText(getResources().getStringArray(R.array.rooms_number_spinner)[(int) mGres.getRmQty() - 1]);
		tv_enter.setText(mGres.getArrDate());
		tv_leave.setText(mGres.getDepDate());

		view.findViewById(R.id.OK).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.liveTimeLayout:
			Intent intent = new Intent(getActivity(), KCalendarActivity.class);
			intent.putExtra("titleText", "入住时间");
			startActivityForResult(intent, REQUESTCODE1);
			break;

		case R.id.liveTimeLayout1:
			Intent intent1 = new Intent(getActivity(), KCalendarActivity.class);
			intent1.putExtra("titleText", "离店时间");
			startActivityForResult(intent1, REQUESTCODE2);

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
				tvEnterTime.setText(string);
				String string2 = data.getStringExtra("nextDateString");

				tvLeaveTime.setText(string2);
			}
		}
		if (requestCode == REQUESTCODE2) {
			if (resultCode == KCalendarActivity.RESULT_CODE1) {
				String string = data.getStringExtra("dateFormat");
				tvLeaveTime.setText(string);
			}
		}

	}

}
