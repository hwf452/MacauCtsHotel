/**
 * 
 */
package com.halong.macauctshotel;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.common.FragmentToOtherActivity;
import com.halong.macauctshotel.entity.async.Travel;
import com.halong.macauctshotel.entity.soap.Sale;
import com.halong.macauctshotel.sale.NewSaleActivity;
import com.halong.macauctshotel.travel.TravelAdapter;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 
 */
public class SaleFragment extends FragmentToOtherActivity {
	private ListView listView;
	private BaseAdapter adapter;

	private List<Travel> list;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_sale, null);

		view.findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		listView = (ListView) view.findViewById(R.id.listview);

		post();
		return view;
	}

	private void post() {
		AsyncAPIClient client = new AsyncAPIClient(getActivity());
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				list = ParseJson.getInstance().newsList(getActivity(), content);
				if (list == null) {
					Toast.makeText(getActivity(), getResources().getString(R.string.data_null_toast), Toast.LENGTH_SHORT).show();
					return;
				}
				initListview();
			}
		});
		client.newsList("1");
	}

	private void initListview() {
		adapter = new TravelAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				// toOtherActivity(NewSaleActivity.class);
				Intent intent = new Intent();
				intent.setClass(getActivity(), NewSaleActivity.class);
				intent.putExtra("travel", list.get(arg2));
				startActivity(intent);
			}
		});
	}

}
