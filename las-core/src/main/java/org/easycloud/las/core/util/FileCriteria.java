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

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class FileCriteria implements FileFilter, FilenameFilter {

	/**
	 * list of other criteria that must be met *
	 */
	private List<FileCriteria> and = new ArrayList<FileCriteria>();

	/**
	 * conditions that must be orred *
	 */
	private List<FileCriteria> or = new ArrayList<FileCriteria>();

	/**
	 * controls whether filter is applied to directories*
	 */
	private boolean directoryFilter = false;

	/**
	 * filename must contain these strings *
	 */
	private List<String> includes = new ArrayList<String>();

	/**
	 * filename must not contain these strings *
	 */
	private List<String> excludes = new ArrayList<String>();

	/**
	 * value for ends with *
	 */
	private List<String> endsWith = new ArrayList<String>();

	/**
	 * filename must match these regular expressions
	 */
	private List<String> includeRegExpr = new ArrayList<String>();

	/**
	 * filename must NOT match these regular expressions
	 */
	private List<String> excludeRegExpr = new ArrayList<String>();

	/**
	 * if true, filter only directories *
	 */
	private boolean onlyDirectories = false;

	/**
	 * if true, filter only files *
	 */
	private boolean onlyFiles = false;


	/**
	 * short file name *
	 */
	private String shortFileName;

	/**
	 * Default constructor for passing a string value for the exclude or
	 * include (or both) parameters
	 */
	public FileCriteria() {
		// Default constructor
	}


	/**
	 * For any file, check the file name match exactly before
	 * accepting.
	 *
	 * @param pShortFileName the short file name (no directory info) of the file to be found
	 * @return this
	 */
	public FileCriteria fileNameEquals(String pShortFileName) {
		this.shortFileName = pShortFileName;
		return this;
	}


	/**
	 * Convenience constructor for passing a string value for the exclude or
	 * include (or both) parameters
	 *
	 * @param include if not null, this must be included in the file name
	 * @param exclude if not null, this string must not appear in the file name
	 */
	public FileCriteria(String include, String exclude) {
		if (include != null) {
			includes.add(include);
		}
		if (exclude != null) {
			excludes.add(exclude);
		}
	}

	/**
	 * If the file name contains a prohibited string , or the file does not
	 * contain a required string, then false will be returned
	 *
	 * @param file the File instance to be considered
	 * @return false if the filter does not accept the file
	 */
	@Override
	public boolean accept(File file) {

		// unconditional checks
		if ((file.isFile() && onlyDirectories) ||
						(file.isDirectory() && onlyFiles)) {
			return false;
		}
		// if short file name != null, it must match
		if (shortFileName != null) {
			if (file.isFile() && file.getName().equals(shortFileName)) {
				return true;
			} else {
				return false;
			}
		}

		boolean accept = false;
		// if we meet an or condition, don't check anymore
		accept = satisfiesOr(file);

		// if or conditions not satisfied but ands are
		if (!accept && satisfiesAnd(file)) {
			accept = true;
			String fileName = file.getName();
			// reject if directory and we don't do directories
			if (file.isDirectory() && (!isDirectoryFilter())) {
				accept = false;
			}
			// do the ends with check - inverse logic a bit
			// the file must end with one of the strings in
			// the endsWith list in order to be accepted.
			if (accept && endsWith.size() > 0) {
				accept = false;
				for (String ew : endsWith) {
					if (ew != null && (fileName.endsWith(ew))) {
						accept = true;
						break;
					}
				}
			}
			// passed the directory and end with check
			if (accept && includes.size() > 0) {
				for (String incl : includes) {
					if (!fileName.contains(incl)) {
						accept = false;
						break;
					}
				}
			}
			// passed the include filter
			if (accept && excludes.size() > 0) {
				for (String excl : excludes) {
					if (fileName.contains(excl)) {
						accept = false;
						break;
					}
				}
			}
			// passed the inclusion filters, insure that the name matches
			if (accept && includeRegExpr.size() > 0) {
				for (String regex : includeRegExpr) {
					if (!Pattern.matches(regex, fileName) &&
									!Pattern.matches(regex, file.getAbsolutePath())) {
						accept = false;
						break;
					}
				}
			}

			// using the exclusion filters, make sure the name doesn't match
			if (accept && excludeRegExpr.size() > 0) {
				for (String regex : excludeRegExpr) {
					if (Pattern.matches(regex, shortFileName) ||
									Pattern.matches(regex, file.getAbsolutePath())) {
						accept = false;
						break;
					}
				}
			}

		}
		return accept;
	}

	/**
	 * Implement the mandated method for FilenameFilter
	 *
	 * @param dir      the directory in which the file is found
	 * @param fileName the short file name
	 * @return true if the file passes filtering
	 */
	@Override
	public boolean accept(File dir, String fileName) {
		File f = new File(dir, fileName);
		return accept(f);
	}

	/**
	 * Mandate that a file name contain the passed string
	 *
	 * @param value string that must be in file name
	 * @return this
	 */
	public FileCriteria include(String value) {
		includes.add(value);
		return this;
	}

	/**
	 * Exclude any file that contains the passed string as part of its fully
	 * qualified name
	 *
	 * @param value the string which cannot be contained in the name
	 * @return this
	 */
	public FileCriteria exclude(String value) {
		excludes.add(value);
		return this;
	}


	/**
	 * Mandates that a file ends with the passed string value If there are other
	 * checks, they are applied as well if this one passes.
	 *
	 * @param value the string the file must end with
	 * @return this
	 */
	public FileCriteria endsWith(String value) {
		endsWith.add(value);
		return this;
	}

	/**
	 * Mandate that a file name must match the passed regular expression
	 *
	 * @param regex the regular expression the file must match
	 * @return this
	 */
	public FileCriteria matches(String regex) {
		includeRegExpr.add(regex);
		return this;
	}

	/**
	 * Mandate that a file name must not contain the character sequences described
	 * by the passed regular expression
	 *
	 * @param regex the regular expression the file must match
	 * @return this
	 */
	public FileCriteria excludeMatches(String regex) {
		excludeRegExpr.add(regex);
		return this;
	}

	/**
	 * Directive that tells the filter to apply against files only
	 *
	 * @return this
	 */
	public FileCriteria onlyFiles() {
		this.onlyFiles = true;
		directoryFilter = false;
		return this;
	}

	/**
	 * Directive that tells the filter to apply against directories as well as
	 * files. The default is always that the filter will not accept a directory.
	 * But if this toggle is flipped, then the filter will accept directories that
	 * match the provided name criteria
	 *
	 * @return this
	 */
	public FileCriteria onlyDirectories() {
		this.onlyDirectories = true;
		allowDirectories();
		return this;
	}

	/**
	 * Directive that tells the filter to apply against directories as well as
	 * files. The default is always that the filter will not accept a directory.
	 * But if this toggle is flipped, then the filter will accept directories that
	 * match the provided name criteria
	 *
	 * @return this
	 */
	public FileCriteria allowDirectories() {
		directoryFilter = true;
		return this;
	}


	/**
	 * Add a file criteria to be applied with this criteria.
	 * Result is that this filter must pass and
	 *
	 * @param crit the criteria to be anded with this
	 * @return this
	 */
	public FileCriteria and(FileCriteria crit) {
		and.add(crit);
		return this;
	}

	/**
	 * Add a file criteria to be orred with this criteria.
	 *
	 * @param crit the criteria to be orred
	 * @return this
	 */
	public FileCriteria or(FileCriteria crit) {
		or.add(crit);
		return this;
	}

	/**
	 * Iterato over all the or filters.
	 * return true if one passes
	 *
	 * @param file the File instance to be considered
	 * @return false if the filter does not accept the file
	 */
	private boolean satisfiesOr(File file) {
		boolean accept = false;
		// passed the orred criteria, see if it matches the orred criteria
		for (FileCriteria fc : or) {
			if (fc.accept(file)) {
				accept = true;
				break;
			}
		}
		return accept;
	}


	/**
	 * Iterato over all the anded filters.
	 * Insure that each filter passes
	 *
	 * @param file the File instance to be considered
	 * @return false if the filter does not accept the file
	 */
	private boolean satisfiesAnd(File file) {
		boolean accept = true;
		for (FileCriteria fc : and) {
			if (!fc.accept(file)) {
				accept = false;
				break;
			}
		}
		return accept;
	}


	/**
	 * Check if this filter is a directory only filter
	 *
	 * @return true if this is a directory filter
	 */
	public boolean isDirectoryFilter() {
		return directoryFilter;
	}
}
