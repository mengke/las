package org.easycloud.las.agent;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.EventDeliveryException;
import org.easycloud.las.agent.cfg.AgentConfiguration;
import org.easycloud.las.cfg.Configuration;
import org.easycloud.las.cfg.PropertiesWriter;
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

import static org.easycloud.las.agent.AvroClient.DEFAULT_AVRO_PORT;
import static org.easycloud.las.agent.Constants.*;
import static org.easycloud.las.util.Assert.assertState;
import static org.easycloud.las.util.Assert.assertStateHasLength;
import static org.easycloud.las.util.TimeUtil.DEFAULT_TIME_FORMAT;

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

		String latestPushed = null;
		for (File logFile : logs) {
			String loggingTimeStr = parseLoggingTime(rootPath, postfix, logFile);

			latestPushed = pushLogFile(lastPushedTime, hostName, port, latestPushed, logFile, loggingTimeStr);
		}

		if (StringUtils.isNotBlank(latestPushed)) {
			DateFormat df = new SimpleDateFormat(agentConfiguration.get(LOG_NAME_PATTERN));
			Date latestPushedTime = parseDate(latestPushed, df);
			if (latestPushedTime != null) {
				PropertiesWriter propsWriter = new PropertiesWriter(logPushCfg);
				propsWriter.persist(LAST_PUSHED, TimeUtil.DEFAULT_TIME_FORMAT.format(latestPushedTime));
			}
		}
	}

	private String pushLogFile(Date lastPushedTime, String hostName, int port, String latestPushed, File logFile, String loggingTimeStr) {
		boolean needPush = isNeedPush(loggingTimeStr, lastPushedTime);
		if (needPush) {
			if (TimeUtil.isAfter(loggingTimeStr, agentConfiguration.get(LOG_NAME_PATTERN), lastPushedTime)) {
				latestPushed = loggingTimeStr;
			}
			AvroClient avroClient = new AvroClient(hostName, port, logFile);
			try {
				avroClient.push();
			} catch (EventDeliveryException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("LogPushThread Unable to deliver events to Flume. Exception follows.", e);
				}
			} catch (IOException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("LogPushThread Unable to send data to Flume. Exception follows.", e);
				}
			}
		}
		return latestPushed;
	}

	private Date parseDate(String timeStr, DateFormat df) {
		Date time = null;
		try {
			if (StringUtils.isNotBlank(timeStr)) {
				time = df.parse(timeStr);

			}
		} catch (ParseException e) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("LogPushThread parsing the logging time failed. Exception follows.", e);
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

	private boolean isNeedPush(String loggingTime, Date lastPushedTime) {
		boolean needPush = false;
		boolean isAfter = TimeUtil.isAfter(loggingTime, agentConfiguration.get(LOG_NAME_PATTERN), lastPushedTime);
		boolean isBeforeNow = TimeUtil.isBeforeNow(loggingTime, agentConfiguration.get(LOG_NAME_PATTERN),
						agentConfiguration.getInt(LOG_PUSH_BEFORE, DEFAULT_BEFORE_HOURS), Calendar.HOUR);
		needPush = isAfter && isBeforeNow;

		return needPush;
	}

}
