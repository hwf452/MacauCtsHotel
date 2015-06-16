package com.halong.macauctshotel.book;

import java.util.List;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.entity.soap.RoomRateSearchDTO;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<HotelDTO> mList;

	private ImageLoader mImageLoader;
	private DisplayImageOptions mOptions;
	private RoomRateSearchDTO mRoomRateSearchDTO;

	public SearchAdapter(Context context, List<HotelDTO> list, RoomRateSearchDTO roomRateSearchDTO) {
		this.mContext = context;
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mList = list;
		this.mRoomRateSearchDTO = roomRateSearchDTO;
		this.mImageLoader = ImageLoader.getInstance();
		this.mOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
	}

	static class ListItemView {
		ImageView imageView1;
		TextView nameTextView;
		TextView addressTextView;
		TextView priveTextView;
		TextView bookButton;
		RatingBar ratingBar;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListItemView listItemView;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_activity_search, null);
			listItemView = new ListItemView();
			listItemView.imageView1 = (ImageView) convertView.findViewById(R.id.imageView);
			listItemView.nameTextView = (TextView) convertView.findViewById(R.id.textView1);
			listItemView.addressTextView = (TextView) convertView.findViewById(R.id.textView2);
			listItemView.priveTextView = (TextView) convertView.findViewById(R.id.price);
			listItemView.bookButton = (TextView) convertView.findViewById(R.id.bookButton);
			listItemView.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		HotelDTO hotelDTO = mList.get(position);
		listItemView.nameTextView.setText(hotelDTO.getName());
		if ("anyType{}".equals(hotelDTO.getAddress())) {
			hotelDTO.setAddress("1");
		}
		listItemView.addressTextView.setText(hotelDTO.getAddress());

		if ("anyType{}".equals(hotelDTO.getCurrency())) {
			hotelDTO.setCurrency("");
		}
		listItemView.priveTextView.setText(hotelDTO.getCurrency());

		String price = hotelDTO.getCurrency();
		if ("CNY".equals(hotelDTO.getCurrency())) {
			price = price + "¥ ";
		} else if ("HKD".equals(hotelDTO.getCurrency())) {
			price = price + "$ ";
		} else {
			price = price + " ";
		}
		listItemView.priveTextView.setText(price + "");
		listItemView.priveTextView.setText(price + hotelDTO.getLowestPrice());
		listItemView.priveTextView.setVisibility(View.INVISIBLE);

		mImageLoader.displayImage(AsyncAPIClient.PICTURE_URL + hotelDTO.getPhoto1(), listItemView.imageView1, mOptions);

		listItemView.ratingBar.setNumStars(5);
		listItemView.ratingBar.setRating(Float.parseFloat(hotelDTO.getStar()));

		if ("0".equals(hotelDTO.getStatus())) {
			listItemView.bookButton.setText(mContext.getResources().getString(R.string.roomratews_full));
			listItemView.bookButton.setBackgroundResource(android.R.color.transparent);
			convertView.setOnClickListener(null);
		} else {
			listItemView.bookButton.setText(mContext.getResources().getString(R.string.roomratews_null));
			listItemView.bookButton.setBackgroundResource(R.drawable.cancel_button_selected);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext, HotelDetailsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("mHotelDTO", mList.get(position));
					bundle.putSerializable("mRoomRateSearchDTO", mRoomRateSearchDTO);
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
			});
		}

		return convertView;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	public void setData(List<HotelDTO> list) {
		mList = list;
		notifyDataSetChanged();
	}

	public void setRoomRateSearchDTO(RoomRateSearchDTO roomRateSearchDTO) {
		this.mRoomRateSearchDTO = roomRateSearchDTO;
	}

}
