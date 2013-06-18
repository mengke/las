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

/**
 * A simple filter to support search of files by name
 *
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class FileNameFilter extends FileCriteria {

	/**
	 * Convenience constructor for passing a string value for the exclude or
	 * include (or both) parameters
	 */
	public FileNameFilter() {
		super(null, null);
	}

	/**
	 * Convenience constructor for passing a string value for the exclude or
	 * include (or both) parameters
	 *
	 * @param include if not null, this must be included in the file name
	 * @param exclude if not null, this string must not appear in the file name
	 */
	public FileNameFilter(String include, String exclude) {
		super(include, exclude);
	}
}
