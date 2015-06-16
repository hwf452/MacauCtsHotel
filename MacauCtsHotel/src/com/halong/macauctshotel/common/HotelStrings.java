/**
 * 
 */
package com.halong.macauctshotel.common;


import com.halong.macauctshotel.R;

/**
 * 
 */
public class HotelStrings {
	public static final String BOOK = "酒店预订";
	public static final String SALE = "特惠活动";
	public static final String INTRO = "酒店简介";
	public static final String LOGIN = "用户登录";
	public static final String SETTING = "个人中心";
	public static final String TAB = "tab";
	public static final int[][] HOTELDETAILS = {
			{ 0, R.layout.item_expandable_group1, },
			{ R.string.hotelIntro, R.layout.item_expandable_group2_to_group5,
					R.layout.item_expandable_group2_child },
			{ R.string.hotelImages, R.layout.item_expandable_group2_to_group5,
					R.layout.item_expandable_group3_child },
			{ R.string.hotelServer, R.layout.item_expandable_group2_to_group5,
					R.layout.item_expandable_group4_child },
			{ R.string.hotelRoomType,
					R.layout.item_expandable_group2_to_group5,
					R.layout.item_expandable_group5_child } };
}
