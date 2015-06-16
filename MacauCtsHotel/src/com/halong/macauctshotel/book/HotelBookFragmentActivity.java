/**
 * 
 */
package com.halong.macauctshotel.book;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackFragmentActivity;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.util.AppStackManager;

/**
 * 
 */
public class HotelBookFragmentActivity extends BackFragmentActivity {
	private FragmentPagerAdapter fragmentPagerAdapter;
	private ViewPager viewPager;
	private Button bookOrderButton;
	private Button checkOrderButton;
	private Button successOrderButton;
	private OnClickListener onClickListener;

	private TextView liveEditText, tv_enter, tv_leave, textView;
	private EditText custmersNameEditText;
	private EditText bookCustomerEditText;
	private EditText bookCustomerPhoneEditText;
	private EditText suggestionEditText;
	private EditText bookCustomerEmailEditText;

	private Gres mGres;

	private HotelBookFragment mHotelBookFragment;
	private LookOverOrderFragment mLookOverOrderFragment;
	private ScuccessOrderFragment mScuccessOrderFragment;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		AppStackManager.getMyAppStackManager().addActivity(this);
		setContentView(R.layout.fragment_activity_hotel_book);

		mGres = (Gres) getIntent().getSerializableExtra("mGres");

		mHotelBookFragment = new HotelBookFragment();
		mLookOverOrderFragment = new LookOverOrderFragment();
		mScuccessOrderFragment = new ScuccessOrderFragment();

		initViewPagerView();
		buttonClick();
		// initFragment1View();
	}

	private void buttonClick() {
		onClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				switch (v.getId()) {
				case R.id.bookOrder:
					v.setSelected(true);
					viewPager.setCurrentItem(0);
					break;
				case R.id.checkOrder:
					v.setSelected(true);
					viewPager.setCurrentItem(1);
					break;
				case R.id.successOrder:
					if (viewPager.getCurrentItem() == 1) {
						viewPager.setCurrentItem(2);
					} else {
						v.setSelected(false);
					}

					break;
				default:
					break;
				}
			}
		};

		bookOrderButton = (Button) findViewById(R.id.bookOrder);
		bookOrderButton.setOnClickListener(onClickListener);
		bookOrderButton.setSelected(true);

		checkOrderButton = (Button) findViewById(R.id.checkOrder);
		checkOrderButton.setOnClickListener(onClickListener);

		successOrderButton = (Button) findViewById(R.id.successOrder);
		successOrderButton.setOnClickListener(onClickListener);

	}

	public void initViewPagerView() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 3;
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					return mHotelBookFragment;
				case 1:
					return mLookOverOrderFragment;
				case 2:
					return mScuccessOrderFragment;
				default:
					return null;
				}

			}

		};
		viewPager.setAdapter(fragmentPagerAdapter);
		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					bookOrderButton.setSelected(true);
					checkOrderButton.setSelected(false);
					successOrderButton.setSelected(false);
					break;
				case 1:
					bookOrderButton.setSelected(false);
					checkOrderButton.setSelected(true);
					successOrderButton.setSelected(false);
					break;
				case 2:

					bookOrderButton.setSelected(false);
					checkOrderButton.setSelected(false);
					successOrderButton.setSelected(true);

					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@SuppressLint("ResourceAsColor")
			@Override
			public void onPageScrollStateChanged(final int arg0) {
				// TODO Auto-generated method stub

				switch (arg0) {
				case 0:
					int current = viewPager.getCurrentItem();
					if (current == 2) {
						
					}

					if (current == 1) {
						Fragment fragment = (HotelBookFragment) fragmentPagerAdapter.instantiateItem(viewPager, 0);
						View view = fragment.getView();
						bookCustomerPhoneEditText = (EditText) view.findViewById(R.id.bookCustomerPhoneEditText);
						custmersNameEditText = (EditText) view.findViewById(R.id.custmersNameEditText);
						bookCustomerEditText = (EditText) view.findViewById(R.id.bookCustomerEditText);
						bookCustomerEmailEditText = (EditText) view.findViewById(R.id.bookCustomerEmailEditText);
						suggestionEditText = (EditText) view.findViewById(R.id.suggestionEditText);
						String string = custmersNameEditText.getText().toString().trim();
						String string2 = bookCustomerEditText.getText().toString().trim();
						String string3 = bookCustomerPhoneEditText.getText().toString().trim();
						String string4 = bookCustomerEmailEditText.getText().toString().trim();

						if (string.equals("") || string2.equals("") || string3.equals("") || string3.length() < 3 || "".equals(string4)) {
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.alert), Toast.LENGTH_SHORT).show();
							viewPager.setCurrentItem(arg0);
							return;
						}
						mGres.setGstName(string);
						mGres.setBooker(string2);
						mGres.setBookTel(string3);
						mGres.setRemarks(suggestionEditText.getText().toString().trim());
						mGres.setBookerEmail(string4);
					}

					mLookOverOrderFragment.update();

					break;

				default:
					break;
				}
			}
		});

	}

	public Gres getmGres() {
		return mGres;
	}

	public void setmGres(Gres mGres) {
		this.mGres = mGres;
	}

	public void initFragment1View() {
		View view = mHotelBookFragment.getView();
		bookCustomerPhoneEditText = (EditText) view.findViewById(R.id.bookCustomerPhoneEditText);
		custmersNameEditText = (EditText) view.findViewById(R.id.custmersNameEditText);
		bookCustomerEditText = (EditText) view.findViewById(R.id.bookCustomerEditText);
		suggestionEditText = (EditText) view.findViewById(R.id.suggestionEditText);
		liveEditText = (TextView) view.findViewById(R.id.liveEditText);
		tv_enter = (TextView) view.findViewById(R.id.tv_enter);
		tv_leave = (TextView) view.findViewById(R.id.tv_leave);
		textView = (TextView) view.findViewById(R.id.textView);

		liveEditText.setText(getResources().getStringArray(R.array.rooms_number_spinner)[(int) mGres.getRmQty()]);
		tv_enter.setText(mGres.getArrDate());
		tv_leave.setText(mGres.getDepDate());

	}

}
