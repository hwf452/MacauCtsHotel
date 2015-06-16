package com.halong.macauctshotel.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private SimpleDateFormat sdf;

	public DateUtil() {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
	}

	// 获取昨天的日期
	public String getDateOfYesterday() {
		Calendar c = Calendar.getInstance();
		long t = c.getTimeInMillis();
		long l = t - 24 * 3600 * 1000;
		Date d = new Date(l);
		String s = sdf.format(d);
		return s;
	}
}
