/*
 * Copyright 2013 Ke Meng (mengke@icloud.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.easycloud.las.core.util;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-3-1
 */
public class StringUtil {

	final public static String[] EMPTY_STRING_ARRAY = {};

	/**
	 * Returns a collection of strings.
	 *
	 * @param str   <code>split</code> separated string values
	 * @param split the string value that separating <code>str</code>
	 * @return an <code>ArrayList</code> of string values
	 */
	public static Collection<String> getStringCollection(String str, String split) {
		if (split == null || split.isEmpty()) {
			split = ",";
		}
		List<String> values = new ArrayList<String>();
		if (str == null)
			return values;
		StringTokenizer tokenizer = new StringTokenizer(str, split);
		values = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			values.add(tokenizer.nextToken());
		}
		return values;
	}

	/**
	 * Returns a collection of strings.
	 *
	 * @param str the comma separated string values
	 * @return an <code>ArrayList</code> of string values
	 */
	public static Collection<String> getStringCollection(String str) {
		return getStringCollection(str, ",");
	}

	/**
	 * Returns an array list of strings.
	 *
	 * @param str   the comma separated string values
	 * @param split the string value  separating <code>str</code>
	 * @return the array list of the comma separated string values
	 */
	public static String[] getStrings(String str, String split) {
		Collection<String> values = getStringCollection(str, split);
		if (values.size() == 0) {
			return null;
		}
		return values.toArray(new String[values.size()]);
	}

	/**
	 * Returns an array list of strings.
	 *
	 * @param str the comma separated string values
	 * @return the array list of the comma separated string values
	 */
	public static String[] getStrings(String str) {
		Collection<String> values = getStringCollection(str);
		if (values.size() == 0) {
			return null;
		}
		return values.toArray(new String[values.size()]);
	}

	/**
	 * Splits a comma separated value <code>String</code>, trimming leading and trailing whitespace on each value.
	 * @param str a comma separated <String> with values
	 * @return a <code>Collection</code> of <code>String</code> values
	 */
	public static Collection<String> getTrimmedStringCollection(String str){
		return new ArrayList<String>(
						Arrays.asList(getTrimmedStrings(str)));
	}

	/**
	 * Splits a comma separated value <code>String</code>, trimming leading and trailing whitespace on each value.
	 * @param str a comma separated <String> with values
	 * @return an array of <code>String</code> values
	 */
	public static String[] getTrimmedStrings(String str){
		if (null == str || str.trim().isEmpty()) {
			return EMPTY_STRING_ARRAY;
		}

		return str.trim().split("\\s*,\\s*");
	}
}
