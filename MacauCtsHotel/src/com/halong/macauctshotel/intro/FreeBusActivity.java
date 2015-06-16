/**
 * 
 */
package com.halong.macauctshotel.intro;

import java.io.InputStream;

import android.os.Bundle;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.common.DragImageView;
import com.halong.macauctshotel.common.BitmapUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 
 */
public class FreeBusActivity extends BackActivity {
	private int window_width, window_height;

	private DragImageView dragImageView;
	private int state_height;

	private ViewTreeObserver viewTreeObserver;

	@SuppressWarnings("deprecation")
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_free_bus);
		Toast.makeText(getApplicationContext(), R.string.touch, Toast.LENGTH_SHORT).show();
		WindowManager manager = getWindowManager();
		window_width = manager.getDefaultDisplay().getWidth();
		window_height = manager.getDefaultDisplay().getHeight()-R.dimen.top_title_height;

		dragImageView = (DragImageView) findViewById(R.id.div_main);
		Bitmap bmp = BitmapUtil.ReadBitmapById(this, R.drawable.shuttle_bus_schedule,
				window_width, window_height);
		dragImageView.setImageBitmap(bmp);
		dragImageView.setmActivity(this);
		viewTreeObserver = dragImageView.getViewTreeObserver();
		viewTreeObserver
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						if (state_height == 0) {
							Rect frame = new Rect();
							getWindow().getDecorView()
									.getWindowVisibleDisplayFrame(frame);
							state_height = frame.top;
							dragImageView.setScreen_H(window_height-state_height);
							dragImageView.setScreen_W(window_width);
						}

					}
				});

	}

	/**
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap ReadBitmapById(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

}