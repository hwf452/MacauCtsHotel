/**
 * 
 */
package com.halong.macauctshotel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.halong.macauctshotel.book.PhotoViewActivity;
import com.halong.macauctshotel.common.HotelStrings;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.intro.FreeBusActivity;
import com.halong.macauctshotel.travel.TravelActivity;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.ApiLanguageUtil;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 
 */
public class MainActivity extends Activity {
	private ViewPager viewPager;
	private HashMap<Integer, View> views = new HashMap<Integer, View>();
	// private List<View> views = new ArrayList<View>();
	private View view;
	private int[] guideIcons;
	private ImageView[] guideIconImageViews;
	private int defaultLanguage = R.id.defaultRdo;

	public final static String CHOICELANGUAGESTRING = "choice_language";

	public List<String> mImageList;
	public LinearLayout mLinearLayout;

	public ImageLoader mImageLoader;
	public DisplayImageOptions mOptions;

	/*
	 * (non-Javadoc) dsfdsfdsfdsfd
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ApiLanguageUtil.setmContext(getApplicationContext());
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		seletLanguage(SharedPreferencesHelper.getIntValue(this, CHOICELANGUAGESTRING, defaultLanguage));
		setContentView(R.layout.acitvity_main);

		this.mImageLoader = ImageLoader.getInstance();
		mImageLoader.init(ImageLoaderConfiguration.createDefault(this));
		mOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();

		focusImg();

		autoLogin();
	}

	private void initViewPager() {
		// TODO Auto-generated method stub
		mLinearLayout = (LinearLayout) findViewById(R.id.guideIconLayout);
		guideIconImageViews = new ImageView[mImageList.size()];
		guideIcons = new int[mImageList.size()];
		for (int i = 0; i < mImageList.size(); i++) {
			FrameLayout frameLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.item_fragment_icon, null);
			ImageView iconImageView = (ImageView) frameLayout.findViewById(R.id.imageView);
			iconImageView.setImageResource(R.drawable.guide_icon_selected);
			iconImageView.setId(i);
			guideIcons[i] = i;
			guideIconImageViews[i] = iconImageView;
			if (i == 0) {
				guideIconImageViews[i].setSelected(true);
			}
			mLinearLayout.addView(frameLayout);
		}

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		PagerAdapter adapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mImageList.size();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.support.v4.view.PagerAdapter#destroyItem(android.view
			 * .ViewGroup, int, java.lang.Object)
			 */
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
				container.removeView((View) object);
				views.remove(position);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.support.v4.view.PagerAdapter#instantiateItem(android.
			 * view.ViewGroup, int)
			 */
			@Override
			public Object instantiateItem(ViewGroup container, final int position) {
				// TODO Auto-generated method stub
				ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_imageview, null);
				imageView.setBackgroundResource(R.drawable.image_loading_01);
				mImageLoader.displayImage(mImageList.get(position), imageView, mOptions);
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MainActivity.this, PhotoViewActivity.class);
						String[] imageStrings = mImageList.toArray(new String[mImageList.size()]);
						intent.putExtra("photo", imageStrings);
						intent.putExtra("i", position);
						startActivity(intent);
					}
				});
				views.put(position, imageView);
				container.addView(imageView);
				return imageView;
			}

		};
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < guideIconImageViews.length; i++) {
					if (i == arg0) {
						guideIconImageViews[i].setSelected(true);
					} else {
						guideIconImageViews[i].setSelected(false);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 获取广告图片
	 */
	private void focusImg() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				mImageList = ParseJson.getInstance().focusImg(MainActivity.this, content);
				if (mImageList != null) {
					initViewPager();
				}
			}

		});
		asyncAPIClient.focusImg();
	}

	/**
	 * 自动登陆
	 */
	private void autoLogin() {
		if (SharedPreferencesHelper.getIntValue(MainActivity.this, SaveInfoUtil.AUTOLOGIN_KEY_STRING, 0) == 0) {
			return;
		}
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				boolean result = ParseJson.getInstance().loginGetInterface(MainActivity.this, content);
				if (result) {

				} else {
					SharedPreferencesHelper.clear(MainActivity.this);
				}
			}

			@Override
			public void onFailure(String content) {
				// TODO Auto-generated method stub
				super.onFailure(content);
				SharedPreferencesHelper.clear(MainActivity.this);
			}
		});
		asyncAPIClient.loginGetInterface(SharedPreferencesHelper.getStringValue(MainActivity.this, SaveInfoUtil.CODE_KEY),
				SharedPreferencesHelper.getStringValue(MainActivity.this, SaveInfoUtil.PASSWORD_KEY));
	}

	/**
	 * 
	 */
	public void toTabActivity(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.bookButton:
		case R.id.saleButton:
		case R.id.settingButton:
		case R.id.introButton:
			Intent intent = new Intent();
			intent.putExtra(HotelStrings.TAB, view.getId());
			intent.setClass(this, TabActivity.class);
			startActivity(intent);
			break;
		case R.id.callButton:
			final Dialog callDialog = new Dialog(MainActivity.this, R.style.MyDialog);
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
					startActivity(new Intent().setAction(Intent.ACTION_CALL).setData(Uri.parse("tel:" + "00853-83911111")));
				}
			});
			callDialog.show();
			break;
		case R.id.languageButton:

			final Dialog languageDialog = new Dialog(MainActivity.this, R.style.MyDialog);
			languageDialog.setContentView(R.layout.dialog_language);

			int languaeChoiceID = SharedPreferencesHelper.getIntValue(MainActivity.this, CHOICELANGUAGESTRING, defaultLanguage);
			((RadioButton) languageDialog.findViewById(languaeChoiceID)).setChecked(true);
			languageDialog.findViewById(R.id.OK).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					RadioGroup languageGroup = (RadioGroup) languageDialog.findViewById(R.id.languageRdo);

					int languageID = languageGroup.getCheckedRadioButtonId();
					SharedPreferencesHelper.setIntValue(MainActivity.this, CHOICELANGUAGESTRING, languageID);
					languageDialog.dismiss();
					finish();
					startActivity(new Intent().setClass(MainActivity.this, MainActivity.class));
				}
			});
			languageDialog.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					languageDialog.dismiss();
				}
			});
			languageDialog.show();
			break;
		case R.id.mapBtn:

			Intent mapIntent = new Intent();
			mapIntent.setClass(this, FreeBusActivity.class);
			startActivity(mapIntent);
			break;
		case R.id.travelBtn:
			Intent travelIntent = new Intent();
			travelIntent.setClass(this, TravelActivity.class);
			startActivity(travelIntent);
			break;
		default:
			break;
		}

	}

	/**
 * 
 */
	private void seletLanguage(int id) {
		// TODO Auto-generated method stub
		Resources resources = getResources();
		Configuration configuration = resources.getConfiguration();
		DisplayMetrics dm = resources.getDisplayMetrics();
		switch (id) {
		case R.id.defaultRdo:
			configuration.locale = Locale.getDefault();
			break;
		case R.id.rcnRdo:
			configuration.locale = Locale.SIMPLIFIED_CHINESE;
			break;
		case R.id.rhkRdo:
			configuration.locale = Locale.CHINESE;
			break;
		case R.id.enRdo:
			configuration.locale = Locale.ENGLISH;
			break;

		default:
			configuration.locale = Locale.getDefault();
			break;
		}
		resources.updateConfiguration(configuration, dm);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			if (SharedPreferencesHelper.getIntValue(this, SaveInfoUtil.SAVELOGIN_KEY_STRING, 0) == 1) {
				SharedPreferencesHelper.setLongValue(this, SaveInfoUtil.CUSTID_KEY_STRING, (long) 0);
			}
		}
		return super.onKeyUp(keyCode, event);
	}
}
