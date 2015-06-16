package com.halong.macauctshotel.widget;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html.ImageGetter;
import android.widget.TextView;

public class HtmlImageGetter implements ImageGetter {
	private TextView _htmlText;
	private Drawable _defaultDrawable;
	private String TAG = "HtmlImageGetter";
	private String _imgPath = "/CtsHotel";
	private String url;
	private Context mContext;

	public HtmlImageGetter(Context context, TextView htmlText, Drawable defaultDrawable, String url) {
		this.url = url;
		FileUtil.gc();
		this.mContext = context;
		_htmlText = htmlText;
		_defaultDrawable = defaultDrawable;
		FileUtil.gc();

	}

	@Override
	public Drawable getDrawable(String imgUrl) {

		if (imgUrl.contains("http://")) {
			return _defaultDrawable;
		} else {
			imgUrl = url + imgUrl;
		}

		String imgKey = String.valueOf(imgUrl.hashCode());
		String path = Environment.getExternalStorageDirectory() + _imgPath;
		FileUtil.createPath(path);

		String[] ss = imgUrl.split("\\.");
		String imgX = ss[ss.length - 1];
		imgKey = path + "/" + imgKey + "." + imgX;

		if (FileUtil.exists(imgKey)) {
			Drawable drawable = FileUtil.getImageDrawable(imgKey);
			if (drawable != null) {
				drawable.setBounds(0, 0, dip2px(drawable.getIntrinsicWidth()), dip2px(drawable.getIntrinsicHeight()));
				return drawable;
			} else {
			}
		}

		URLDrawable urlDrawable = new URLDrawable(_defaultDrawable);
		new AsyncThread(urlDrawable).execute(imgKey, imgUrl);
		return urlDrawable;
	}
	
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public  int dip2px(float dpValue) {  
        final float scale = mContext.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    } 

	private class AsyncThread extends AsyncTask<String, Integer, Drawable> {
		private String imgKey;
		private URLDrawable _drawable;

		public AsyncThread(URLDrawable drawable) {
			_drawable = drawable;
		}

		@Override
		protected Drawable doInBackground(String... strings) {
			imgKey = strings[0];
			InputStream inps = NetWork.getInputStream(strings[1]);
			if (inps == null)
				return _drawable;

			FileUtil.saveFile(imgKey, inps);
			Drawable drawable = Drawable.createFromPath(imgKey);
			return drawable;
		}

		public void onProgressUpdate(Integer... value) {

		}

		@Override
		protected void onPostExecute(Drawable result) {
			_drawable.setDrawable(result);
			_htmlText.setText(_htmlText.getText());
		}
	}

	public class URLDrawable extends BitmapDrawable {

		private Drawable drawable;

		public URLDrawable(Drawable defaultDraw) {
			setDrawable(defaultDraw);
		}

		private void setDrawable(Drawable ndrawable) {
			drawable = ndrawable;
			drawable.setBounds(0, 0,dip2px( drawable.getIntrinsicWidth()), dip2px(drawable.getIntrinsicHeight()));
			setBounds(0, 0, dip2px(drawable.getIntrinsicWidth()), dip2px(drawable.getIntrinsicHeight()));
		}

		@Override
		public void draw(Canvas canvas) {
			drawable.draw(canvas);
		}
	}
}
