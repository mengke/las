package org.easycloud.las.core.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class FileCriteriaTests {

	@Test
	public void testAccept(){
		FileCriteria fc = new FileCriteria("aaa","*");
		Assert.assertTrue(fc.accept(new File("aaa")));
	}

	@Test
	public void testAccept2() {
		FileCriteria fc = new FileCriteria();
		fc.endsWith(".txt");
		fc.onlyFiles();
		Assert.assertTrue(fc.accept(new File("a.txt")));
		Assert.assertFalse(fc.accept(new File("bbb/aaa")));
		Assert.assertTrue(fc.accept(new File("bbb/aaa.txt")));
	}

	@Test
	public void testNotAccept(){
		FileCriteria fc = new FileCriteria("aaa","a");
		Assert.assertFalse(fc.accept(new File("aaa")));
	}

	@Test
	public void testNotAccept2(){
		FileCriteria fc = new FileCriteria("b","q");
		Assert.assertFalse(fc.accept(new File("aaa")));
	}

	@Test
	public void testNotAccept2_directory(){
		FileCriteria fc = new FileCriteria("b","q");
		// Make sure the directory name is not included in the checks
		Assert.assertFalse(fc.accept(new File("bbb/aaa")));
	}


}
