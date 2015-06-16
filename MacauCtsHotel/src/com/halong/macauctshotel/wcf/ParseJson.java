package com.halong.macauctshotel.wcf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.halong.macauctshotel.R;
import com.halong.macauctshotel.common.SaveInfoUtil;
import com.halong.macauctshotel.entity.async.Collect;
import com.halong.macauctshotel.entity.async.Travel;
import com.halong.macauctshotel.entity.soap.CustSrvRecord;
import com.halong.macauctshotel.entity.soap.CustomerInfo;
import com.halong.macauctshotel.entity.soap.DcPublic;
import com.halong.macauctshotel.entity.soap.HotelDTO;
import com.halong.macauctshotel.entity.soap.RoomRateWS;
import com.halong.macauctshotel.entity.soap.rmtype.RateCode;
import com.halong.macauctshotel.entity.soap.rmtype.RmType;
import com.halong.macauctshotel.util.SharedPreferencesHelper;

public class ParseJson {

	private static ParseJson parseJson;

	private ParseJson() {

	}

	public synchronized static ParseJson getInstance() {
		if (parseJson == null) {
			parseJson = new ParseJson();
		}
		return parseJson;
	}

	public boolean updateVer(Context context, String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			String code = jsonObject.getString("code");
			if (code.contains("10001")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 预定完提示
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public String infotips(Context context, String content) {
		String tips = "";

		try {
			JSONObject object = new JSONObject(content);
			if (ApiLanguageUtil.EN.equals(ApiLanguageUtil.getStringLanguage())) {
				tips = object.getString("ename");
			} else {
				tips = object.getString("name");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tips.trim();
	}

	/**
	 * 解析收藏
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public List<Collect> memberMarkGoods(Context context, String content) {
		// TODO Auto-generated method stub
		List<Collect> list = new ArrayList<Collect>();
		try {
			JSONArray jsonArray = new JSONArray(content);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				Collect collect = new Collect();
				collect.setStar(Float.parseFloat(object.getString("star")));
				collect.setAddress(object.getString("address"));
				collect.setHotelid(object.getString("hotelId"));
				collect.setName(object.getString("name"));
				collect.setPhoto(object.getString("imgurl"));
				list.add(collect);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析主页面图片
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public List<String> focusImg(Context context, String content) {
		// TODO Auto-generated method stub
		try {
			JSONArray jsonArray = new JSONArray(content);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				list.add(AsyncAPIClient.PICTURE_URL_HALONG + object.getString("img"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Travel newsOne(Context context, String content) {
		Travel travel = null;
		try {
			travel = new Travel();
			JSONObject object = new JSONObject(content);
			travel.setId(object.getInt("id"));
			travel.setTitle(object.getString("title"));
			travel.setInfo(object.getString("info"));
			travel.setImg(object.getString("img"));
			return travel;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析资讯、活动列表
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public List<Travel> newsList(Context context, String content) {
		List<Travel> list = new ArrayList<Travel>();
		try {
			list = new ArrayList<Travel>();
			JSONArray jsonArray = new JSONArray(content);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				Travel travel = new Travel();
				travel.setId(object.getInt("id"));
				travel.setAdd_time(object.getString("add_time"));
				travel.setTitle(object.getString("title"));
				travel.setImg(object.getString("img"));
				list.add(travel);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<CustSrvRecord> orderUserComment(Context context, String content) {
		List<CustSrvRecord> list = new ArrayList<CustSrvRecord>();

		try {
			JSONArray jsonArray = new JSONArray(content);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				CustSrvRecord custSrvRecord = new CustSrvRecord();
				custSrvRecord.setOrdernum(object.getString("ordernum"));
				custSrvRecord.setHotelId(object.getString("hotelId"));
				custSrvRecord.setLevel(object.getInt("level"));
				custSrvRecord.setComment(object.getString("comment"));
				custSrvRecord.setRmTypeName(object.getString("rmTypeName"));
				custSrvRecord.setAddtime(object.getString("addtime"));
				list.add(custSrvRecord);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// public List<Travel> newsList(Context context, String content) {
	// List<Travel> list = new ArrayList<Travel>();
	// try {
	// list = new ArrayList<Travel>();
	// JSONArray jsonArray = new JSONArray(content);
	// for (int i = 0; i < jsonArray.length(); i++) {
	// JSONObject object = (JSONObject) jsonArray.get(i);
	// Travel travel = new Travel();
	// travel.setId(object.getInt("id"));
	// travel.setAdd_time(object.getString("add_time"));
	// travel.setTitle(object.getString("title"));
	// list.add(travel);
	// }
	// return list;
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * 解析种类
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public List<DcPublic> getDcPublic(Context context, String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				List<DcPublic> list = new ArrayList<DcPublic>();
				JSONArray jsonArray = jsonObject.getJSONArray("dcPublics");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					DcPublic dcPublic = new DcPublic();
					String Language = ApiLanguageUtil.getStringLanguage();
					dcPublic.setCode(object.getString("code"));
					dcPublic.setCname(object.getString("cname"));
					dcPublic.setEname(object.getString("ename"));
					if (Language.equalsIgnoreCase(ApiLanguageUtil.EN)) {
						dcPublic.setName(object.getString("ename"));
					} else if (Language.equalsIgnoreCase(ApiLanguageUtil.CN)) {
						dcPublic.setName(object.getString("cname"));
					}
					list.add(dcPublic);
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析酒店列表详情
	 * 
	 * @param context
	 * @param content
	 * @param hasZhongJiu
	 *            是否保存id=1的酒店（中酒）
	 * @return
	 */
	public List<HotelDTO> getHotelList(Context context, String content, boolean hasZhongJiu) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				List<HotelDTO> list = new ArrayList<HotelDTO>();
				JSONArray jsonArray = jsonObject.getJSONArray("hotelDTOs");
				for (int i = jsonArray.length() - 1; i >= 0; i--) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					HotelDTO hotelDTO = getHotelList(object);
					if (hasZhongJiu) {
						list.add(hotelDTO);
					} else {
						if (hotelDTO.getHotelId() == 1) {

						} else {
							list.add(hotelDTO);
						}
					}

				}
				return list;
			}
		} catch (JSONException e) {
			try {
				JSONObject jsonObject = new JSONObject(content);
				if (isSuccess(context, jsonObject)) {
					List<HotelDTO> list = new ArrayList<HotelDTO>();
					JSONObject hotels = jsonObject.getJSONObject("hotelDTOs");
					JSONObject object = hotels.getJSONObject("HotelDTO");
					HotelDTO hotelDTO = getHotelList(object);
					if (hasZhongJiu) {
						list.add(hotelDTO);
					} else {
						if (hotelDTO.getHotelId() == 1) {

						} else {
							list.add(hotelDTO);
						}
					}
					return list;
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HotelDTO getHotelList(JSONObject object) {
		try {

			HotelDTO hotelDTO = new HotelDTO();
			int languaeChoiceID = ApiLanguageUtil.getLanguage();
			if (ApiLanguageUtil.EN.equalsIgnoreCase(ApiLanguageUtil.getStringLanguage())) {
				hotelDTO.setName(object.getString("EName"));
				hotelDTO.setAddress(object.getString("EAddress"));
				hotelDTO.setIntro(object.getString("eIntro"));
				hotelDTO.setDescript(object.getString("eDescript"));
				hotelDTO.setResume(object.getString("resume_en"));
			} else {
				hotelDTO.setName(object.getString("CName"));
				hotelDTO.setAddress(object.getString("address"));
				hotelDTO.setIntro(object.getString("intro"));
				hotelDTO.setDescript(object.getString("descript"));
				hotelDTO.setResume(object.getString("resume"));
			}
			hotelDTO.setHotelId(Integer.parseInt(object.getString("hotelId")));
			hotelDTO.setPhoto1(object.getString("photo1"));
			hotelDTO.setPhoto2(object.getString("photo2"));
			hotelDTO.setPhoto3(object.getString("photo3"));
			hotelDTO.setCurrency(object.getString("currency"));
			hotelDTO.setStar(object.getString("star").trim());
			hotelDTO.setTel(object.getString("tel"));
			hotelDTO.setCity(object.getString("city").trim());
			hotelDTO.setCaddres(object.getString("CName"));

			JSONArray jsonArray = object.getJSONArray("hotelimg");
			String[] hotelimg = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				hotelimg[i] = jsonArray.getString(i);
			}
			hotelDTO.setPhotos(hotelimg);

			if ("".equals(hotelDTO.getStar())) {
				hotelDTO.setStar(0 + "");
			}
			return hotelDTO;
		} catch (JSONException e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 酒店可售房查询接口
	 * 
	 * @param category
	 */
	public List<RmType> roomRateQueryWSInterface(Context context, String content) {

		List<RmType> rmTypeList = new ArrayList<RmType>();
		HashMap<String, RmType> rmTypeMap = new HashMap<String, RmType>();

		try {
			JSONObject jsonObject = new JSONObject(content);
			if (!isSuccess(context, jsonObject)) {
				return null;
			}
			JSONArray roomRateRsList = jsonObject.getJSONArray("roomRateRsList");
			for (int i = 0; i < roomRateRsList.length(); i++) {
				parseRoomRateRsList(context, (JSONObject) roomRateRsList.get(i), rmTypeMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				JSONObject jsonObject = new JSONObject(content);
				JSONObject roomRateRs = jsonObject.getJSONObject("roomRateRsList").getJSONObject("RoomRateRs");
				parseRoomRateRsList(context, roomRateRs, rmTypeMap);
			} catch (JSONException e2) {
				// TODO: handle exception
				return null;
			}
		}
		for (String key : rmTypeMap.keySet()) {
			rmTypeList.add(rmTypeMap.get(key));
		}
		return rmTypeList;
	}

	private void parseRoomRateRsList(Context context, JSONObject RoomRateRs, HashMap<String, RmType> rmTypeMap) {
		try {
			JSONArray roomRateList = RoomRateRs.getJSONArray("roomRateList");
			for (int i = 0; i < roomRateList.length(); i++) {
				JSONObject object = roomRateList.getJSONObject(i);
				JSONObject rmTypeObject = object.getJSONObject("rmType");
				JSONObject rateCodeObject = object.getJSONObject("rateCode");

				RmType rmType = null;
				List<RateCode> rateCodeList = null;

				// 判断是否已经创建
				String rmTypeCode = rmTypeObject.getString("code");
				if (rmTypeMap.containsKey(rmTypeCode)) {
					rmType = rmTypeMap.get(rmTypeCode);
					rateCodeList = rmType.getRateCodeList();
				} else {
					rmType = new RmType();
					rateCodeList = new ArrayList<RateCode>();
				}

				// 新建房价代码
				RateCode rateCode = new RateCode();
				rateCode.setCurrency(rateCodeObject.getString("currency"));
				rateCode.setRateCat(rateCodeObject.getString("rateCat"));
				rateCode.setRateCode(rateCodeObject.getString("rateCode"));
				rateCode.setRemarks(rateCodeObject.getString("remarks"));
				rateCode.setStatus(rateCodeObject.getString("status"));
				if (ApiLanguageUtil.EN.equals(ApiLanguageUtil.getStringLanguage())) {
					rateCode.setName(rateCodeObject.getString("EName"));
					rateCode.setDescript(rateCodeObject.getString("edescript"));
				} else {
					rateCode.setName(rateCodeObject.getString("CName"));
					rateCode.setDescript(rateCodeObject.getString("descript"));
				}
				rateCode.setRmStatus(object.getString("rmStatus"));
				rateCode.setTotalRate(object.getString("totalRate"));
				rateCode.setDayRate(object.getString("dayRate"));
				rateCode.setRateDate(object.getString("rateDate"));

				String dayRate = object.getString("dayRate");
				try {
					JSONObject object2 = new JSONObject(dayRate);
					rateCode.setLowest(object2.getString("float"));
				} catch (Exception e) {
					try {
						JSONArray object2 = new JSONArray(dayRate);
						rateCode.setLowest(object2.get(0).toString());
					} catch (Exception e2) {
					}
				}

				if ("CNY".equals(rateCode.getCurrency())) {
					rateCode.setCurrencyName(context.getResources().getString(R.string.cny));
				} else if ("HKD".equals(rateCode.getCurrency())) {
					rateCode.setCurrencyName(context.getResources().getString(R.string.hkd));
				} else {
					rateCode.setCurrencyName(rateCode.getCurrency());
				}

				rateCodeList.add(rateCode);

				rmType.setCode(rmTypeObject.getString("code"));
				rmType.setHotelId(rmTypeObject.getString("hotelId"));
				rmType.setPhoto1(rmTypeObject.getString("photo1"));
				rmType.setPhoto2(rmTypeObject.getString("photo2"));
				rmType.setStatus(rmTypeObject.getString("status"));
				rmType.setStdpax(rmTypeObject.getString("stdPax"));
				rmType.setStdrate(rmTypeObject.getString("stdRate"));
				rmType.setRemarks(rmTypeObject.getString("remarks"));
				if (ApiLanguageUtil.EN.equals(ApiLanguageUtil.getStringLanguage())) {
					rmType.setName(rmTypeObject.getString("EName"));
					rmType.setDescript(rmTypeObject.getString("edescript"));
				} else {
					rmType.setDescript(rmTypeObject.getString("descript"));
					rmType.setName(rmTypeObject.getString("CName"));
				}

				try {
					if (ApiLanguageUtil.EN.equals(ApiLanguageUtil.getStringLanguage())) {
						rmType.setResume(rmTypeObject.getString("resume_en"));
					} else {
						rmType.setResume(rmTypeObject.getString("resume"));
					}
				} catch (JSONException e) {
					// TODO: handle exception
				}

				rmType.setRateCodeList(rateCodeList);

				if (rmType.getResume() == null) {
					rmType.setResume("");
				}

				rmTypeMap.put(rmTypeCode, rmType);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加订单
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public long addOrderInterface(Context context, String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				JSONObject object = jsonObject.getJSONObject("gres").getJSONObject("Gres");
				return object.getLong("accId");
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 取消订单
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public boolean cancelOrderInterface(Context context, String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 解析登录、保存信息
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public boolean loginGetInterface(Context context, String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				JSONObject object = jsonObject.getJSONObject("customerInfo");
				long custid = object.getLong("custid");
				String cardno = object.getString("cardno");
				String email = object.getString("email");

				// 保存账号custid、cardno
				SharedPreferencesHelper.setLongValue(context, SaveInfoUtil.CUSTID_KEY_STRING, custid);
				SharedPreferencesHelper.setStringValue(context, SaveInfoUtil.CARDNO_KEY_STRING, cardno);
				SharedPreferencesHelper.setStringValue(context, SaveInfoUtil.EMAIL_KEY_STRING, email);

				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 注册会员
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public boolean registerMemberInterface(Context context, String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 查看会员资料
	 * 
	 * @param customerID
	 *            custid
	 * @return
	 */
	public CustomerInfo customerGetInterface(Context context, String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				JSONObject object = jsonObject.getJSONObject("customerInfo");
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setCode(object.getString("code").toString());
				customerInfo.setCardno(object.getString("cardno").toString());
				customerInfo.setCustid(Integer.parseInt(object.getString("custid").toString()));
				customerInfo.setEmail(object.getString("email").toString());
				customerInfo.setEngname(object.getString("engname").toString());
				customerInfo.setGender(object.getString("gender").toString());
				customerInfo.setGstname(object.getString("gstname").toString());
				customerInfo.setNickname(object.getString("nickname").toString());
				customerInfo.setMobile(object.getString("mobile").toString());
				customerInfo.setPointaccid(object.getString("pointaccid").toString());
				customerInfo.setMemberstatus(object.getString("memberstatus"));
				return customerInfo;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 修改会员信息
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public boolean modifyMember(Context context, String content) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 修改密码
	 * 
	 * @param context
	 * @param content
	 * @return
	 */
	public boolean modifyPassword(Context context, String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean getOrderList(Context context, String content) {
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (isSuccess(context, jsonObject)) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否成功
	 * 如果不成功会根据后台返回进行提示
	 * @param context
	 * @param jsonObject
	 * @return
	 */
	private boolean isSuccess(Context context, JSONObject jsonObject) {
		try {
			int code = jsonObject.getInt("result");
			if (code == 0) {
				return true;
			} else {
				if (ApiLanguageUtil.getStringLanguage().equals(ApiLanguageUtil.EN)) {
					Toast.makeText(context, jsonObject.getString("errorMsgEn"), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, jsonObject.getString("errorMsgZh"), Toast.LENGTH_SHORT).show();
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
}
