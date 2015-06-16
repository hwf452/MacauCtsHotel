package com.halong.macauctshotel.book;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.book.KCalendar.OnCalendarClickListener;
import com.halong.macauctshotel.book.KCalendar.OnCalendarDateChangedListener;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class KCalendarActivity extends BackActivity {
	public static final int RESULT_CODE1 = 1;
	public static final int RESULT_CODE2 = 2;
	private TextView tv1, tv2;
	private Button btn2, btn3;

	private String mDateFormat = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final KCalendar calendar = new KCalendar(this);
		View view = getLayoutInflater().inflate(R.layout.kcalendar_activity, null);
		RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout2);
		relativeLayout.addView(calendar);
		setContentView(view);
		tv1 = (TextView) view.findViewById(R.id.textView1);
		tv2 = (TextView) view.findViewById(R.id.textView2);
		btn2 = (Button) view.findViewById(R.id.button2);
		btn3 = (Button) view.findViewById(R.id.button3);

		tv2.setText(calendar.getCalendarYear() + "年" + calendar.getCalendarMonth() + "月");

		Intent intent = getIntent();
		tv1.setText(intent.getExtras().getInt("titleText"));

		mDateFormat = getIntent().getStringExtra("dateFormat");

		calendar.setOnCalendarClickListener(new OnCalendarClickListener() {
			@Override
			public void onCalendarClick(int row, int col, String dateFormat) {
				// Toast.makeText(, dateFormat, Toast.LENGTH_SHORT).show();
				String nextDay = null;
				String nextMonday = null;
				String nextYear = null;
				int days = calendar.getDateNum(calendar.getCalendarYear(), calendar.getCalendarMonth() - 1);
				int cur_day = Integer.valueOf(dateFormat.substring(8, 10));
				if (calendar.getCalendarMonth() < 12) {
					if (cur_day < days) {
						if (cur_day < 9) {
							nextDay = "0" + String.valueOf(cur_day + 1);
							if (calendar.getCalendarMonth() < 10) {
								nextMonday = "0" + String.valueOf(calendar.getCalendarMonth());
							} else {
								nextMonday = String.valueOf(calendar.getCalendarMonth());
							}

						} else {
							nextDay = String.valueOf(cur_day + 1);
							if (calendar.getCalendarMonth() < 10) {
								nextMonday = "0" + String.valueOf(calendar.getCalendarMonth());
							} else {
								nextMonday = String.valueOf(calendar.getCalendarMonth());
							}

						}

					} else {
						nextDay = "01";
						if (calendar.getCalendarMonth() < 10) {
							nextMonday = "0" + String.valueOf(calendar.getCalendarMonth() + 1);
						} else {
							nextMonday = String.valueOf(calendar.getCalendarMonth() + 1);
						}

					}

					nextYear = String.valueOf(calendar.getCalendarYear());
				} else {
					nextYear = String.valueOf(calendar.getCalendarYear() + 1);
				}
				String nextDateString = null;
				if (nextYear != null && nextMonday != null && nextDay != null) {
					nextDateString = nextYear + "-" + nextMonday + "-" + nextDay;
				}

				if (!(mDateFormat == null || "".equals(mDateFormat))) {
					// 设定时间的模板
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// 得到指定模范的时间
					try {
						Date d1 = sdf.parse(mDateFormat);
						Date d2 = sdf.parse(dateFormat);
						if (d1.getTime() >= d2.getTime()) {
							if (tv1.getText().toString().equals(getResources().getString(R.string.credit_date))) {
								Toast.makeText(KCalendarActivity.this, getResources().getString(R.string.credit_date), Toast.LENGTH_SHORT).show();
								return;
							}
							Toast.makeText(KCalendarActivity.this, getResources().getString(R.string.date_toast), Toast.LENGTH_SHORT).show();
							return;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				if (nextDateString != null) {
					Intent intent1 = new Intent();
					intent1.putExtra("nextDateString", nextDateString);
					intent1.putExtra("dateFormat", dateFormat);
					setResult(RESULT_CODE1, intent1);
					finish();
				}

			}
		});

		calendar.setOnCalendarDateChangedListener(new OnCalendarDateChangedListener() {
			@Override
			public void onCalendarDateChanged(int year, int month) {

				tv2.setText(year + "年" + month + "月");
			}
		});
		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				calendar.lastMonth();

			}
		});
		btn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				calendar.nextMonth();

			}
		});

	}

	@SuppressWarnings("static-access")
	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {

		}

		// 切换为横屏

		else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {

		}

	}
}
