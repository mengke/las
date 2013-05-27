package org.easycloud.las.core.util;

import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class TimeUtilTests {

	@Test
	public void testIsBeforeNow() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -1);
		calendar.add(Calendar.MINUTE, -20);
		Date date = calendar.getTime();
		org.testng.Assert.assertTrue(TimeUtil.isBeforeNow(date, 1, Calendar.HOUR));
		org.testng.Assert.assertFalse(TimeUtil.isBeforeNow(new Date(), 1, Calendar.MINUTE));
	}
}
