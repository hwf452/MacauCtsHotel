/**
 * 
 */
package com.halong.macauctshotel.setting;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.CustSrvRecord;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 
 */
public class CommentActivity extends BackActivity {
	private ListView listView;
	private BaseAdapter adapter;

	private List<CustSrvRecord> list;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		listView = (ListView) findViewById(R.id.listview);
		post();
	}

	private void initListView() {
		adapter = new BaseAdapter() {

			class ListItemView {
				public TextView textView;
				public TextView textView1;
				public RelativeLayout relativeLayout;
			}

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				ListItemView listItemView = null;
				if (arg1 == null) {
					arg1 = getLayoutInflater().inflate(R.layout.item_activity_comment_mid, null);
					listItemView = new ListItemView();
					listItemView.textView = (TextView) arg1.findViewById(R.id.orderNumberTextView);
					listItemView.textView1 = (TextView) arg1.findViewById(R.id.dateTextView);
					listItemView.relativeLayout = (RelativeLayout) arg1.findViewById(R.id.itemsComment);
					arg1.setTag(listItemView);
				} else {
					listItemView = (ListItemView) arg1.getTag();
				}

				if (arg0 == 0) {
					listItemView.relativeLayout.setBackgroundResource(R.drawable.list_column10_1_background);
				} else if (arg0 == getCount() - 1) {
					listItemView.relativeLayout.setBackgroundResource(R.drawable.list_column10_3_background);
				} else {
					listItemView.relativeLayout.setBackgroundResource(R.drawable.list_column10_2_background);
				}

				if (getCount() == 1) {
					listItemView.relativeLayout.setBackgroundResource(R.drawable.list_column10_4_background);
				}
				CustSrvRecord custSrvRecord = list.get(arg0);
				listItemView.textView.setText(getResources().getString(R.string.order_number) + custSrvRecord.getOrdernum());
				listItemView.textView1.setText(custSrvRecord.getAddtime());

				return arg1;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return list.get(arg0);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}

		};
		View view = new View(CommentActivity.this);
		view.setMinimumHeight(30);
		listView.addFooterView(view);
		listView.addHeaderView(view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CommentActivity.this, CommentActivity2.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("CustSrvRecord", list.get(position-1));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void post() {
		AsyncAPIClient client = new AsyncAPIClient(this);
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				list = ParseJson.getInstance().orderUserComment(CommentActivity.this, content);
				if (list == null) {
					Toast.makeText(CommentActivity.this, getResources().getString(R.string.data_null_toast), Toast.LENGTH_SHORT).show();
				} else {
					initListView();
				}
			}
		});
		client.orderUserComment(SharedPreferencesHelper.getLongValue(CommentActivity.this, SaveInfoUtil.CUSTID_KEY_STRING) + "");
	}
}
