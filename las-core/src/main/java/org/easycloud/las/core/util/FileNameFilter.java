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
