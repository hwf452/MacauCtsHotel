/**
 * 
 */
package com.halong.macauctshotel.book;

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
import com.halong.macauctshotel.entity.soap.DcPublic;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 
 */
public class CitySearchActivity extends BackActivity {
	private AutoCompleteTextView autoCompleteTextView;
	private ArrayAdapter<String> adapter;
	private String[] strings;
	public static final int REQUESTCODE3 = 3;

	private List<DcPublic> list;
	private boolean falg = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_search);
		initVIew();
		getDcPublic();
	}

	private void initVIew() {
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoTextView);
		autoCompleteTextView.setThreshold(0);
		autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if ("".equals(autoCompleteTextView.getText().toString().trim())) {
					return;
				}
				Intent intent1 = new Intent();
				intent1.putExtra("dateFormat", autoCompleteTextView.getText().toString().trim());
				intent1.putExtra("code", list.get(arg2).getCode());
				setResult(REQUESTCODE3, intent1);
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
	}

	private void getDcPublic() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				list = ParseJson.getInstance().getDcPublic(CitySearchActivity.this, content);
				if (list==null) {
					return;
				}
				strings = new String[list.size()];
				for (int j = 0; j < list.size(); j++) {
					strings[j] = list.get(j).getName();
				}
				adapter = new ArrayAdapter<String>(CitySearchActivity.this, android.R.layout.simple_dropdown_item_1line, strings);
				autoCompleteTextView.setAdapter(adapter);

				if (falg) {
					autoCompleteTextView.showDropDown();
				}
			}
		});
		asyncAPIClient.getDcPublic("city");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		falg = false;
	}
}
