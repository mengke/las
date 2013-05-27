package org.easycloud.las.core.util;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.easycloud.las.core.util.las.exception.FileOperationsException;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * File utility class that operates files
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class Files {

	private static final Logger LOGGER = LoggerFactory.getLogger(Files.class);

	/**
	 * new line *
	 */
	public static String NL = System.getProperty("line.separator", "\r\n");

	/**
	 * setup the default file path convention and log
	 */
	public static final String PATH_SEP = System.getProperty("path.separator", ":");

	/**
	 * This method will look under the current user directory, scan recursively
	 * and return the contents of the first file that matches the passed name. It
	 * does no additional searching once the first file is encountered, so if
	 * there are other files with the same name in another directory, they may not
	 * be encountered, so no "duplicate file error " will be thrown.
	 * <p/>
	 * This method makes every effort to find a file. If it cant find it anywhere
	 * under the current user directory, it looks on the local class path. If it
	 * cant find it there, it looks on the System class path.
	 *
	 * @param shortFileName the name, with no directory info, of the file to find
	 * @return the found file
	 */
	public static File findFile(String shortFileName) throws FileNotFoundException {
		File file = null;
		try {
			ClassPathResource res = new ClassPathResource(shortFileName);
			file = res.getFile();
		} catch (IOException ioe) {
			// Fall-through and try other methods
		}
		if (file == null) {
			File rootDir = new File(new File("").getAbsolutePath());
			file = getFile(rootDir, shortFileName);
		}
		if (file == null) {
			file = findFileOnSystemClasspath(shortFileName);
		}
		if (file == null) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Failed to find file locally or on classpath. File name=" + shortFileName);
			}
			throw new FileNotFoundException(shortFileName);
		}
		return file;
	}

	/**
	 * Create a directory
	 *
	 * @param parentDir the full name of the parent directory to be created
	 * @param childDir  the relative name of the child directory
	 * @return the newly created directory
	 */
	public static File makeDirectory(String parentDir, String childDir) {
		File rc = null;
		try {
			String p = FilenameUtils.normalize(parentDir);
			String c = FilenameUtils.normalize(childDir);
			File f = new File(p, c);
			if (!f.exists()) {
				FileUtils.forceMkdir(f);
			}
			rc = f;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			throw new FileOperationsException("failed directory create " + parentDir + "." + childDir, e);
		}
		return rc;
	}

	/**
	 * Create a directory
	 *
	 * @param dir the full name of the directory to be created
	 * @return the newly created directory
	 */
	public static File makeDirectory(String dir) {
		File rc = null;
		try {
			String n = FilenameUtils.normalize(dir);
			File f = new File(n);
			if (!f.exists()) {
				FileUtils.forceMkdir(f);
			}
			rc = f;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			throw new FileOperationsException("failed to create directory " + dir, e);
		}
		return rc;
	}

	/**
	 * Remove all subdirectories for a given directory
	 *
	 * @param rootDir the directory where we start
	 */
	public static void deleteSubdirectories(String rootDir) {
		try {
			File in = new File(rootDir);
			FileUtils.cleanDirectory(in);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			throw new FileOperationsException(e);
		}
	}

	/**
	 * This method deletes files ( and directories ) based on the passed in
	 * filter. If a file or directory matches the filter, it will be deleted. If
	 * you dont want directories deleted, then make sure the Filter is smart
	 * enough to check. (You can use the FileNameFilter in this package if you
	 * like, as it is smart enough to ignore directories or include them)
	 *
	 * @param dir       the directory where we will begin deleting
	 * @param filter    the filter to be applied before deletion
	 * @param recursive subdirectories are included if true
	 */
	public static void deleteFiles(String dir, FileCriteria filter, boolean recursive) {
		try {
			File in = new File(dir);
			if (!in.exists()) {
				throw new FileNotFoundException(dir);
			}
			Collection<File> files = null;
			if (recursive) {
				files = FileUtils.listFiles(in, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
			} else {
				files = FileUtils.listFiles(in, TrueFileFilter.INSTANCE, null);
			}
			for (File f : files) {
				if (filter.accept(f) && f.exists()) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("deleting file " + f.getAbsolutePath());
					}
					FileUtils.forceDelete(f);
				}
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			throw new FileOperationsException(e);
		}

	}

	/**
	 * Move a file from one directory to another. The directory param strings can
	 * contain either kind of slash and replace it with the correct file
	 * delimiter. It leaves the dates of the copied files intact.
	 *
	 * @param srcDir  the directory to copy
	 * @param destDir the directory to be copied to
	 */
	public static void copyDirectory(String srcDir, String destDir) {
		try {
			File in = new File(srcDir);
			if (!in.exists()) {
				throw new FileNotFoundException(srcDir);
			}
			File out = new File(destDir);
			FileUtils.copyDirectory(in, out, true);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			throw new FileOperationsException(e);
		}
	}

	/**
	 * Convenience method to allow saving xml documents directly
	 * <p/>
	 * Note : This method uses the Jdom Doc type, as most of the common xml
	 * utilities use that in preference to the w3c version. If we need additional
	 * methods to support the w3c version, will add them later;
	 *
	 * @param dir       the directory name
	 * @param fn        the filename
	 * @param doc       the xml doc we wish to see as a string
	 * @param overwrite overwrite the existing file if true
	 */
	public static void createFile(String dir, String fn, Document doc, boolean overwrite) {
		try {
			File directory = new File(dir);
			directory.mkdirs();
			File f = new File(directory, fn);
			if (f.exists()) {
				if (!overwrite) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("File " + f.getAbsolutePath()
										+ " exists, overwrite disabled. File unchanged");
					}
				} else {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("File " + f.getAbsolutePath() + " exists, overwrite disabled. Deleting...");
					}
					f.delete();
					writeXmlFile(f, doc);
				}
			} else {
				writeXmlFile(f, doc);
			}
		} catch (Exception e) {
			throw new FileOperationsException(e);
		}
	}

	/**
	 * Creates a new file. If the file already exists, then the olderThan date is
	 * checked to see if the file can be overwritten. This method is useful if you
	 * need to modify or change files based on date criteria
	 *
	 * @param dir       the file directory
	 * @param fn        the file name
	 * @param content   the content to write
	 * @param timestamp in millis. If the existing file has modified date after the passed
	 *                  date, it will not be overwritten.
	 */
	public static void createFile(String dir, String fn, String content, long timestamp) {
		boolean overwrite = false;
		File directory = new File(dir);
		File f = new File(directory, fn);
		if (f.exists()) {
			if (f.lastModified() < timestamp) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("existing file :" + new Date(f.lastModified()) + ", new file: "
									+ new Date(timestamp));
				}
				overwrite = true;
			}
		}
		createFile(dir, fn, content, overwrite);
	}

	/**
	 * Utility method to write a file to the hard drive
	 *
	 * @param dir       the directory name
	 * @param fn        the file name
	 * @param content   the content to write
	 * @param overwrite if the file exists, overwrite it
	 * @return the file object if created, else return null
	 */
	public static File createFile(String dir, String fn, String content, boolean overwrite) {
		File f = null;
		try {
			File directory = new File(dir);
			directory.mkdirs();
			f = new File(directory, fn);
			if (f.exists()) {
				if (!overwrite) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("File " + f.getAbsolutePath()
										+ " exists, overwrite disabled. File unchanged");
					}
				} else {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("File " + f.getAbsolutePath() + " exists, overwrite disabled. Deleting...");
					}
					f.delete();
					writeToFile(f, content);
				}
			} else {
				writeToFile(f, content);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return f;
	}

	/**
	 * Utility method to write a temporary file to the hard drive
	 * Note!! This method creates the directory under the current
	 * user directory, not the absolute directory path specified
	 * by the passed in dir string. This is to insure that temp files
	 * created by the application will be able to be found by the
	 * file search methods in this class. Note that this calls the
	 * Java file function to create the temp file, so the actual
	 * file name is generated by that call by appending generated
	 * character string to the fnPrefix param. If you want to find the
	 * file again, then use the name of the returned File to do so,
	 * not the passed in fnPrefix parameter.
	 *
	 * @param dir      the directory name
	 * @param fnPrefix the file name prefix that will be used as the base of generated name
	 * @param ext      the file extension
	 * @return the file object if created, else return null
	 */
	public static File createTempFile(String dir, String fnPrefix, String ext) {
		return createTempFile(dir, fnPrefix, ext, null);
	}

	/**
	 * Utility method to write a temporary file to the hard drive
	 * Note!! This method creates the directory under the current
	 * user directory, not the absolute directory path specified
	 * by the passed in dir string. This is to insure that temp files
	 * created by the application will be able to be found by the
	 * file search methods in this class. Note that this calls the
	 * Java file function to create the temp file, so the actual
	 * file name is generated by that call by appending generated
	 * character string to the fnPrefix param. If you want to find the
	 * file again, then use the name of the returned File to do so,
	 * not the passed in fnPrefix parameter.
	 *
	 * @param dir      the directory name
	 * @param fnPrefix the file name prefix that will be used as the base of generated name
	 * @param ext      the file extension
	 * @param content  the data to put in the file
	 * @return the file object if created, else return null
	 */
	public static File createTempFile(String dir, String fnPrefix, String ext, String content) {
		FileOutputStream fos = null;
		File file = null;
		try {
			if (dir == null) {
				dir = new File("").getAbsolutePath() + File.separator + dir;
			} else {
				if (!dir.endsWith(File.separator)) {
					dir = dir + File.separator;
				}
			}
			File directory = new File(dir);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			String extension = ext;
			if (extension != null) {
				if (!extension.contains(".")) {
					extension = "." + extension;
				}
			}
			file = File.createTempFile(fnPrefix, extension, directory);
			if (content != null) {
				fos = new FileOutputStream(file);
				byte[] bytes = content.getBytes();
				fos.write(bytes);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error("unexpected error closing filestream", e);
					}
				}
			}
		}
		return file;
	}

	/**
	 * Find a list of files based on a passed in file name filter relative to a
	 * directory
	 *
	 * @param rootDir   where we should start searching
	 * @param filter    file name filter
	 * @param recursive true if we should search directories recursively
	 * @return an array of files passing the filter
	 */
	public static File[] findFiles(File rootDir, FileCriteria filter, boolean recursive) {
		List<File> files = new ArrayList<File>();
		getFiles(rootDir, filter, recursive, files);
		return files.toArray(new File[files.size()]);
	}

	private static void getFiles(File rootDir, FileCriteria filter, boolean recursive, List<File> files) {
		rootDir = rootDir == null ? new File(new File("").getAbsolutePath()) : rootDir;
		if (rootDir.listFiles() != null) {
			File[] fileList = rootDir.listFiles();
			if (fileList != null) {
				for (File dirOrFile : fileList) {
					if (filter.accept(dirOrFile)) {
						files.add(dirOrFile);
					} else {
						if (recursive && dirOrFile.isDirectory()) {
							getFiles(dirOrFile, filter, recursive, files);
						}
					}
				}
			}
		}
	}

	private static void writeToFile(File f, String content) {
		FileOutputStream fos = null;
		try {
			f.createNewFile();
			fos = new FileOutputStream(f);
			byte[] bytes = content.getBytes();
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error("unexpected error closing filestream", e);
					}
				}
			}
		}
	}


	private static void writeXmlFile(File f, Document doc) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			XMLOutputter xmlo = new XMLOutputter();
			xmlo.output(doc, fos);
		} catch (Exception e) {
			throw new FileOperationsException(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	private static File getFile(File rootDir, String shortFileName) {
		File[] filesAndDirs = rootDir.listFiles();
		if (filesAndDirs != null) {
			for (File file : filesAndDirs) {
				if (file.isFile()) {
					if (file.getName().equals(shortFileName)) {
						return file;
					}
				} else {
					// Skip directory entries and hidden directories such as .svn
					if (!file.getName().startsWith(".")) {
						File result = getFile(file, shortFileName);
						if (result != null) {
							return result;
						}
					}
				}
			}
		}
		return null;
	}

	private static File findFileOnSystemClasspath(String shortFileName) {
		String path = System.getProperty("java.class.path");
		String[] dirs = path.split(PATH_SEP);
		String[] children;
		FileNameFilter fnf = new FileNameFilter();
		fnf.endsWith(File.separator + shortFileName);
		for (String dir : dirs) {
			File file = new File(dir);
			children = file.list(fnf);
			if (children != null && children.length > 0) {
				file = new File(dir + File.separator + children[0]);
				return file;
			}
		}
		return null;
	}
}
