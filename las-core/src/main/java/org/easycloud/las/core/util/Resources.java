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

import org.apache.commons.lang.StringUtils;
import org.easycloud.las.core.exception.FileOperationsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

import static java.util.Map.Entry;

/**
 * Resource utility class that does some resources or properties files stuff
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class Resources {

	private static final Logger LOGGER = LoggerFactory.getLogger(Resources.class);

	/**
	 * Provide a means to determine if a file name passed exists.
	 * Good idea to call this before calling the loadProperties()
	 * method with the file name if you dont want to throw a file
	 * not found exception. If the file name passed contains directory
	 * information ( a File.separator character ) then we look to
	 * see if the file exists with the passed name as is.
	 * If the file name contains no separator character, then it is
	 * assumed to be the name of a resource, and we will look to see
	 * if a resource of the name exists on the classpath. We do this
	 * by trying to create a ClassPathResource with the passed name and
	 * see if it exists.
	 *
	 * @param fileName the name of the file to be checked
	 * @return true if the file exists.
	 */
	public static boolean resourceExists(String fileName) {
		boolean rc = false;
		if (fileName.contains(File.separator)) {
			File resource = new File(fileName);
			if (resource.exists()) {
				rc = true;
			}
		} else {
			ClassPathResource res = new ClassPathResource(fileName);
			if (res.exists()) {
				rc = true;
			}
		}
		return rc;
	}

	/**
	 * If the passed file name contains directory info, load it
	 * directly. Else, a bare file name with no directory info,
	 * try to load it as a resource on the system class path
	 *
	 * @param fileName the name of the file to be loaded
	 * @return all the properties in the file
	 */
	public static Properties loadProperties(String fileName) {
		Properties props = new Properties();
		InputStream fis = null;
		try {
			if (StringUtils.isNotEmpty(fileName)) {
				if (fileName.contains(File.separator)) {
					fis = new FileInputStream(new File(fileName));
					props.load(fis);
					fis.close();
				} else {
					ClassPathResource res = new ClassPathResource(fileName);
					fis = res.getInputStream();
					props.load(fis);
				}
				Iterator itr = props.entrySet().iterator();
				while (itr.hasNext()) {
					Entry e = (Entry) itr.next();
					e.setValue(e.getValue().toString().trim());
				}
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			throw new FileOperationsException("Failed to load " + fileName, e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		}
		return props;
	}

	/**
	 * Load the given <tt>Resource</tt> and return
	 * the contents as a String
	 *
	 * @param resource the <tt>Resource</tt>
	 * @return the contents of the resource or null if it's an invalid resource
	 */
	public static String loadResource(Resource resource) {
		if (resource != null) {
			try {
				return loadResource(resource.getInputStream());
			} catch (Exception ex) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(ex.getMessage(), ex);
				}
				throw new FileOperationsException("Failed to load resource " + resource.getFilename(), ex);
			}
		}
		return null;
	}

	/**
	 * Load the given InputStream resource and return
	 * the contents as a String
	 *
	 * @param is the InputStream
	 * @return the contents of the resource
	 */
	public static String loadResource(InputStream is) {
		try {
			StringBuffer sb = new StringBuffer();
			if (is != null) {
				InputStreamReader reader = new InputStreamReader(is);
				BufferedReader in = new BufferedReader(reader);
				while (true) {
					String line = in.readLine();
					if (line == null) {
						break;
					}
					sb.append(line);
				}
			}
			return sb.toString();
		} catch (Exception ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ex.getMessage(), ex);
			}
			throw new FileOperationsException("Failed to load resource", ex);
		}
	}

}
