/**
 * 
 */
package com.halong.macauctshotel.book;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.halong.macauctshotel.LoginActivity;
import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.BackActivity;
import com.halong.macauctshotel.common.HotelStrings;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.entity.soap.RoomRateSearchDTO;
import com.halong.macauctshotel.entity.soap.rmtype.RateCode;
import com.halong.macauctshotel.entity.soap.rmtype.RmType;
import com.halong.macauctshotel.util.AppStackManager;
import com.halong.macauctshotel.util.SharedPreferencesHelper;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.ApiLanguageUtil;
import com.halong.macauctshotel.wcf.AsyncAPIClient;
import com.halong.macauctshotel.wcf.OnReturnListener;
import com.halong.macauctshotel.wcf.ParseJson;
import com.halong.macauctshotel.widget.HtmlImageGetter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 */
public class HotelDetailsActivity extends BackActivity implements OnClickListener {
	private ExpandableListView expandableListView;
	private ExpandableListAdapter adapter;
	private TextView groupTextView;
	private String[] gourp2To5Strings;
	private int[][] hotelGroupChild;
	private LinearLayout linearLayout;
	private ImageView imageView;
	private Button toMapButton;
	private String city, address;
	private TextView group4TextView1;
	private TextView group4TextView2;
	private TextView title;

	private RoomRateSearchDTO mRoomRateSearchDTO;
	private HotelDTO mHotelDTO;
	private List<RmType> mList;

	private ImageLoader mImageLoader;
	private DisplayImageOptions mOptions;

	private boolean isCollect = false;
	private CheckBox checkBox;
	private long custid = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AppStackManager.getMyAppStackManager().addActivity(this);
		
		gourp2To5Strings = getResources().getStringArray(R.array.hotelIntro);
		hotelGroupChild = HotelStrings.HOTELDETAILS;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_details);

		mImageLoader = ImageLoader.getInstance();
		this.mOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		mRoomRateSearchDTO = (RoomRateSearchDTO) getIntent().getSerializableExtra("mRoomRateSearchDTO");
		mHotelDTO = (HotelDTO) getIntent().getSerializableExtra("mHotelDTO");
		custid = SharedPreferencesHelper.getLongValue(this, SaveInfoUtil.CUSTID_KEY_STRING);

		mRoomRateSearchDTO.setHotelId(mHotelDTO.getHotelId() + "");
		title = (TextView) findViewById(R.id.title);
		title.setText(mHotelDTO.getName());

		toMapButton = (Button) findViewById(R.id.toBaiduMap);
		toMapButton.setOnClickListener(this);

		roomRateQueryWSInterface();
	}

	/**
	 * 
	 */
	private void initExpandableListView() {
		// TODO Auto-generated method stub

		expandableListView = (ExpandableListView) findViewById(R.id.exlist);
		adapter = new ExpandableListAdapter() {

			@Override
			public void unregisterDataSetObserver(DataSetObserver observer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void registerDataSetObserver(DataSetObserver observer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGroupExpanded(int groupPosition) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGroupCollapsed(int groupPosition) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				convertView = null;
				switch (groupPosition) {
				case 0:
					convertView = getLayoutInflater().inflate(R.layout.item_expandable_group1, null);
					ImageView imageView1 = (ImageView) convertView.findViewById(R.id.imageView);
					TextView tvView1 = (TextView) convertView.findViewById(R.id.textView1);
					TextView tvView2 = (TextView) convertView.findViewById(R.id.textView2);
					TextView tvView3 = (TextView) convertView.findViewById(R.id.textView3);
					checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
					checkBox.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (checkLogin(true)) {
								if (!checkBox.isChecked()) {
									postDelCollect();
								} else {
									postAddCollect();
								}
							}
						}
					});

					tvView1.setText(mHotelDTO.getName());
					tvView2.setText(mHotelDTO.getAddress());
					tvView3.setText(mHotelDTO.getTel());

					mImageLoader.displayImage(AsyncAPIClient.PICTURE_URL + mHotelDTO.getPhoto1(), imageView1, mOptions);

					city = mHotelDTO.getCity();
					address = mHotelDTO.getCaddres();

					break;

				default:

					convertView = getLayoutInflater().inflate(R.layout.item_expandable_group2_to_group5, null);
					groupTextView = (TextView) convertView.findViewById(R.id.textView);
					groupTextView.setText(gourp2To5Strings[groupPosition - 1]);
					if (groupPosition == (hotelGroupChild.length - 1)) {
						ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
						imageView.setImageResource(android.R.color.transparent);
					}
					break;
				}
				return convertView;
			}

			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return hotelGroupChild.length;
			}

			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getCombinedGroupId(long groupId) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getCombinedChildId(long groupId, long childId) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				int count = 0;
				switch (groupPosition) {

				case 1:
				case 2:

					count = 1;
					break;
				case 3:
					count = 1;
					break;
				case 4:
					if (mList == null) {
						count = 0;
					} else {
						count = mList.size();
					}
					break;
				default:
					break;
				}
				return count;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				convertView = null;
				convertView = getLayoutInflater().inflate(hotelGroupChild[groupPosition][2], null);
				switch (groupPosition) {
				case 1:
					initGroup1Child(convertView);
					break;
				case 2:
					initGroup2Child(convertView);
					break;
				case 3:
					initGroup3Child(convertView, childPosition);
					break;
				case 4:
					initGroup4Child(convertView, childPosition);
					break;
				default:
					break;
				}
				return convertView;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean areAllItemsEnabled() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		expandableListView.setAdapter(adapter);
		expandableListView.expandGroup(hotelGroupChild.length - 1);
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				// TODO Auto-generated method stub
				if (groupPosition == (hotelGroupChild.length - 1)) {
					return true;
				} else {
					return false;
				}
			}
		});

		// 检查是否登录、设置默认收藏值
		if (checkLogin(false)) {
			postCollect();
		}
	}

	/**
	 * 检查是否登录
	 * 
	 * @param isLogin
	 *            是否需要登录功能
	 * @return
	 */
	private boolean checkLogin(boolean isLogin) {
		if (!SaveInfoUtil.isLogin(HotelDetailsActivity.this)) {
			if (isLogin) {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivityForResult(intent, LoginActivity.LOGINSUCCESS);
			}
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是否收藏
	 */
	private void postCollect() {
		AsyncAPIClient client = new AsyncAPIClient(this);
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(content);
					String code = jsonObject.getString("code");
					if (checkBox == null) {
						return;
					}
					if ("mp10301".equals(code)) {
						checkBox.setChecked(true);
					} else {
						checkBox.setChecked(false);
						// postAddCollect();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(String content) {
				// TODO Auto-generated method stub
				super.onFailure(content);
				checkBox.setChecked(false);
			}
		});
		client.memberMarkGoodsInput(custid + "", mHotelDTO.getHotelId() + "");
	}

	/**
	 * 添加收藏
	 */
	private void postAddCollect() {
		AsyncAPIClient client = new AsyncAPIClient(this);
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(content);

					String code = jsonObject.getString("code");
					if ("mp10001".equals(code)) {
						checkBox.setChecked(true);
					} else if ("mp10301".equals(code)) {
						checkBox.setChecked(true);
					} else if ("mp10101".equals(code)) {
						checkBox.setChecked(false);
					} else if ("mp10000".equals(code)) {
						checkBox.setChecked(false);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(String content) {
				// TODO Auto-generated method stub
				checkBox.setChecked(false);
			}
		});
		client.memberMarkGoodsInput1(custid + "", mHotelDTO);
	}

	/**
	 * 删除收藏
	 */
	private void postDelCollect() {
		AsyncAPIClient client = new AsyncAPIClient(this);
		client.setOnReturnListener(new OnReturnListener() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(content);

					String code = jsonObject.getString("code");
					if ("mh10001".equals(code)) {
						checkBox.setChecked(false);
					} else if ("mh10202".equals(code)) {
						checkBox.setChecked(false);
					} else if ("mh10000".equals(code)) {
						checkBox.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(String content) {
				// TODO Auto-generated method stub
				checkBox.setChecked(true);
			}
		});
		client.memberMarkGoodsHover(custid + "", mHotelDTO.getHotelId() + "");
	}

	private View initGroup1Child(View view) {
		TextView textView = (TextView) view.findViewById(R.id.textView);

		//去掉HTML的图片
		String regEx_html = "<img[^>]+/>";
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(mHotelDTO.getDescript());
		String htmlStr = m_html.replaceAll("");
		textView.setText(Html.fromHtml(htmlStr));
		return view;
	}

	private void initGroup2Child(View view) {
		linearLayout = (LinearLayout) view.findViewById(R.id.layout);
		final String[] url = mHotelDTO.getPhotos();
		for (int i = 0; i < url.length; i++) {
			if ("anyType{}".equals(url[i]) || "".equals(url[i]) || url[i] == null) {
				url[i] = "";
				break;
			} else {
				if (!url[i].contains("http://")) {
					url[i] = AsyncAPIClient.PICTURE_HOTEL_URL_HALONG + url[i];
				}
			}
			imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_expandable_group3_child_image, null);
			mImageLoader.displayImage(url[i], imageView, mOptions);
			imageview(imageView, url, i);

			linearLayout.addView(imageView, i);
		}
	}

	private void imageview(ImageView imageView, final String[] url, final int position) {
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HotelDetailsActivity.this, PhotoViewActivity.class);
				intent.putExtra("photo", url);
				intent.putExtra("i", position);
				startActivity(intent);
			}
		});
	}

	private void initGroup3Child(View view, int child) {
		switch (child) {
		case 0:
			group4TextView1 = (TextView) view.findViewById(R.id.textView1);
			group4TextView1.setText(mHotelDTO.getResume());
			break;

		default:
			break;
		}
	}

	private void initGroup4Child(View view, final int child) {
		ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
		TextView textView = (TextView) view.findViewById(R.id.textView);
		final TextView textView1 = (TextView) view.findViewById(R.id.textView1);
		TextView textView2 = (TextView) view.findViewById(R.id.textView2);
		TextView textView3 = (TextView) view.findViewById(R.id.textView3);
		Button button = (Button) view.findViewById(R.id.button);

		final RmType rmType = mList.get(child);
		final RateCode rateCode = rmType.getRateCodeList().get(0);
		textView.setText(rmType.getName());

		double lowest = Double.parseDouble(rateCode.getLowest());
		for (int i = 0; i < rmType.getRateCodeList().size(); i++) {
			if (lowest > Double.parseDouble(rmType.getRateCodeList().get(i).getLowest())) {
				lowest = Double.parseDouble(rmType.getRateCodeList().get(i).getLowest());
			}
		}
		String price = getResources().getString(R.string.lowest) + lowest + rateCode.getCurrencyName();
		textView1.setText(price);

		textView2.setText("");

		textView3.setText(rmType.getResume());

		if (!"anyType{}".equals(rmType.getPhoto1())) {
			mImageLoader.displayImage(AsyncAPIClient.PICTURE_URL + rmType.getPhoto1(), imageView);
		}

		if ("1".equals(rateCode.getRmStatus())) {
			button.setText(getResources().getString(R.string.roomratews_full));
			button.setClickable(false);
		} else {
			button.setText(getResources().getString(R.string.roomratews_null));
			button.setClickable(true);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(HotelDetailsActivity.this, HotelBookActivity.class);
					intent.putExtra("name", mHotelDTO.getName());
					intent.putExtra("photo", mHotelDTO.getPhoto1());
					Bundle bundle = new Bundle();
					Gres gres = new Gres();
					gres.setArrDate(mRoomRateSearchDTO.getApprDate());
					gres.setDepDate(mRoomRateSearchDTO.getDepDate());
					gres.setHotelId(mHotelDTO.getHotelId());
					gres.setMsgType(",10,20,");
					gres.setRmQty(Integer.parseInt(mRoomRateSearchDTO.getRoomNum()));
					gres.setRmRate(rateCode.getTotalRate());
					gres.setRmType(rmType.getCode());
					gres.setRmTypeName(rmType.getName());
					gres.setHotelAddres(mHotelDTO.getAddress());
					gres.setHotelName(mHotelDTO.getName());
					gres.setCustId(SharedPreferencesHelper.getLongValue(HotelDetailsActivity.this, SaveInfoUtil.CUSTID_KEY_STRING));
					gres.setAdults(2);
					bundle.putSerializable("mGres", gres);
					bundle.putSerializable("rmType", mList.get(child));
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}

	}

	private void roomRateQueryWSInterface() {
		AsyncAPIClient asyncAPIClient = new AsyncAPIClient(this);
		asyncAPIClient.setOnReturnListener(new OnReturnListener() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				mList = ParseJson.getInstance().roomRateQueryWSInterface(HotelDetailsActivity.this, content);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				initExpandableListView();
			}
		});
		asyncAPIClient.roomRateQueryWSInterface(mRoomRateSearchDTO, ApiLanguageUtil.getStringLanguage());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.toBaiduMap:
			if (city != null && address != null) {
				Intent intent = new Intent(this, PoiSearchDemo.class);
				intent.putExtra("city", city);
				intent.putExtra("address", address);
				startActivity(intent);
			}
			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == LoginActivity.LOGINSUCCESS) {
			postCollect();
		}
	}
}
