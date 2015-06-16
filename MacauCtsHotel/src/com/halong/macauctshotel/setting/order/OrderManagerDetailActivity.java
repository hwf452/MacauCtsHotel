package com.halong.macauctshotel.setting.order;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.entity.soap.Gres;

public class OrderManagerDetailActivity extends BackActivity {

	private Gres mGres;

	private TextView textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10,textView23;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_manager_detail_activity);

		mGres = (Gres) getIntent().getSerializableExtra("gres");

		((TextView) findViewById(R.id.textView2)).setText(mGres.getAccId() + "");
		((TextView) findViewById(R.id.textView4)).setText(mGres.getRmRate() + mGres.getCurrencyname());
		((TextView) findViewById(R.id.textView6)).setText(mGres.getHotelName());
		((TextView) findViewById(R.id.textView8)).setText(mGres.getRmTypeName());
		((TextView) findViewById(R.id.textView22)).setText(getResources().getStringArray(R.array.rooms_number_spinner)[(int) mGres.getRmQty() - 1]);
		((TextView) findViewById(R.id.textView10)).setText(mGres.getHotelAddres());
		((TextView) findViewById(R.id.textView12)).setText(mGres.getHoteletel());
		((TextView) findViewById(R.id.textView14)).setText(mGres.getArrDate() + getString(R.string.to) + mGres.getDepDate());
		((TextView) findViewById(R.id.textView16)).setText(mGres.getBooker());
		((TextView) findViewById(R.id.textView18)).setText(mGres.getBookTel());
		((TextView) findViewById(R.id.textView20)).setText(mGres.getRemarks());
		
	}

	public void changeOrder(View view) {
		final Dialog callDialog = new Dialog(OrderManagerDetailActivity.this, R.style.MyDialog);
		callDialog.setContentView(R.layout.dialog_call);
		callDialog.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callDialog.dismiss();
			}
		});
		callDialog.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callDialog.dismiss();
				startActivity(new Intent().setAction(Intent.ACTION_CALL).setData(Uri.parse("tel:" + mGres.getHoteletel())));
			}
		});
		callDialog.show();
	}
}
