package org.easycloud.las.agent;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.easycloud.las.agent.cfg.AgentConfiguration;
import org.easycloud.las.cfg.Configuration;
import org.easycloud.las.util.FileNameFilter;
import org.easycloud.las.util.Files;
import org.easycloud.las.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.easycloud.las.agent.Constants.*;
import static org.easycloud.las.util.Assert.assertState;
import static org.easycloud.las.util.TimeUtil.DEFAULT_TIME_FORMAT;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-23
 */
public class LogClearThread implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogClearThread.class);
	private static final int DEFAULT_BEFORE_DAYS = 1;

	private AgentConfiguration agentConfiguration;

	public LogClearThread(AgentConfiguration agentConfiguration) {
		this.agentConfiguration = agentConfiguration;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("LogClearThread: started at " + DEFAULT_TIME_FORMAT.format(start));
		}

		String rootPath = agentConfiguration.get(LOG_ROOT_PATH);
		String dirPattern = agentConfiguration.get(LOG_DIRECTORY_PATTERN);
		DateFormat df = new SimpleDateFormat(dirPattern);

		Configuration logPushCfg = new Configuration(LOG_PUSH_PROPS);
		String lastPushed = logPushCfg.get(LAST_PUSHED);
		Date lastPushedTime = parseDate(lastPushed, DEFAULT_TIME_FORMAT);

		File[] logDirs = prepareForDeleting(rootPath);

		for (File logDir : logDirs) {
			boolean shouldDelete = shouldDelete(rootPath, df, lastPushedTime, logDir);

			if (shouldDelete) {
				try {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Deleting the directory [" + logDir.getPath() + "]");
					}
					FileUtils.forceDelete(logDir);
				} catch (IOException e) {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error("LogClearThread an unknown error occurred when deleting directories. Exception follows.", e);
					}
				}
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The directory [" + logDir.getPath() + "] should be skipped due to it's logging time.");
				}
			}
		}

		if (LOGGER.isInfoEnabled()) {
			long end = System.currentTimeMillis();
			LOGGER.info("LogClearThread: finished at " + DEFAULT_TIME_FORMAT.format(end)
							+ ", elapsed: " + TimeUtil.elapsedTime(start, end));
		}
	}

	private boolean shouldDelete(String rootPath, DateFormat df, Date lastPushedTime, File logDir) {
		String absDirPath = logDir.getAbsolutePath();
		String relDirPath = StringUtils.remove(absDirPath, rootPath);
		// TODO hard code "/" here, maybe the windows can't be supported
		if (StringUtils.startsWith(relDirPath, "/")) {
			relDirPath = relDirPath.substring(1);
		}
		if (StringUtils.endsWith(relDirPath, "/")) {
			relDirPath = relDirPath.substring(0, relDirPath.length() - 1);
		}
		Date loggingTime = parseDate(relDirPath, df);
		if (loggingTime == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("LogPushThread: Parsing the log directory [" + logDir.getPath() + "] failed, and it's skipped.");
			}
			return false;
		} else {
			return TimeUtil.isBeforeNow(loggingTime,
							agentConfiguration.getInt(LOG_CLEAR_BEFORE, DEFAULT_BEFORE_DAYS), Calendar.DATE)
							&& TimeUtil.isBefore(loggingTime, lastPushedTime);
		}
	}

	private File[] prepareForDeleting(String rootPath) {
		File rootDir = new File(rootPath);
		assertState(rootDir.exists() && rootDir.isDirectory(), "LogClearThread the file specified in " + LOG_AGENT_PROPS +
						" doesn't exist or isn't a directory. Please check the [" + LOG_ROOT_PATH + "] setting.");
		FileNameFilter filter = new FileNameFilter();
		filter.onlyDirectories();
		return Files.findFiles(rootDir, filter, false);
	}

	private Date parseDate(String timeStr, DateFormat df) {
		Date time = null;
		try {
			if (StringUtils.isNotBlank(timeStr)) {
				time = df.parse(timeStr);
			}
		} catch (ParseException e) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("LogClearThread: parsing the logging time failed. Exception follows.", e);
			}
		}
		return time;
	}
}
