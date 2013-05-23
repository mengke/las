package org.easycloud.las.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class FilesTests {

	@Test
	public void testMakeDirectory2(){
		File f = Files.makeDirectory("testdir44");
		org.testng.Assert.assertNotNull(f);
		f.delete();
	}

	@Test
	public void testMakeDirectory3(){
		File f = Files.makeDirectory("testdir55","testdir33");
		org.testng.Assert.assertNotNull(f);
		File f2 = f.getParentFile();
		f.delete();
		f2.delete();
	}

	@Test
	public void testDeleteSubDirectories(){
		File f = Files.makeDirectory("testdir777");
		File f2 = Files.makeDirectory("./testdir777","testdir2");
		Files.deleteSubdirectories(f2.getParent());
		org.testng.Assert.assertFalse(f2.exists());
		try{
			FileUtils.deleteDirectory(f);
			org.testng.Assert.assertFalse(f.exists());
		}catch(Exception e){}
	}

	@Test
	public void testCopyDirectory(){
		File f = Files.makeDirectory("testdir999");
		Files.createFile("testdir999", "testfile", "testContent", true);
		Files.copyDirectory("testdir999", "testdir929");
		String n = FilenameUtils.normalize("testdir929");
		File f2 = new File(n);
		org.testng.Assert.assertNotNull(f2);
		Files.deleteSubdirectories(f.getPath());
		f.delete();
		Files.deleteSubdirectories(f2.getPath());
		f2.delete();
	}

	@Test
	public void testFindFiles(){
		File f = Files.makeDirectory("testdir17");
		Files.createFile("testdir17", "testfile", "testContent", true);
		File[] fs = Files.findFiles(f, new FileCriteria(), false);
		org.testng.Assert.assertNotNull(fs);
		org.testng.Assert.assertEquals(1, fs.length);
		Files.deleteSubdirectories(f.getPath());
		f.delete();
	}

	@Test
	public void testDeleteFiles(){
		File f = Files.makeDirectory("testdir555");
		Files.createFile(f.getName(), "testfile", "testContent", true);
		org.testng.Assert.assertFalse(f.list().length == 0);
		Files.deleteFiles(f.getName(), new FileCriteria(), false);
		org.testng.Assert.assertTrue(f.list().length == 0);
		Files.deleteSubdirectories(f.getPath());
		f.delete();
	}

	@Test
	public void testDeleteFiles2(){
		try {
			Files.deleteFiles("baddir55", new FileCriteria(), true);
			org.testng.Assert.fail();
		}catch(Exception e ){}
	}


	@Test
	public void testNotCopyDirectory(){
		try {
			Files.copyDirectory("baddir55", "baddir234");
			org.testng.Assert.fail();
		}catch(Exception e ){}
	}

	@Test
	public void testCreateFile2(){
		File f = Files.makeDirectory("testdir123");
		Files.createFile(f.getName(), "testFileName12", "", 123);
		org.testng.Assert.assertEquals((f.list()[0]), "testFileName12");
		Files.deleteSubdirectories(f.getPath());
		f.delete();
	}

	@Test
	public void testCreateFile4(){
		File f = Files.makeDirectory("testdir123");
		Files.createFile(f.getName(), "testFileName12", "", true);
		long l = f.listFiles()[0].lastModified();
		Files.createFile(f.getName(), "testFileName12", "Content", l);
		org.testng.Assert.assertTrue(l == f.listFiles()[0].lastModified());
		Files.deleteSubdirectories(f.getPath());
		f.delete();
	}

	@Test
	public void testCreateFile5(){
		File f = Files.makeDirectory("testdir456");
		Element e = new Element("test");
		Document doc = new Document(e, null);
		Files.createFile(f.getName(), "testfile.xml", doc, true);
		long l = f.listFiles()[0].lastModified();
		org.testng.Assert.assertEquals((f.list()[0]), "testfile.xml");
		Files.createFile(f.getName(), "testfile.xml", doc, false);
		org.testng.Assert.assertTrue(l == f.listFiles()[0].lastModified());
		Files.deleteSubdirectories(f.getPath());
		f.delete();
	}

}
