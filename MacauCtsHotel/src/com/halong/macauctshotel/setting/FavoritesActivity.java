/**
 * 
 */
package com.halong.macauctshotel.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.LoginActivity;
import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.async.Collect;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.ApiLanguageUtil;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 
 */
public class FavoritesActivity extends BackActivity {
	public static final int REQUESTCODE5 = 5;
	private ListView listView;
	private BaseAdapter adapter;
	private ImageView imageView;
	private TextView textView1;
	private TextView textView2;

	private List<Collect> list;
	private List<HotelDTO> hotelDTOList;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mOptions;

	private boolean memberMarkGoods = false, getHotelList = false;

	/*
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		this.mImageLoader = ImageLoader.getInstance();
		this.mOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		list = new ArrayList<Collect>();
		listView = (ListView) findViewById(R.id.listview);
		memberMarkGoods();
		getHotelList();
	}

	private void initListView() {
		if (!(getHotelList && memberMarkGoods)) {
			return;
		}
		adapter = new BaseAdapter() {

			class ListViewItem {
				public TextView nameTextView;
				public TextView addressTextView;
				public ImageView imageView;
				public RatingBar ratingBar;
			}

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				ListViewItem listViewItem = null;
				if (arg1 == null) {
					arg1 = getLayoutInflater().inflate(R.layout.item_activity_favorites, null);
					listViewItem = new ListViewItem();
					listViewItem.nameTextView = (TextView) arg1.findViewById(R.id.textView1);
					listViewItem.addressTextView = (TextView) arg1.findViewById(R.id.textView2);
					listViewItem.imageView = (ImageView) arg1.findViewById(R.id.imageView);
					listViewItem.ratingBar = (RatingBar) arg1.findViewById(R.id.ratingBar);
					arg1.setTag(listViewItem);
				} else {
					listViewItem = (ListViewItem) arg1.getTag();
				}

				Collect collect = list.get(arg0);
				listViewItem.nameTextView.setText(collect.getName());
				listViewItem.addressTextView.setText(collect.getAddress());
				listViewItem.ratingBar.setNumStars(5);
				listViewItem.ratingBar.setRating(collect.getStar());

				mImageLoader.displayImage(AsyncAPIClient.PICTURE_URL + collect.getPhoto(), listViewItem.imageView, mOptions);


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
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				String current = list.get(arg2).getHotelid();
				int num = 0;
				for (int i = 0; i < hotelDTOList.size(); i++) {
					if (current.equals(hotelDTOList.get(i).getHotelId()+"")) {
						num=i;
						Log.v("current", current+"-"+num);
						break;
					}
				}
				Log.v("current", current+"-"+num);
				Intent intent1 = new Intent();
				intent1.putExtra("dateFormat", list.get(arg2).getName());
				intent1.putExtra("hotelNum", num);
				Bundle bundle = new Bundle();
				bundle.putSerializable("hotelDTOs", (Serializable) hotelDTOList);
				intent1.putExtras(bundle);
				setResult(REQUESTCODE5, intent1);
				finish();
			}
		});
	}

	private void memberMarkGoods() {
		// TODO Auto-generated method stub
		long custid = SharedPreferencesHelper.getLongValue(this, SaveInfoUtil.CUSTID_KEY_STRING);
		if (custid <= 0) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivityForResult(intent, LoginActivity.LOGINSUCCESS);
			return;
		}
		AsyncAPIClient client = new AsyncAPIClient(this);
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				list = ParseJson.getInstance().memberMarkGoods(FavoritesActivity.this, content);
				if (list == null) {
					Toast.makeText(FavoritesActivity.this, getResources().getString(R.string.data_null_toast), Toast.LENGTH_SHORT).show();
					return;
				} else {
					memberMarkGoods = true;
					initListView();
				}
			}
		});
		client.memberMarkGoods(custid + "");
	}

	private void getHotelList() {
		AsyncAPIClient client = new AsyncAPIClient(this);
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				hotelDTOList = ParseJson.getInstance().getHotelList(FavoritesActivity.this, content, false);
				if (hotelDTOList == null) {
					return;
				} else {
					getHotelList = true;
					initListView();
				}
			}
		});
		client.getHotelList("");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == LoginActivity.LOGINSUCCESS) {
			memberMarkGoods();
		}
	}
}
