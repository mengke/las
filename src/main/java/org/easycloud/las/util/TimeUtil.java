package org.easycloud.las.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.easycloud.las.util.Assert.assertArgHasLength;
import static org.easycloud.las.util.Assert.assertArgNotNull;

/**
 * Time utility class that gets the time and computes intervals
 *
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class TimeUtil {

	private static final Logger LOGGER = Logger.getLogger(TimeUtil.class);

	public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	public static final DateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
	public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_DATE_PATTERN);

	/**
	 * Current system time.
	 * @return current time in msec.
	 */
	public static long now() {
		return System.currentTimeMillis();
	}

	/**
	 * Check if <code>before<code/> is before now by <code>interval<code/>
	 * @param before the time to be checked
	 * @param interval the intervals
	 * @param timeUnit the time unit of interval
	 * @return true if <code>before<code/> is before now by <code>interval<code/>
	 */
	public static boolean isBeforeNow(Date before, int interval, int timeUnit) {
		assertArgNotNull(before, "TimeUtil isBeforeNow before null");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(before);
		calendar.add(timeUnit, interval);
		return calendar.before(Calendar.getInstance());
	}

	/**
	 * Check if <code>before<code/> is before now by <code>interval<code/>
	 * @param before the time to be checked
	 * @param timePattern the time pattern used to parse <code>before<code/>, see {@link java.text.DateFormat}
	 * @param interval the intervals
	 * @param timeUnit the time unit of interval
	 * @return true if <code>before<code/> is before now by <code>interval<code/>
	 */
	public static boolean isBeforeNow(String before, String timePattern, int interval, int timeUnit) {
		assertArgHasLength(before, "TimeUtil isBeforeNow before null or empty");
		Date beforeDate = new Date();
		try {
			if (StringUtils.isNotBlank(timePattern)) {
				DateFormat df = new SimpleDateFormat(timePattern);
				beforeDate = df.parse(before);
			} else {
				DateFormat ddf = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
				beforeDate = ddf.parse(before);
			}
		} catch (ParseException e) {
			LOGGER.error("TimeUtil isBeforeNow an parse exception occurred, please check the args", e);
		}
		return isBeforeNow(beforeDate, interval, timeUnit);
	}

	/**
	 * Check if the <code>time</code> is after <code>before<code/>
	 * @param time the time to be checked
	 * @param before the time to be compared
	 * @return true if the <code>time</code> is after <code>before</code>
	 */
	public static boolean isAfter(Date time, Date before) {
		assertArgNotNull(time, "TimeUtil isAfter time null");
		return before == null || time.after(before);
	}

	public static boolean isAfter(String timeStr, String timePattern, Date before) {
		assertArgHasLength(timeStr, "TimeUtil isAfter time null or empty");
		Date time = null;
		try {
			if (StringUtils.isNotBlank(timePattern)) {
				DateFormat df = new SimpleDateFormat(timePattern);
				time = df.parse(timeStr);
			} else {
				DateFormat ddf = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
				time = ddf.parse(timeStr);
			}
		} catch (ParseException e) {
			LOGGER.error("TimeUtil isAfter an parse exception occurred, please check the args", e);
		}
		return isAfter(time, before);
	}

}
