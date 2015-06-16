/**
 * 
 */
package com.halong.macauctshotel.setting.order;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.FragmentToOtherActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.setting.CommentActivity2;
import com.halong.macauctshotel.setting.OrderFragmentAcitivity;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;

/**
 * 
 */
public class HistoryOrderFragment extends FragmentToOtherActivity {
	private ListView listView;
	private TextView cancelButton;

	private List<Gres> mList;
	private OrderAdapter mAdapter;

	private ArrayList<String> ordernums;

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
		View view = inflater.inflate(R.layout.fragement_history_order, null);
		listView = (ListView) view.findViewById(R.id.listview);
		mList = ((OrderFragmentAcitivity) getActivity()).getmOldList();
		
		View view2 = new View(getActivity());
		view2.setMinimumHeight(30);
		listView.addHeaderView(view2);
		listView.addFooterView(view2);
		
		mAdapter = new OrderAdapter(getActivity(), mList);
		listView.setAdapter(mAdapter);
		return view;
	}

}
