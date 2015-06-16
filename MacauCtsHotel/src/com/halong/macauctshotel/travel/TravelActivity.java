/**
 * 
 */
package com.halong.macauctshotel.travel;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.entity.async.Travel;
import com.halong.macauctshotel.sale.NewSaleActivity;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * @author 杨锘 创建时间：2014年4月3日 下午6:35:36
 * 
 */
public class TravelActivity extends BackActivity {
	private ListView listView;
	private TravelAdapter adapter;
	private ImageView imageView;
	private TextView contenTextView;
	private List<Travel> list;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel);

		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listView = (ListView) findViewById(R.id.listview);
		post();

	}

	private void post() {
		AsyncAPIClient client = new AsyncAPIClient(this);
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				list = ParseJson.getInstance().newsList(TravelActivity.this, content);
				if (list == null) {
					Toast.makeText(TravelActivity.this, getResources().getString(R.string.data_null_toast), Toast.LENGTH_SHORT).show();
					return ;
				}
				initListview();
			}
		});
		client.newsList("2");
	}

	private void initListview() {
		adapter = new TravelAdapter(this, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				// toOtherActivity(NewSaleActivity.class);
				Intent intent = new Intent();
				intent.setClass(TravelActivity.this, NewSaleActivity.class);
				intent.putExtra("travel", list.get(arg2));
				startActivity(intent);
			}
		});
	}

}
