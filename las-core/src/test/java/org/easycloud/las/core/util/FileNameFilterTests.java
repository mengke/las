package org.easycloud.las.core.util;

import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class FileNameFilterTests {

	@Test
	public void testNewFileNameFilter(){
		FileNameFilter f = new FileNameFilter("","");
		org.testng.Assert.assertNotNull(f);
	}

	@Test
	public void testNewFileNameFilter1(){
		FileNameFilter f = new FileNameFilter();
		org.testng.Assert.assertNotNull(f);
	}
}
