/*
 * Copyright 2013 Ke Meng (mengke@icloud.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
