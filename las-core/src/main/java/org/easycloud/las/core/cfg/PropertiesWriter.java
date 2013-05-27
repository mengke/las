package org.easycloud.las.core.cfg;


import org.easycloud.las.core.exception.PropertiesPersistException;
import org.easycloud.las.core.util.Files;
import org.easycloud.las.core.util.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

import static org.easycloud.las.core.util.Assert.assertArgHasLength;
import static org.easycloud.las.core.util.Assert.assertArgNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: michaelmeng
 * Date: 12-11-7
 */
public class PropertiesWriter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesWriter.class);

	private String propertiesFileName;

	public PropertiesWriter(String propertiesFileName) {
		assertArgHasLength(propertiesFileName, "PropertiesWriter constructor propertiesFileName null or empty");
		this.propertiesFileName = propertiesFileName;
	}

	public PropertiesWriter(Configuration configuration) {
		assertArgNotNull(configuration, "PropertiesWriter constructor configuration null");
		assertArgHasLength(configuration.resourceName, "PropertiesWriter constructor configuration.resourceName null or empty");
		this.propertiesFileName = configuration.resourceName;
	}

	private File getPersistFile() throws FileNotFoundException {
		return Files.findFile(propertiesFileName);
	}

	public boolean isWritable() {
		try {
			File persistFile = getPersistFile();
			return persistFile.exists() ? persistFile.canWrite() : persistFile
							.getParentFile().canWrite();
		} catch (FileNotFoundException e) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("PropertiesWriter isWritable: can't find the specified file: " + propertiesFileName);
			}
			return false;
		}
	}

	public void persist(Properties p) {
		OutputStream propOutput = null;

		Properties props = Resources.loadProperties(propertiesFileName);

		try {
			props.putAll(p);
			File propertiesFile = getPersistFile();
			propOutput = new FileOutputStream(propertiesFile);
			props.store(propOutput, null);
			if (LOGGER.isWarnEnabled()) {
				LOGGER.info("PropertiesWriter persist: Wrote properties to " + propertiesFileName);
			}
		} catch (Exception e) {
			throw new PropertiesPersistException();
		} finally {
			try {
				if (propOutput != null) {
					propOutput.close();
					propOutput = null;
				}
			} catch (IOException e) {
				propOutput = null;
			}
		}
	}

	public void persist(String key, String value) {
		Properties props = Resources.loadProperties(propertiesFileName);
		props.setProperty(key, value);
		persist(props);
	}

}
