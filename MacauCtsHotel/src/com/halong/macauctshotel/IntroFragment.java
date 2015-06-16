/**
 * 
 */
package com.halong.macauctshotel;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.halong.macauctshotel.common.FragmentToOtherActivity;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.intro.FreeBusActivity;
import com.halong.macauctshotel.intro.IntroInfoFragment;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 
 */
public class IntroFragment extends FragmentToOtherActivity {

	private View introFragmentView;
	private int[] guideIcons;
	private ImageView[] guideIconImageViews;
	private Button busButton;

	private ViewPager viewPager;
	private FragmentStatePagerAdapter adapter;
	private List<HotelDTO> list;
	private LayoutInflater inflater;

	private LinearLayout mLinearLayout;

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
		this.inflater = inflater;
		introFragmentView = inflater.inflate(R.layout.fragment_intro, null);
		introFragmentView.findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		busButton = (Button) introFragmentView.findViewById(R.id.freeBus);
		busButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toOtherActivity(FreeBusActivity.class);
			}
		});
		list = new ArrayList<HotelDTO>();
		getHotelList();

		return introFragmentView;
	}

	private void getHotelList() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(getActivity());
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				list = ParseJson.getInstance().getHotelList(getActivity(), content, true);
				if (list == null) {
					return;
				}
				initFragmentViewpager();
			}
		});
		asyncAPIClient.getHotelList("");
	}

	private void initFragmentViewpager() {
		mLinearLayout = (LinearLayout) introFragmentView.findViewById(R.id.linearLayout);
		guideIconImageViews = new ImageView[list.size()];
		guideIcons = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.item_fragment_icon, null);
			ImageView iconImageView = (ImageView) frameLayout.findViewById(R.id.imageView);
			iconImageView.setId(i);
			guideIcons[i] = i;
			guideIconImageViews[i] = iconImageView;
			if (i == 0) {
				guideIconImageViews[i].setSelected(true);
			}
			mLinearLayout.addView(frameLayout);
		}
		viewPager = (ViewPager) introFragmentView.findViewById(R.id.viewPager);

		FragmentManager fragmentManager = null;
		try {
			fragmentManager = getActivity().getSupportFragmentManager();
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
		adapter = new FragmentStatePagerAdapter(fragmentManager) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				IntroInfoFragment fragment = new IntroInfoFragment();
				fragment.setmHotelDTO(list.get(arg0));
				return fragment;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
			}
		};

		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < guideIconImageViews.length; i++) {
					if (i == arg0) {
						guideIconImageViews[i].setSelected(true);
					} else {
						guideIconImageViews[i].setSelected(false);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
