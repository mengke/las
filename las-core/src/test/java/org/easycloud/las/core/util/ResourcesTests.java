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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import static org.testng.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class ResourcesTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesTests.class);
	@Test
	public void basic(){

		boolean expected = false;
		Throwable t = null;
		try {
			String fn = "resourcesTests.properties";
			Resources.resourceExists(fn);
			Properties props = Resources.loadProperties(fn);
			assert props != null : "Could not find the properties";
			Resources.loadProperties( new String());
			File f = Files.findFile(fn);
			fn = f.getAbsolutePath();
			props = Resources.loadProperties(fn);
			Resource resource = new FileSystemResource(f);
			Resources.loadResource(resource);
			Resources.loadResource(new FileInputStream(f));
			Resources.resourceExists(f.getAbsolutePath());


			expected = true;
			Resources.loadProperties("Bogus file");
		}
		catch(Throwable error){
			t = error;
			LOGGER.error("Unexpected error", error);
			t = expected ? null : t;
		}
		assert t == null;
	}

	@Test
	public void testNotLoadProperties(){
		try {
			Resources.loadProperties("badfilename");
			fail();
		}catch (Exception e){}
	}

	@Test
	public void testTrimProperties(){
		String fn = "resourcesTests.properties";
		File f = null;
		try {
			f = Files.findFile(fn);
			fn = f.getAbsolutePath();

			Properties p=Resources.loadProperties(fn);
			org.testng.Assert.assertEquals(p.getProperty("space_string"), "true");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}


	}
}
