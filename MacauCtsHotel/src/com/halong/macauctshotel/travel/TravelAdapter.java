package com.halong.macauctshotel.travel;

import java.util.List;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.entity.async.Travel;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelAdapter extends BaseAdapter {

	private Context mContext;
	private List<Travel> mList;
	private LayoutInflater mLayoutInflater;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mOptions;

	public TravelAdapter(Context context, List<Travel> list) {
		mContext = context;
		mList = list;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mImageLoader = ImageLoader.getInstance();
		this.mOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
	}

	public class ListItemView {
		public TextView title;
		public TextView date;
		public ImageView imageView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mList == null) {
			return 0;
		}
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListItemView listItemView = null;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_sale, null);
			listItemView = new ListItemView();
			listItemView.title = (TextView) convertView.findViewById(R.id.content);
			listItemView.date = (TextView) convertView.findViewById(R.id.date);
			listItemView.imageView = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		Travel travel = mList.get(position);
		listItemView.title.setText(travel.getTitle());
		listItemView.date.setText(travel.getAdd_time());
		
		mImageLoader.displayImage(AsyncAPIClient.PICTURE_NEWS_URL_HALONG+travel.getImg(), listItemView.imageView,mOptions);
		return convertView;
	}
	
	public  void setList(List<Travel> list){
		mList=list;
		notifyDataSetChanged();
	}

}
