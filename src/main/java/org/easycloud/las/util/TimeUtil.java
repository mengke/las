package org.easycloud.las.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.NumberFormat;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeUtil.class);

	public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	public static final DateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
	public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_DATE_PATTERN);

	private static long[] TIME_FACTOR = { 60 * 60 * 1000, 60 * 1000, 1000 };

	/**
	 * Current system time.
	 * @return current time in msec.
	 */
	public static long now() {
		return System.currentTimeMillis();
	}

	/**
	 * Check if <code>before<code/> is before now by <code>interval<code/>
	 * @param time the time to be checked
	 * @param interval the intervals
	 * @param timeUnit the time unit of interval
	 * @return true if <code>before<code/> is before now by <code>interval<code/>
	 */
	public static boolean isBeforeNow(Date time, int interval, int timeUnit) {
		assertArgNotNull(time, "TimeUtil isBeforeNow before null");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(timeUnit, interval);
		return calendar.before(Calendar.getInstance());
	}

	/**
	 * Check if <code>before<code/> is before now by <code>interval<code/>
	 * @param time the time to be checked
	 * @param timePattern the time pattern used to parse <code>before<code/>, see {@link java.text.DateFormat}
	 * @param interval the intervals
	 * @param timeUnit the time unit of interval
	 * @return true if <code>before<code/> is before now by <code>interval<code/>
	 */
	public static boolean isBeforeNow(String time, String timePattern, int interval, int timeUnit) {
		assertArgHasLength(time, "TimeUtil isBeforeNow before null or empty");
		Date beforeDate = new Date();
		try {
			if (StringUtils.isNotBlank(timePattern)) {
				DateFormat df = new SimpleDateFormat(timePattern);
				beforeDate = df.parse(time);
			} else {
				DateFormat ddf = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
				beforeDate = ddf.parse(time);
			}
		} catch (ParseException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("TimeUtil isBeforeNow an parse exception occurred, please check the args", e);
			}
		}
		return isBeforeNow(beforeDate, interval, timeUnit);
	}


	public static boolean isBefore(Date time1, Date time2) {
		assertArgNotNull(time1, "TimeUtil: isBefore time1 null");
		assertArgNotNull(time2, "TimeUtil: isBefore time2 null");
		return time1.before(time2) || time1.equals(time2);
	}

	public static boolean isBefore(String time1, String timePattern, Date time2) {
		assertArgHasLength(time1, "TimeUtil isAfter isBefore: null or empty");
		Date time = null;
		try {
			if (StringUtils.isNotBlank(timePattern)) {
				DateFormat df = new SimpleDateFormat(timePattern);
				time = df.parse(time1);
			} else {
				DateFormat ddf = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
				time = ddf.parse(time1);
			}
		} catch (ParseException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("TimeUtil isAfter an parse exception occurred, please check the args", e);
			}
		}
		return isBefore(time, time2);
	}

	/**
	 * Check if the <code>time</code> is after <code>before<code/>
	 * @param time1 the time to be checked
	 * @param time2 the time to be compared
	 * @return true if the <code>time</code> is after <code>before</code>
	 */
	public static boolean isAfter(Date time1, Date time2) {
		assertArgNotNull(time1, "TimeUtil isAfter time1 null");
		assertArgNotNull(time2, "TimeUtil isAfter time2 null");
		return time1.after(time2);
	}

	public static boolean isAfter(String time1, String timePattern, Date time2) {
		assertArgHasLength(time1, "TimeUtil isAfter time1 null or empty");
		Date time = null;
		try {
			if (StringUtils.isNotBlank(timePattern)) {
				DateFormat df = new SimpleDateFormat(timePattern);
				time = df.parse(time1);
			} else {
				DateFormat ddf = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
				time = ddf.parse(time1);
			}
		} catch (ParseException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("TimeUtil isAfter an parse exception occurred, please check the args", e);
			}
		}
		return isAfter(time, time2);
	}

	/**
	 * Calculate the elapsed time between two times specified in milliseconds.
	 * @param start The start of the time period
	 * @param end The end of the time period
	 * @return a string of the form "XhYmZs" when the elapsed time is X hours, Y minutes and Z seconds or null if start > end.
	 */
	public static String elapsedTime(long start, long end){
		if (start > end) {
			return null;
		}

		long[] elapsedTime = new long[TIME_FACTOR.length];

		for (int i = 0; i < TIME_FACTOR.length; i++) {
			elapsedTime[i] = start > end ? -1 : (end - start) / TIME_FACTOR[i];
			start += TIME_FACTOR[i] * elapsedTime[i];
		}

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < elapsedTime.length; i++) {
			if (i > 0) {
				buf.append(":");
			}
			buf.append(nf.format(elapsedTime[i]));
		}
		return buf.toString();
	}

}
