/**
 * 
 */
package com.halong.macauctshotel.setting.order;

import java.util.List;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.entity.soap.OrderDTO;
import com.halong.macauctshotel.setting.OrderFragmentAcitivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 
 */
public class NewOrderFragment extends Fragment{
	private ListView listView;
	private BaseAdapter adapter;
	private TextView cancelButton;
	private Dialog dialog;
	private int listviewItemsRow = 5;
	
	private OrderDTO mOrderDTO  ;
	
	private List<Gres> mList;
	private OrderAdapter mAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragement_new_order, null);
		mList=((OrderFragmentAcitivity)getActivity()).getmNewList();
		listView = (ListView) view.findViewById(R.id.listview);
		
		View view2 = new View(getActivity());
		view2.setMinimumHeight(30);
		listView.addHeaderView(view2);
		listView.addFooterView(view2);
		
		mAdapter = new OrderAdapter(getActivity(), mList);
		listView.setAdapter(mAdapter);
		
		return view;
	}

	/**
	 * 
	 */
	private void showDilag(int items) {
		dialog = null;
		Builder builder = new AlertDialog.Builder(getActivity());
		builder.setPositiveButton(R.string.OK, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				listviewItemsRow = listviewItemsRow - 1;
				adapter.notifyDataSetChanged();
				
				listView.setAdapter(adapter);
			}
		}).setNegativeButton(R.string.cancel, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		}).setMessage(R.string.cancel_order);
		dialog = builder.create();
		dialog.show();
	}

	

}
