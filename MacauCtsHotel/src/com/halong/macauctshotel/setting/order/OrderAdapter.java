package com.halong.macauctshotel.setting.order;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

public class OrderAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<Gres> mList;
	private Dialog dialog;

	public OrderAdapter(Context context, List<Gres> list) {
		this.mContext = context;
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mList = list;
	}

	class ListItemView {
		RelativeLayout itemsOrderRelativeLayout;
		TextView orderNumberTextView;
		TextView hotelNameTextView;
		TextView orderDateTextView;
		TextView orderListViewItemsButton;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListItemView listItemView = null;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_fragement_order_mid, null);
			listItemView = new ListItemView();
			listItemView.hotelNameTextView = (TextView) convertView.findViewById(R.id.hotelNameTextView);
			listItemView.itemsOrderRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.itemsOrder);
			listItemView.orderDateTextView = (TextView) convertView.findViewById(R.id.orderDateTextView);
			listItemView.orderNumberTextView = (TextView) convertView.findViewById(R.id.orderNumberTextView);
			listItemView.orderListViewItemsButton = (TextView) convertView.findViewById(R.id.orderListViewItemsButton);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		final Gres gres = mList.get(position);

		listItemView.hotelNameTextView.setText(gres.getHotelName());
		listItemView.orderDateTextView.setText(gres.getResDate());
		listItemView.orderNumberTextView.setText(mContext.getResources().getString(R.string.order_number) + gres.getAccId());

		if (position == 0) {
			listItemView.itemsOrderRelativeLayout.setBackgroundResource(R.drawable.list_column10_1_background);
		} else if (position == (getCount() - 1)) {
			listItemView.itemsOrderRelativeLayout.setBackgroundResource(R.drawable.list_column10_3_background);
		} else {
			listItemView.itemsOrderRelativeLayout.setBackgroundResource(R.drawable.list_column10_2_background);
		}

		if (getCount() == 1) {
			listItemView.itemsOrderRelativeLayout.setBackgroundResource(R.drawable.list_column10_4_background);
		}

		listItemView.orderListViewItemsButton.setText(gres.getStatusName());

		if ("R".equals(gres.getStatus()) || "W".equals(gres.getStatus())) {
			listItemView.orderListViewItemsButton.setText(mContext.getResources().getString(R.string.cancel));
			listItemView.orderListViewItemsButton.setBackgroundResource(R.drawable.cancel_button_selected);
			listItemView.orderListViewItemsButton.setTextColor(mContext.getResources().getColor(R.color.button_text_color));
			listItemView.orderListViewItemsButton.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDilag(position);
				}
			});
		} else if ("I".equals(gres.getStatus())) {
			listItemView.orderListViewItemsButton.setBackgroundResource(android.R.color.transparent);
			listItemView.orderListViewItemsButton.setTextColor(mContext.getResources().getColor(R.color.light_text_color));
			listItemView.orderListViewItemsButton.setOnClickListener(null);
		} else if ("N".equals(gres.getStatus())) {
			listItemView.orderListViewItemsButton.setBackgroundResource(android.R.color.transparent);
			listItemView.orderListViewItemsButton.setTextColor(mContext.getResources().getColor(R.color.light_text_color));
			listItemView.orderListViewItemsButton.setOnClickListener(null);
		} else if ("X".equals(gres.getStatus())) {
			listItemView.orderListViewItemsButton.setText(mContext.getResources().getString(R.string.canceled));
			listItemView.orderListViewItemsButton.setBackgroundResource(android.R.color.transparent);
			listItemView.orderListViewItemsButton.setTextColor(mContext.getResources().getColor(R.color.light_text_color));
			listItemView.orderListViewItemsButton.setOnClickListener(null);
		} else if ("O".equals(gres.getStatus()) || "H".equals(gres.getStatus())) {
			if (!"".equals(gres.getComment())) {
				listItemView.orderListViewItemsButton.setText(mContext.getResources().getString(R.string.commented));
				listItemView.orderListViewItemsButton.setBackgroundResource(android.R.color.transparent);
				listItemView.orderListViewItemsButton.setTextColor(mContext.getResources().getColor(R.color.light_text_color));
				listItemView.orderListViewItemsButton.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mContext, OrderManagerDetailActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("gres", mList.get(position));
						intent.putExtras(bundle);
						mContext.startActivity(intent);
					}
				});
			} else {
				listItemView.orderListViewItemsButton.setText(mContext.getResources().getString(R.string.comment));
				listItemView.orderListViewItemsButton.setBackgroundResource(R.drawable.cancel_button_selected);
				listItemView.orderListViewItemsButton.setTextColor(mContext.getResources().getColor(R.color.button_text_color));
				listItemView.orderListViewItemsButton.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mContext, OrderCommentActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("gres", mList.get(position));
						intent.putExtras(bundle);
						mContext.startActivity(intent);
					}
				});
			}
		} else if ("H".equals(gres.getStatus())) {
			listItemView.orderListViewItemsButton.setBackgroundResource(R.drawable.cancel_button_selected);
			listItemView.orderListViewItemsButton.setTextColor(mContext.getResources().getColor(R.color.button_text_color));
			listItemView.orderListViewItemsButton.setOnClickListener(null);
		}

		convertView.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, OrderManagerDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("gres", gres);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	/**
	 * 
	 */
	private void showDilag(final int items) {
		dialog = null;
		Builder builder = new AlertDialog.Builder(mContext);
		builder.setPositiveButton(R.string.OK, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				cancelOrderInterface(items);
				dialog.dismiss();
			}
		}).setNegativeButton(R.string.cancel, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}).setMessage(R.string.cancel_order);
		dialog = builder.create();
		dialog.show();
	}

	private void cancelOrderInterface(final int items) {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(mContext);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				boolean result = ParseJson.getInstance().cancelOrderInterface(mContext, content);
				if (result) {
					mList.remove(items);
					notifyDataSetChanged();
					Toast.makeText(mContext, mContext.getResources().getString(R.string.action_success_toast), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(String content) {
				// TODO Auto-generated method stub
				super.onFailure(content);
			}
		});
		asyncAPIClient.cancelOrderInterface(mList.get(items).getResId());
	}

}
