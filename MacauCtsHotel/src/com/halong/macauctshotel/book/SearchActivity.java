/**
 * 
 */
package com.halong.macauctshotel.book;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.entity.soap.RoomRateSearchDTO;
import com.halong.macauctshotel.util.AppStackManager;
import com.halong.macauctshotel.util.DateUtil;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;

/**
 * 
 */
public class SearchActivity extends BackActivity {

	private ListView listView;
	private SearchAdapter adapter;
	private TextView liveTimeTextView, leaveTimeTextView, homesTextView;
	private LinearLayout homesLayout, liveTimeLayout, leaveTimeLayout;
	private OnClickListener onClickListener;
	private static final int REQUESTCODE1 = 1, REQUESTCODE2 = 2;

	private RoomRateSearchDTO mRoomRateSearchDTO;

	private String mCityCode;
	private List<HotelDTO> list;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		AppStackManager.getMyAppStackManager().addActivity(this);

		setContentView(R.layout.activity_search);

		homesLayout = (LinearLayout) findViewById(R.id.homesLayout);
		liveTimeLayout = (LinearLayout) findViewById(R.id.liveTimeLayout);
		leaveTimeLayout = (LinearLayout) findViewById(R.id.leaveTimeLayout);
		homesTextView = (TextView) findViewById(R.id.homesTextView);
		liveTimeTextView = (TextView) findViewById(R.id.liveTimeTextView);
		leaveTimeTextView = (TextView) findViewById(R.id.leaveTimeTextView);

		// 设置房间数、住店离店日期
		mRoomRateSearchDTO = (RoomRateSearchDTO) getIntent().getSerializableExtra("mRoomRateSearchDTO");
		String[] arrays = getResources().getStringArray(R.array.rooms_number_spinner);
		homesTextView.setText(arrays[Integer.parseInt(mRoomRateSearchDTO.getRoomNum()) - 1]);
		liveTimeTextView.setText(mRoomRateSearchDTO.getApprDate().toString());
		leaveTimeTextView.setText(mRoomRateSearchDTO.getDepDate().toString());
		mCityCode = getIntent().getStringExtra("mCityCode");

		onClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.homesLayout:
					showDialog(homesTextView);
					break;
				case R.id.liveTimeLayout:
					Intent intent = new Intent(SearchActivity.this, KCalendarActivity.class);
					DateUtil dateUtil = new DateUtil();
					intent.putExtra("dateFormat", dateUtil.getDateOfYesterday());
					intent.putExtra("titleText", R.string.live_time2);
					startActivityForResult(intent, REQUESTCODE1);
					break;
				case R.id.leaveTimeLayout:
					String dateFormat = liveTimeTextView.getText().toString().trim();
					Intent intent1 = new Intent(SearchActivity.this, KCalendarActivity.class);
					intent1.putExtra("dateFormat", dateFormat);
					intent1.putExtra("titleText", R.string.leave_time2);
					startActivityForResult(intent1, REQUESTCODE2);
					break;
				default:
					break;
				}
			}
		};
		liveTimeLayout.setOnClickListener(onClickListener);
		leaveTimeLayout.setOnClickListener(onClickListener);
		homesLayout.setOnClickListener(onClickListener);

		listView = (ListView) findViewById(R.id.listview);
		getHotelList();
	}

	private void getHotelList() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				list = ParseJson.getInstance().getHotelList(SearchActivity.this, content, false);
				if (list == null) {
					return;
				}
				adapter = new SearchAdapter(SearchActivity.this, list, mRoomRateSearchDTO);
				listView.setAdapter(adapter);
			}
		});
		asyncAPIClient.getHotelList(mCityCode);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE1) {
			if (resultCode == KCalendarActivity.RESULT_CODE1) {
				String string = data.getStringExtra("dateFormat");
				String string2 = data.getStringExtra("nextDateString");
				liveTimeTextView.setText(string);
				 leaveTimeTextView.setText(string2);
				mRoomRateSearchDTO.setApprDate(string);
				mRoomRateSearchDTO.setDepDate(string2);
			}
		}
		if (requestCode == REQUESTCODE2) {
			if (resultCode == KCalendarActivity.RESULT_CODE1) {
				String string = data.getStringExtra("dateFormat");
				leaveTimeTextView.setText(string);
				mRoomRateSearchDTO.setDepDate(string);
			}

		}
		// adapter.setRoomRateSearchDTO(mRoomRateSearchDTO);

	}

	private void showDialog(final TextView textView) {
		Dialog dialog = new Dialog(getApplicationContext());
		Builder build = new AlertDialog.Builder(this);
		build.setSingleChoiceItems(R.array.rooms_number_spinner, 0, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				textView.setText(getResources().getStringArray(R.array.rooms_number_spinner)[arg1]);
				mRoomRateSearchDTO.setRoomNum((arg1 + 1) + "");
			}
		}).setPositiveButton(R.string.OK, null);
		dialog = build.create();
		dialog.show();
	};
}
