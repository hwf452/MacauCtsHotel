/**
 * 
 */
package com.halong.macauctshotel.sale;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.entity.async.Travel;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;
import com.halong.macauctshotel.widget.HtmlImageGetter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 */
public class NewSaleActivity extends BackActivity {
	private TextView textView1;
	private TextView textView;
	private ImageView imageView;

	private Travel travel;
	private int id;

	private ImageLoader mImageLoader;
	private DisplayImageOptions mOptions;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.mImageLoader = ImageLoader.getInstance();
		this.mOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		
		setContentView(R.layout.activity_new_sale);
		textView1 = (TextView) findViewById(R.id.contentTextView);
		textView = (TextView) findViewById(R.id.titleTextView);
		imageView = (ImageView) findViewById(R.id.imageView);
		travel = (Travel) getIntent().getSerializableExtra("travel");
		if (travel != null) {
			post();
		}
	}

	private void post() {
		AsyncAPIClient client = new AsyncAPIClient(this);
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				travel = ParseJson.getInstance().newsOne(NewSaleActivity.this, content);
				initInfo();
			}
		});
		client.newsOne(travel.getId() + "");
	}

	private void initInfo() {
		textView.setText(travel.getTitle());

		Drawable defaultDrawable = getResources().getDrawable(R.drawable.image_loading_01);
		HtmlImageGetter htmlImageGetter = new HtmlImageGetter(this, textView1, defaultDrawable, AsyncAPIClient.PICTURE_URL_HALONG_HTML);
		textView1.setText(Html.fromHtml(travel.getInfo(), htmlImageGetter, null));
		
		mImageLoader.displayImage(AsyncAPIClient.PICTURE_NEWS_URL_HALONG+travel.getImg(), imageView,mOptions);
	}
}
