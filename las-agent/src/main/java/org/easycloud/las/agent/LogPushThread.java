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

package org.easycloud.las.agent;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.flume.EventDeliveryException;
import org.easycloud.las.agent.cfg.AgentConfiguration;
import org.easycloud.las.core.cfg.Configuration;
import org.easycloud.las.core.cfg.PropertiesWriter;
import org.easycloud.las.core.util.FileNameFilter;
import org.easycloud.las.core.util.Files;
import org.easycloud.las.core.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.easycloud.las.agent.AvroClient.DEFAULT_AVRO_PORT;
import static org.easycloud.las.agent.Constants.*;
import static org.easycloud.las.core.util.Assert.assertState;
import static org.easycloud.las.core.util.Assert.assertStateHasLength;
import static org.easycloud.las.core.util.TimeUtil.DEFAULT_TIME_FORMAT;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class LogPushThread implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogPushThread.class);
	private static final int DEFAULT_BEFORE_HOURS = 1;

	private AgentConfiguration agentConfiguration;

	public LogPushThread(AgentConfiguration agentConfiguration) {
		this.agentConfiguration = agentConfiguration;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("LogPushThread: started at " + DEFAULT_TIME_FORMAT.format(start));
		}

		Configuration logPushCfg = new Configuration(LOG_PUSH_PROPS);
		String lastPushed = logPushCfg.get(LAST_PUSHED);
		Date lastPushedTime = parseDate(lastPushed, DEFAULT_TIME_FORMAT);

		String rootPath = agentConfiguration.get(LOG_ROOT_PATH);
		String postfix = agentConfiguration.get(LOG_FILE_POSTFIX);
		if (!postfix.startsWith(".")) {
			postfix = "." + postfix;
		}

		File[] logs = prepareForPushing(rootPath, postfix);

		String hostName = agentConfiguration.get(Constants.AVRO_SOURCE_HOST);
		int port = agentConfiguration.getInt(Constants.AVRO_SOURCE_PORT, DEFAULT_AVRO_PORT);

		List<Date> loggingTimes = new ArrayList<Date>();
		DateFormat df = new SimpleDateFormat(agentConfiguration.get(LOG_FILE_PATTERN));
		for (File logFile : logs) {
			String loggingTimeStr = parseLoggingTime(rootPath, postfix, logFile);
			Date loggingTime = parseDate(loggingTimeStr, df);

			if (loggingTime != null) {
				boolean hasPushed = pushLogFile(lastPushedTime, hostName, port, logFile, loggingTime);
				if (hasPushed) {
					loggingTimes.add(loggingTime);
				}
			} else {
				if (LOGGER.isWarnEnabled()) {
					LOGGER.warn("LogPushThread: Parsing the log file [" + logFile.getPath() + "] failed, and it's skipped.");
				}
			}

		}

		if (CollectionUtils.isNotEmpty(loggingTimes)) {
			Date latestPushedTime = Collections.max(loggingTimes);
			PropertiesWriter propsWriter = new PropertiesWriter(logPushCfg);
			propsWriter.persist(LAST_PUSHED, DEFAULT_TIME_FORMAT.format(latestPushedTime));
		} else {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("LogPushThread: no files was pushed.");
			}
		}

		if (LOGGER.isInfoEnabled()) {
			long end = System.currentTimeMillis();
			LOGGER.info("LogPushThread: finished at " + DEFAULT_TIME_FORMAT.format(end) +
							", elapsed: " + TimeUtil.elapsedTime(start, end));
		}
	}

	private boolean pushLogFile(Date lastPushedTime, String hostName, int port, File logFile,
													 Date loggingTime) {
		boolean needPush = isNeedPush(loggingTime, lastPushedTime);
		if (needPush) {
			AvroClient avroClient = new AvroClient(hostName, port, logFile);
			try {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("LogPushThread: Pushing the log file [" + logFile.getPath() + "]");
				}
				avroClient.push();
			} catch (EventDeliveryException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("LogPushThread: Unable to deliver events to Flume. Exception follows.", e);
				}
			} catch (IOException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("LogPushThread: Unable to send data to Flume. Exception follows.", e);
				}
			}
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("LogPushThread: the log file [" + logFile.getPath() + "] is skipped.");
			}
		}
		return needPush;
	}

	private Date parseDate(String timeStr, DateFormat df) {
		Date time = null;
		try {
			if (StringUtils.isNotBlank(timeStr)) {
				time = df.parse(timeStr);
			}
		} catch (ParseException e) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("LogPushThread: parsing the logging time failed. Exception follows.", e);
			}
		}
		return time;
	}

	private String parseLoggingTime(String rootPath, String postfix, File logFile) {
		String absLogPath = logFile.getAbsolutePath();
		String relLogPath = StringUtils.remove(absLogPath, rootPath);
		if (StringUtils.startsWith(relLogPath, "/")) {
			relLogPath = relLogPath.substring(1);
		}
		return StringUtils.remove(relLogPath, postfix);
	}

	private File[] prepareForPushing(String rootPath, String postfix) {
		File rootDir = new File(rootPath);
		assertState(rootDir.exists() && rootDir.isDirectory(), "LogPushThread the file specified in " + LOG_AGENT_PROPS +
						" doesn't exist or isn't a directory. Please check the [" + LOG_ROOT_PATH + "] setting.");
		FileNameFilter filter = new FileNameFilter();

		assertStateHasLength(postfix, "LogPushThread " + LOG_FILE_POSTFIX + " hasn't been defined.");

		filter.endsWith(postfix);
		filter.onlyFiles();
		return Files.findFiles(rootDir, filter, true);
	}

	private boolean isNeedPush(Date loggingTime, Date lastPushedTime) {
		boolean isAfter = lastPushedTime == null || TimeUtil.isAfter(loggingTime, lastPushedTime);
		boolean isBeforeNow = TimeUtil.isBeforeNow(loggingTime,
						agentConfiguration.getInt(LOG_PUSH_BEFORE, DEFAULT_BEFORE_HOURS), Calendar.HOUR);
		return isAfter && isBeforeNow;
	}

}
