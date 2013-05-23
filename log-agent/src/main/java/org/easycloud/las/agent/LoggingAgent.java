package org.easycloud.las.agent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.easycloud.las.agent.cfg.AgentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.easycloud.las.agent.Constants.LOG_AGENT_NAME;
import static org.easycloud.las.agent.Constants.LOG_PUSH_PERIOD;
import static org.easycloud.las.util.Assert.assertStateHasLength;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class LoggingAgent {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAgent.class);

	private static final long DEFAULT_LOG_PUSH_PERIOD = 60;

	private static LoggingAgent instance = new LoggingAgent();

	private final ScheduledExecutorService workerExecutor;
	private String agentName;
	private AgentConfiguration agentConfiguration;

	private LoggingAgent() {
		agentConfiguration = AgentConfiguration.getInstance();
		agentName = agentConfiguration.get(LOG_AGENT_NAME);
		assertStateHasLength(agentName, "No agent name defined. Please check "
						+ LOG_AGENT_NAME + " setting in " + Constants.LOG_AGENT_PROPS);

		workerExecutor = Executors.newSingleThreadScheduledExecutor(
						new ThreadFactoryBuilder().setNameFormat("Log-BackgroundWorker-" + agentName)
										.build());
	}

	public static LoggingAgent getInstance() {
		return instance;
	}

	public void startUp() {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(agentName + " is ready to start ...");
		}
		workerExecutor.scheduleAtFixedRate(new LogPushThread(agentConfiguration),
						0L, agentConfiguration.getLong(LOG_PUSH_PERIOD, DEFAULT_LOG_PUSH_PERIOD), TimeUnit.MINUTES);

	}

	public void shutDown() {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(agentName + " is stopping.");
		}
		workerExecutor.shutdown();
	}
}
