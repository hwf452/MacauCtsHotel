package com.halong.macauctshotel.intro;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.util.picutil.ImageDownLoader;
import com.halong.macauctshotel.util.picutil.ImageDownLoader.OnImageLoaderListener;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.widget.HtmlImageGetter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class IntroInfoFragment extends Fragment {
	private HotelDTO mHotelDTO;
	private View view;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mOptions;

	private TextView textView;

	private ImageDownLoader mImageDownLoader;

	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_macau_cts_hotel, null);
		this.mImageLoader = ImageLoader.getInstance();
		this.mOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();

		ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
		mImageLoader.displayImage(AsyncAPIClient.PICTURE_URL + mHotelDTO.getPhoto1(), imageView, mOptions);

		//
		// mImageDownLoader = new ImageDownLoader(getActivity());

		((TextView) view.findViewById(R.id.titleTextView)).setText(mHotelDTO.getName());
		textView = (TextView) view.findViewById(R.id.textView);

		Drawable defaultDrawable = getResources().getDrawable(R.drawable.image_loading_03);
		HtmlImageGetter htmlImageGetter = new HtmlImageGetter(getActivity(), textView, defaultDrawable, AsyncAPIClient.PICTURE_URL_HTML);
		// textView.setText(Html.fromHtml(mHotelDTO.getIntro(), htmlImageGetter,
		// null));

		String regEx_html = "<img[^>]+/>";
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(mHotelDTO.getDescript());
		String htmlStr = m_html.replaceAll("");
		textView.setText(Html.fromHtml(htmlStr));

		return view;
	}

	public void setmHotelDTO(HotelDTO mHotelDTO) {
		this.mHotelDTO = mHotelDTO;
	}
}
