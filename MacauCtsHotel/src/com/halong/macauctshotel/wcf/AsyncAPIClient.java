package com.halong.macauctshotel.wcf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.entity.soap.Gres;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.entity.soap.OrderDTO;
import com.halong.macauctshotel.entity.soap.RegisterDTO;
import com.halong.macauctshotel.entity.soap.RoomRateSearchDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncAPIClient {

	private Context mContext;
	private static AsyncHttpClient client;

	private static final String DOMAIN = "http://202.175.102.106";

	private final String URL = DOMAIN + "/zhongjiu/api/";
	// 夏浪接口
	private final String METHOD_NEWSONE = "newsOne.api.php";
	private final String METHOD_NEWSLIST = "newsList.api.php";
	private final String METHOD_UPDATEVER = "updateVer.api.php";
	private final String METHOD_ORDERCOMMENTLIST = "orderCommentList.api.php";
	private final String METHOD_ORDERCOMMENTONE = "orderCommentOne.api.php";
	private final String METHOD_ORDERCOMMENTINPUT = "orderCommentInput.api.php";
	private final String METHOD_ORDERUSERCOMMENT = "orderUserComment.api.php";
	private final String METHOD_MEMBERMARKGOODS = "memberMarkGoods.api.php";
	private final String METHOD_MEMBERMARKGOODSINPUT = "memberMarkGoodsInput.api.php";
	private final String METHOD_MEMBERMARKGOODSHOVER = "memberMarkGoodsHover.api.php";
	private final String METHOD_FOCUSIMG = "focusImg.api.php";
	private final String METHOD_ADDFEEDBACK = "addFeedback.api.php";
	private final String METHOD_INFOTIPS = "infoTips.api.php";
	// 千里马接口
	private final String METHOD_GETDCPUBLIC = "getDcPublic.php";
	private final String METHOD_GETHOTELLIST = "getHotelList.php";
	private final String METHOD_ROOMRATEQUERYWSINTERFACE = "roomRateQueryWSInterface.php";
	private final String METHOD_ADDORDERINTERFACE = "addOrderInterface.php";
	private final String METHOD_LOGINGETINTERFACE = "loginGetInterface.php";
	private final String METHOD_CANCELORDERINTERFACE = "cancelOrderInterface.php";
	private final String METHOD_REGISTERMEMBERINTERFACE = "registerMemberInterface.php";
	private final String METHOD_CUSTOMERGETINTERFACE = "customerGetInterface.php";
	private final String METHOD_MODIFYMEMBERINTERFACE = "modifyMember.php";
	private final String METHOD_MODIFYPASSWORD = "modifyPassword1.php";
	private final String METHOD_GETORDERLIST = "getOrderList.php";
	// 图片下载
	public static final String PICTURE_URL = DOMAIN + ":8080/ipegasus/UploadFile/"; // 千里马json返回图片路径
	public static final String PICTURE_URL_HTML = ":8080"; // 千里马html图片路径
	public static final String PICTURE_URL_HALONG_HTML = DOMAIN; // 夏浪Html图片路径
	public static final String PICTURE_URL_HALONG = DOMAIN + "/zhongjiu/data/focus/"; // main广告图片
	public static final String PICTURE_NEWS_URL_HALONG = DOMAIN + "/zhongjiu/data/news/"; // 资讯
	public static final String PICTURE_HOTEL_URL_HALONG = DOMAIN + "/zhongjiu/statics/flash/upload/"; // 酒店
	// 版本更新
	public static final String UPDATEVER_URL_HALONG = DOMAIN + "/zhongjiu/data/version/"; // 版本更新

	public AsyncAPIClient(Context context) {
		this.mContext = context;
		if (client == null) {
			client = new AsyncHttpClient();
		}
	}

	/**
	 * 更新版本
	 */
	public void updateVer() {
		RequestParams params = new RequestParams();
		params.put("system", 1 + "");
		getReturnWholeContent(URL + METHOD_UPDATEVER, params);
	}

	/**
	 * 资讯列表 id=0全部，id=1特惠活动，id=2旅游资讯
	 */
	public void newsList(String id) {
		RequestParams params = new RequestParams();
		params.put("id", id + "");

		get(URL + METHOD_NEWSLIST, params);
	}

	/**
	 * 资讯详情
	 * 
	 * @param id
	 */
	public void newsOne(String id) {
		RequestParams params = new RequestParams();
		params.put("id", id);

		get(URL + METHOD_NEWSONE, params);
	}

	/**
	 * 判断是否评论列表
	 * 
	 * @param userid
	 * @param ordernums
	 */
	public void orderCommentList(String userid, ArrayList<String> ordernums) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("ordernums", ordernums);

		get(URL + METHOD_ORDERCOMMENTLIST, params);
	}

	/**
	 * 获取评论信息
	 * 
	 * @param ordernum
	 * @param userid
	 */
	public void orderCommentOne(String ordernum, String userid) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("ordernum", ordernum);

		get(URL + METHOD_ORDERCOMMENTONE, params);
	}

	/**
	 * 获取评论信息
	 * 
	 * @param ordernum
	 * @param userid
	 */
	public void orderUserComment(String userid) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);

		get(URL + METHOD_ORDERUSERCOMMENT, params);
	}

	/**
	 * 新增评论信息
	 * 
	 * @param userid
	 * @param ordernum
	 * @param comment
	 * @param level
	 * @param hotelId
	 * @param rmTypeName
	 */
	public void orderCommentInput(String userid, String ordernum, String comment, String level, String hotelId, String

	rmTypeName) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("ordernum", ordernum);
		params.put("comment", comment);
		params.put("level", level);
		params.put("hotelId", hotelId);
		params.put("rmTypeName", rmTypeName);

		get(URL + METHOD_ORDERCOMMENTINPUT, params);
	}

	/**
	 * 收藏列表
	 * 
	 * @param userid
	 */
	public void memberMarkGoods(String userid) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);

		get(URL + METHOD_MEMBERMARKGOODS, params);
	}

	/**
	 * 是否收藏
	 * 
	 * @param userid
	 * @param goodsid
	 */
	public void memberMarkGoodsInput(String userid, String hotelId) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("hotelId", hotelId);
		params.put("check", "1");

		getReturnWholeContent(URL + METHOD_MEMBERMARKGOODSINPUT, params);
	}

	/**
	 * 新增收藏
	 * 
	 * @param userid
	 * @param goodsid
	 */
	public void memberMarkGoodsInput1(String userid, HotelDTO hotelDTO) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("name", hotelDTO.getName() + "");
		params.put("hotelId", hotelDTO.getHotelId() + "");
		params.put("address", hotelDTO.getAddress() + "");
		params.put("star", hotelDTO.getStar() + "");
		params.put("imgurl", hotelDTO.getPhoto1() + "");

		getReturnWholeContent(URL + METHOD_MEMBERMARKGOODSINPUT, params);
	}

	/**
	 * 删除收藏
	 * 
	 * @param userid
	 * @param goodsid
	 */
	public void memberMarkGoodsHover(String userid, String goodsid) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("hotelId", goodsid);

		getReturnWholeContent(URL + METHOD_MEMBERMARKGOODSHOVER, params);
	}

	/**
	 * 预定成功后的提示
	 */
	public void infotips() {
		get(URL + METHOD_INFOTIPS, null);
	}

	public void focusImg() {
		get(URL + METHOD_FOCUSIMG, null);
	}

	public void addFeedback(String userid, String name, String mobile, String email, String content) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("name", name);
		params.put("mobile", mobile);
		params.put("email", email);
		params.put("content", content);

		get(URL + METHOD_ADDFEEDBACK, params);
	}

	/**
	 * 查询类别
	 * 
	 * @param category
	 */
	public void getDcPublic(String category) {
		RequestParams params = new RequestParams();
		params.put("category", category);

		get1(URL + METHOD_GETDCPUBLIC, params);
	}

	/**
	 * 查询某城市的所有酒店，city=""查询全部
	 * 
	 * @param city
	 *            城市
	 * @return
	 */
	public void getHotelList(String city) {
		RequestParams params = new RequestParams();
		params.put("city", city);

		get1(URL + METHOD_GETHOTELLIST, params);
	}

	/**
	 * 查询酒店房型
	 * 
	 * @param roomRateSearchDTO
	 * @param locale
	 */
	public void roomRateQueryWSInterface(RoomRateSearchDTO roomRateSearchDTO, String locale) {
		RequestParams params = new RequestParams();
		params.put("locale", locale);
		params.put("hotelId", roomRateSearchDTO.getHotelId());
		params.put("personNum", roomRateSearchDTO.getPersonNum());
		params.put("roomNum", roomRateSearchDTO.getRoomNum());
		params.put("apprDate", roomRateSearchDTO.getApprDate());
		params.put("depDate", roomRateSearchDTO.getDepDate());

		get1(URL + METHOD_ROOMRATEQUERYWSINTERFACE, params);
	}

	/**
	 * 增加订单的接口
	 * 
	 * @param gres
	 */
	public void addOrderInterface(Gres gres) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		gres.setResDate(formatter.format(new java.util.Date()));

		RequestParams params = new RequestParams();
		params.put("adults", gres.getProperty(0).toString());
		params.put("arrDate", gres.getProperty(1).toString());
		params.put("booker", gres.getProperty(2).toString());
		params.put("bookTel", gres.getProperty(3).toString());
		params.put("cardNo", gres.getProperty(4).toString());
		params.put("custId", gres.getProperty(5).toString());
		params.put("depDate", gres.getProperty(6).toString());
		params.put("gstName", gres.getProperty(7).toString());
		params.put("hotelId", gres.getProperty(8).toString());
		params.put("msgType", gres.getProperty(9).toString());
		params.put("nights", gres.getProperty(10).toString());
		params.put("rateCode", gres.getProperty(11).toString());
		params.put("rmQty", gres.getProperty(12).toString());
		params.put("rmRate", gres.getProperty(13).toString());
		params.put("rmType", gres.getProperty(14).toString());
		params.put("remarks", gres.getProperty(15).toString());
		params.put("resDate", gres.getProperty(16).toString());
		params.put("creditCard", gres.getProperty(17).toString());
		params.put("creditDate", gres.getProperty(18).toString());
		params.put("creditType", gres.getProperty(19).toString());
		params.put("cvvCode", gres.getProperty(20).toString());
		params.put("resType", gres.getProperty(21).toString());
		params.put("bookerEmail", gres.getProperty(23).toString());
		params.put("payMthd", gres.getProperty(24).toString());
		params.put("resClerk", "APP");

		get1(URL + METHOD_ADDORDERINTERFACE, params);
	}

	/**
	 * 取消订单
	 * 
	 * @param resId
	 *            订单id
	 * @return
	 */
	public void cancelOrderInterface(long resId) {
		RequestParams params = new RequestParams();
		params.put("resId", resId + "");

		get1(URL + METHOD_CANCELORDERINTERFACE, params);
	}

	/**
	 * 登录
	 * 
	 * @param code
	 *            账号
	 * @param pwd
	 *            密码
	 */
	public void loginGetInterface(String code, String pwd) {
		RequestParams params = new RequestParams();
		params.put("code", code);
		params.put("pwd", pwd);

		get1(URL + METHOD_LOGINGETINTERFACE, params);
	}

	/**
	 * 注册
	 * 
	 * @param registerDTO
	 * @return
	 */
	public void registerMemberInterface(RegisterDTO registerDTO) {
		RequestParams params = new RequestParams();
		params.put("code", registerDTO.getProperty(0).toString());
		params.put("password", registerDTO.getProperty(1).toString());
		params.put("nickname", registerDTO.getProperty(2).toString());
		params.put("email", registerDTO.getProperty(3).toString());
		params.put("mobile", registerDTO.getProperty(4).toString());

		get1(URL + METHOD_REGISTERMEMBERINTERFACE, params);
	}

	/**
	 * 查看会员信息
	 * 
	 * @param customerID
	 */
	public void customerGetInterface(long customerID) {
		RequestParams params = new RequestParams();
		params.put("customerID", customerID + "");

		get1(URL + METHOD_CUSTOMERGETINTERFACE, params);
	}

	/**
	 * 会员资料修改接口
	 * 
	 * @param RegisterDTO
	 */
	public void modifyMember(RegisterDTO registerDTO) {
		RequestParams params = new RequestParams();
		params.put("code", registerDTO.getProperty(0).toString());
		params.put("nickname", registerDTO.getProperty(2).toString());
		params.put("Email", registerDTO.getProperty(3).toString());
		params.put("mobile", registerDTO.getProperty(4).toString());
		params.put("gender", registerDTO.getProperty(5).toString());
		params.put("custid", registerDTO.getProperty(6).toString());
		params.put("memberstatus", registerDTO.getProperty(7).toString());

		get1(URL + METHOD_MODIFYMEMBERINTERFACE, params);
	}

	/**
	 * 修改密码
	 * 
	 * @param candno
	 *            卡号 newPass 新密码
	 */
	public void modifyPassword(String candno, String oldPass, String newPass) {
		RequestParams params = new RequestParams();
		params.put("candno", candno);
		params.put("oldPass", oldPass);
		params.put("newPass", newPass);

		get1(URL + METHOD_MODIFYPASSWORD, params);
	}

	/**
	 * 获取订单列表接口
	 * 
	 * @param orderDto
	 */
	public void getOrderList(OrderDTO orderDTO) {
		RequestParams params = new RequestParams();
		params.put("custId", orderDTO.getCustId() + "");

		get1(URL + METHOD_GETORDERLIST, params);
	}

	/**
	 * 含千里马结果返回
	 * 
	 * @param url
	 *            地址
	 * @param params
	 *            参数
	 */
	private void get1(String url, final RequestParams params) {
		// TODO Auto-generated method stub
		if (params != null) {
			
		}
		client.get(mContext, url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				if (onReturnListener != null) {
					try {
						JSONObject jsonObject = new JSONObject(content);
						String code = jsonObject.getString("code");
						if (code.contains("10001")) {
							onReturnListener.onSuccess(jsonObject.getJSONObject("result").toString());
						} else {
							onReturnListener.onFailure(content);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						onReturnListener.onFailure(mContext.getResources().getString

						(R.string.network_toast1));
					}
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
				Toast.makeText(mContext, mContext.getResources().getString(R.string.network_toast),

				Toast.LENGTH_SHORT).show();
				if (onReturnListener != null) {
					onReturnListener.onFailure(mContext.getResources().getString(R.string.network_toast));
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (onReturnListener != null) {
					onReturnListener.onFinish();
				}
			}
		});
	}

	/**
	 * 直接调用夏浪的接口
	 * 
	 * @param url
	 * @param params
	 */
	private void get(String url, RequestParams params) {
		// TODO Auto-generated method stub
		if (params != null) {
			params.put("language", ApiLanguageUtil.getStringLanguage());
		}
		client.get(mContext, url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				try {
					if (onReturnListener != null) {
						JSONObject jsonObject = new JSONObject(content);
						if (jsonObject.getString("code").contains("10001")) {
							onReturnListener.onSuccess(jsonObject.getString("result").trim());
						} else {
							String msg = "";
							if (ApiLanguageUtil.EN.equals(ApiLanguageUtil.getStringLanguage())) {
								msg = jsonObject.getString("emsg");
							} else {
								msg = jsonObject.getString("msg");
							}
							if ("".equals(msg)) {
								Toast.makeText(mContext, mContext.getResources().getString

								(R.string.network_toast1), Toast.LENGTH_SHORT).show();
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
				if (onReturnListener != null) {
					onReturnListener.onFailure(content);
				}
				Toast.makeText(mContext, mContext.getResources().getString(R.string.network_toast),

				Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (onReturnListener != null) {
					onReturnListener.onFinish();
				}
			}
		});
	}

	/**
	 * 返回全部数据
	 * 
	 * @param url
	 * @param params
	 */
	private void getReturnWholeContent(String url, RequestParams params) {
		// TODO Auto-generated method stub
		if (params != null) {
			params.put("language", ApiLanguageUtil.getStringLanguage());
		}
		client.get(mContext, url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				if (onReturnListener != null) {
					onReturnListener.onSuccess(content);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
				if (onReturnListener != null) {
					onReturnListener.onFailure(content);
				}
				Toast.makeText(mContext, mContext.getResources().getString(R.string.network_toast),

				Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (onReturnListener != null) {
					onReturnListener.onFinish();
				}
			}
		});
	}

	OnReturnListener onReturnListener = null;

	public void setOnReturnListener(OnReturnListener e) {
		if (e != null) {
			onReturnListener = e;
		}
	}
}
