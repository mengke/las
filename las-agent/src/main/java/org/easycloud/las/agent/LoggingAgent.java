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

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.easycloud.las.agent.cfg.AgentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.easycloud.las.agent.Constants.*;
import static org.easycloud.las.core.util.Assert.assertStateHasLength;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class LoggingAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAgent.class);

    private static final long DEFAULT_LOG_PUSH_PERIOD = 60;
    private static final long DEFAULT_LOG_CLEAR_PERIOD = 1;

    private static LoggingAgent instance = new LoggingAgent();

    private final ScheduledExecutorService workerExecutor;
    private String agentName;
    private AgentConfiguration agentConfiguration;

    private LoggingAgent() {
        agentConfiguration = AgentConfiguration.getInstance();
        agentName = agentConfiguration.get(LOG_AGENT_NAME);
        assertStateHasLength(agentName, "No agent name defined. Please check "
                + LOG_AGENT_NAME + " setting in " + Constants.LOG_AGENT_PROPS);

        workerExecutor = Executors.newScheduledThreadPool(2,
                new ThreadFactoryBuilder().setNameFormat("Log-BackgroundWorker-" + agentName)
                        .build());
    }

    public static LoggingAgent getInstance() {
        return instance;
    }

    public void startUp() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("LoggingAgent: " + agentName + " is ready to start ...");
        }
        workerExecutor.scheduleAtFixedRate(new LogPushThread(agentConfiguration), 0L,
                agentConfiguration.getLong(LOG_PUSH_PERIOD, DEFAULT_LOG_PUSH_PERIOD), TimeUnit.MINUTES);

        if (agentConfiguration.getBoolean(LOG_CLEAR_ENABLED, true)) {
            workerExecutor.scheduleAtFixedRate(new LogClearThread(agentConfiguration), 0L,
                    agentConfiguration.getLong(LOG_CLEAR_PERIOD, DEFAULT_LOG_CLEAR_PERIOD), TimeUnit.DAYS);
        }
    }

    public void shutDown() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(agentName + " is stopping.");
        }
        workerExecutor.shutdown();
    }
}
