/**
 * 
 */
package com.halong.macauctshotel.setting;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackFragmentActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.entity.soap.OrderDTO;
import com.halong.macauctshotel.setting.order.HistoryOrderFragment;
import com.halong.macauctshotel.setting.order.NewOrderFragment;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.util.StringUtil;
import com.halong.macauctshotel.wcf.ApiLanguageUtil;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 
 */
public class OrderFragmentAcitivity extends BackFragmentActivity {
	private FragmentPagerAdapter fragmentPagerAdapter;
	private ViewPager viewPager;
	private Button newOrderButton;
	private Button historyOrderButton;
	private OnClickListener orderClickListener;

	private List<Gres> mNewList, mOldList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_order);

		orderClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setSelected(true);
				switch (v.getId()) {
				case R.id.newOrder:
					historyOrderButton.setSelected(false);
					viewPager.setCurrentItem(0);
					break;
				case R.id.historyOrder:
					newOrderButton.setSelected(false);
					viewPager.setCurrentItem(1);
					break;
				default:
					break;
				}
			}
		};

		newOrderButton = (Button) findViewById(R.id.newOrder);
		newOrderButton.setSelected(true);
		historyOrderButton = (Button) findViewById(R.id.historyOrder);

		getOrderList();
	}

	private void initViewPager() {
		newOrderButton.setOnClickListener(orderClickListener);
		historyOrderButton.setOnClickListener(orderClickListener);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 2;
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub

				switch (arg0) {
				case 0:
					return new NewOrderFragment();
				case 1:
					return new HistoryOrderFragment();
				default:
					return null;
				}

			}

		};
		viewPager.setAdapter(fragmentPagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					newOrderButton.setSelected(true);
					historyOrderButton.setSelected(false);
					break;
				case 1:
					newOrderButton.setSelected(false);
					historyOrderButton.setSelected(true);
					break;
				default:
					break;
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

	private void getOrderList() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				if (!ParseJson.getInstance().getOrderList(OrderFragmentAcitivity.this, content)) {
					return;
				}
				mOldList = new ArrayList<Gres>();
				mNewList = new ArrayList<Gres>();
				try {
					JSONObject jsonObject = new JSONObject(content);
					JSONArray jsonArray = jsonObject.getJSONArray("orders");

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = (JSONObject) jsonArray.get(i);
						getHotel(object);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(content);
						JSONObject object = jsonObject.getJSONObject("orders");
						JSONObject object1 = object.getJSONObject("Order");
						getHotel(object1);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				initViewPager();
			}

			private void getHotel(JSONObject object) throws JSONException {
				Gres gres = new Gres();
				gres.setAccId(Long.valueOf(object.getString("accId")));
				gres.setResId(Long.valueOf(object.getString("resId")));
				if (ApiLanguageUtil.EN.equals(ApiLanguageUtil.getStringLanguage())) {
					gres.setHotelName(object.getString("hotelEName"));
					gres.setRmTypeName(object.getString("rmTypeEName"));
					gres.setHotelAddres(object.getString("hoteleaddress"));
					gres.setCurrencyname(object.getString("currencyename"));
					gres.setStatusName(object.getString("statusEName"));
				} else {
					gres.setHotelName(object.getString("hotelCName"));
					gres.setRmTypeName(object.getString("rmTypeCName"));
					gres.setHotelAddres(object.getString("hoteladdress"));
					gres.setCurrencyname(object.getString("currencycname"));
					gres.setStatusName(object.getString("statusCName"));
				}
				gres.setRmQty(Long.valueOf(object.getString("rmQty")));
				gres.setBookTel(object.getString("bookTel"));
				gres.setBooker(object.getString("booker"));
				gres.setRemarks(object.getString("remarks"));
				gres.setArrDate(object.getString("arrDate"));
				gres.setDepDate(object.getString("depDate"));
				gres.setResDate(object.getString("resDate"));
				gres.setRmRate(object.getString("rmRate"));
				gres.setHoteletel(object.getString("hoteletel"));
				gres.setComment(object.getString("comment"));
				gres.setLevel(object.getInt("level"));

				String status = object.getString("status");
				gres.setStatus(status);

				String date = gres.getResDate();
				date = StringUtil.parseDate(date);
				gres.setResDate(date);

				if ("I".equals(status) || "N".equals(status) || "R".equals(status) || "W".equals(status)) {
					mNewList.add(gres);
				} else {
					mOldList.add(gres);
				}
			}

		});
		OrderDTO orderDTO = new OrderDTO();
		long custId = SharedPreferencesHelper.getLongValue(OrderFragmentAcitivity.this, SaveInfoUtil.CUSTID_KEY_STRING);
		orderDTO.setCustId(custId);
		asyncAPIClient.getOrderList(orderDTO);
	}

	public List<Gres> getmNewList() {
		return mNewList;
	}

	public void setmNewList(List<Gres> mNewList) {
		this.mNewList = mNewList;
	}

	public List<Gres> getmOldList() {
		return mOldList;
	}

	public void setmOldList(List<Gres> mOldList) {
		this.mOldList = mOldList;
	}

}
