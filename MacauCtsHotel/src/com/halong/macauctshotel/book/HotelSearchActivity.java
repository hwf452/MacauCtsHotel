/**
 * 
 */
package com.halong.macauctshotel.book;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 酒店搜索
 */
public class HotelSearchActivity extends BackActivity {
	public static final int REQUESTCODE4 = 4;
	private AutoCompleteTextView autoCompleteTextView;
	private ArrayAdapter<String> adapter;
	private String[] strings;

	private List<HotelDTO> list;
	private String mCityCode;
	
	private boolean falg=true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_search);

		mCityCode = getIntent().getStringExtra("code");

		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoTextView);
		autoCompleteTextView.setThreshold(1);
		autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent();

				intent1.putExtra("dateFormat", autoCompleteTextView.getText().toString().trim());
				Bundle bundle = new Bundle();
				bundle.putSerializable("hotelDTOs", (Serializable) list);
				intent1.putExtras(bundle);
				intent1.putExtra("hotelNum", arg2);
				setResult(REQUESTCODE4, intent1);
				finish();
			}
		});
		autoCompleteTextView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				autoCompleteTextView.showDropDown();
			}
		});
		autoCompleteTextView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				autoCompleteTextView.showDropDown();
				return false;
			}
		});
		getHotelList();
	}

	private void getHotelList() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener(){
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				list=ParseJson.getInstance().getHotelList(HotelSearchActivity.this, content,false);
				if (list==null) {
					return ;
				}
				strings = new String[list.size()];
				for (int j = 0; j < list.size(); j++) {
					HotelDTO hotelDTO = list.get(j);
					strings[j] = hotelDTO.getName();
				}
				adapter = new ArrayAdapter<String>(HotelSearchActivity.this, android.R.layout.simple_dropdown_item_1line, strings);
				autoCompleteTextView.setAdapter(adapter);
				if (falg) {
					autoCompleteTextView.showDropDown();
				}
			}
		});
		asyncAPIClient.getHotelList(mCityCode);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		falg=false;
	}
}
