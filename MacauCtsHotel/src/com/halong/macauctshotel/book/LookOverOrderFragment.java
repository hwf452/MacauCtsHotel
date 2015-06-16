/**
 * 
 */
package com.halong.macauctshotel.book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.halong.macauctshotel.R;
import com.halong.macauctshotel.entity.soap.Gres;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 */
public class LookOverOrderFragment extends Fragment {

	private TextView textview, textview1, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11;

	private Gres mGres;
	private HotelBookFragmentActivity activity;

	private Button okButton;

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

		View view = inflater.inflate(R.layout.fragment_look_over_order, null);
		textview = (TextView) view.findViewById(R.id.tv_price);
		textview1 = (TextView) view.findViewById(R.id.textView1);
		textview2 = (TextView) view.findViewById(R.id.textView2);
		textview3 = (TextView) view.findViewById(R.id.textView3);
		textview4 = (TextView) view.findViewById(R.id.textView4);
		textview5 = (TextView) view.findViewById(R.id.textView5);
		textview6 = (TextView) view.findViewById(R.id.textView6);
		textview7 = (TextView) view.findViewById(R.id.textView7);
		textview8 = (TextView) view.findViewById(R.id.textView8);
		textview9 = (TextView) view.findViewById(R.id.textView9);
		textview10 = (TextView) view.findViewById(R.id.textView10);
		textview11 = (TextView) view.findViewById(R.id.textView11);

		okButton = (Button) view.findViewById(R.id.OK);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity.setmGres(mGres);
				ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			}
		});
		update();

		return view;
	}

	public void update() {
		mGres = activity.getmGres();
		textview.setText(mGres.getPrice() + mGres.getCurrencyname());
		textview1.setText(mGres.getArrDate());
		textview2.setText(mGres.getDepDate());
		textview3.setText(mGres.getGstName());
		textview4.setText(mGres.getHotelName());
		textview5.setText(mGres.getRmTypeName());
		int night = daysBetween(mGres.getArrDate(), mGres.getDepDate());
		textview6.setText(night + getResources().getString(R.string.night));
		textview7.setText(mGres.getHotelAddres());
		textview8.setText(mGres.getBooker());
		textview9.setText(mGres.getBookTel());
		textview10.setText(mGres.getRemarks());
		textview11.setText(mGres.getBookerEmail());

		mGres.setNights(night);
	}

	public int daysBetween(String smdate, String bdate) {
		long between_days = 0;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			long to = df.parse(bdate).getTime();
			long from = df.parse(smdate).getTime();
			System.out.println();
			return Integer.parseInt((to - from) / (1000 * 60 * 60 * 24) + "");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
