/**
 * 
 */
package com.halong.macauctshotel;

import java.util.List;

import com.halong.macauctshotel.common.FragmentToOtherActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.setting.AboutActivity;
import com.halong.macauctshotel.setting.ChangePasswordActivity;
import com.halong.macauctshotel.setting.CommentActivity;
import com.halong.macauctshotel.setting.FavoritesActivity;
import com.halong.macauctshotel.setting.HelpActivity;
import com.halong.macauctshotel.setting.OrderFragmentAcitivity;
import com.halong.macauctshotel.setting.UserInroActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 
 */
public class SettingFragment extends FragmentToOtherActivity {
	private RelativeLayout layout;
	private static final int REQUESTCODE5 = 5;

	private int[] relativeLayoutIDs = { R.id.userInfo, R.id.order, R.id.comment, R.id.changePassword, R.id.help, R.id.favorites, R.id.about, R.id.signout };

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
		View view = inflater.inflate(R.layout.fragment_setting, null);
		view.findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		for (int i = 0; i < relativeLayoutIDs.length; i++) {
			layout = (RelativeLayout) view.findViewById(relativeLayoutIDs[i]);
			layout.setOnClickListener(onClickListener);
		}
		return view;
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.userInfo:
				toOtherActivity(UserInroActivity.class);
				break;

			case R.id.order:
				toOtherActivity(OrderFragmentAcitivity.class);
				break;
			case R.id.comment:
				toOtherActivity(CommentActivity.class);
				break;
			case R.id.changePassword:
				toOtherActivity(ChangePasswordActivity.class);
				break;
			case R.id.help:
				toOtherActivity(HelpActivity.class);
				break;
			case R.id.about:
				toOtherActivity(AboutActivity.class);
				break;
			case R.id.favorites:
				Intent myIntent = new Intent(getActivity(), FavoritesActivity.class);
				startActivityForResult(myIntent, REQUESTCODE5);
				break;
			case R.id.signout:
				SaveInfoUtil.clear(getActivity());
				LoginFragment newFragment = new LoginFragment();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.loginLayout, newFragment);
				transaction.commit();
				break;
			default:
				break;
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUESTCODE5) {
			if (resultCode == FavoritesActivity.REQUESTCODE5) {
				String string = data.getStringExtra("dateFormat");
				List<HotelDTO> mHotelDTOs = (List<HotelDTO>) data.getSerializableExtra("hotelDTOs");
				int mHotelNum = data.getIntExtra("hotelNum", -1);
				((TabActivity)getActivity()).toBookFragment(string, mHotelDTOs, mHotelNum);
			}
		}
	};
}
