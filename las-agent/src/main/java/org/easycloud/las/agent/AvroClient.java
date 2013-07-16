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

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.client.avro.BufferedLineReader;
import org.apache.flume.client.avro.LineReader;
import org.apache.flume.event.EventBuilder;
import org.easycloud.las.agent.cfg.AgentConfiguration;
import org.easycloud.las.core.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easycloud.las.agent.Constants.LOG_PROPERTY_DELIMITER;
import static org.easycloud.las.agent.Constants.LOG_TIMESTAMP_PATTERN;
import static org.easycloud.las.core.util.TimeUtil.DEFAULT_TIME_PATTERN;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class AvroClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvroClient.class);
    static final int DEFAULT_AVRO_PORT = 41414;
    static final String DEFAULT_LOG_DELIMITER = "   ";

    private static final int BATCH_SIZE = 5;

    private String hostname;
    private int port;
    private File logFile;

    private int sent;

    public AvroClient(String hostname, int port, File logFile) {
        this.hostname = hostname;
        this.port = port;
        this.logFile = logFile;
    }

    public void push() throws EventDeliveryException, IOException {

        RpcClient rpcClient = RpcClientFactory.getDefaultInstance(hostname, port, BATCH_SIZE);

        AgentConfiguration configuration = AgentConfiguration.getInstance();
        String logDelimiter = configuration.get(LOG_PROPERTY_DELIMITER, DEFAULT_LOG_DELIMITER);
        String timestampPattern = configuration.get(LOG_TIMESTAMP_PATTERN, DEFAULT_TIME_PATTERN);
        DateFormat df = new SimpleDateFormat(timestampPattern);

        LineReader reader = null;
        try {
            reader = new BufferedLineReader(new FileReader(logFile));
            long lastCheck = TimeUtil.now();
            long sentBytes = 0;

            int batchSize = rpcClient.getBatchSize();
            List<String> lines;
            while (!(lines = reader.readLines(batchSize)).isEmpty()) {
                List<Event> eventBuffer = Lists.newArrayList();
                for (String line : lines) {
                    Event event = EventBuilder.withBody(line, Charsets.UTF_8);

                    if (addTimestamp(logDelimiter, timestampPattern, df, line, event)) continue;

                    eventBuffer.add(event);
                    sentBytes += event.getBody().length;
                    sent++;

                    long now = System.currentTimeMillis();
                    if (now >= lastCheck + 5000) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Packed {} bytes, {} events", sentBytes, sent);
                        }
                        lastCheck = now;
                    }
                }
                rpcClient.appendBatch(eventBuffer);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Finished");
            }
        } finally {
            if (reader != null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Closing reader");
                }

                reader.close();
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Closing RPC client");
            }

            rpcClient.close();
        }
    }

    private boolean addTimestamp(String logDelimiter, String timestampPattern, DateFormat df, String line, Event event) {
        if (StringUtils.isNotBlank(line)) {
            int firstDelimiter = line.indexOf(logDelimiter);
            if (firstDelimiter < 0) {
                LOGGER.warn("The log format isn't correct. " + line);
                return true;
            }
            int startIndex = firstDelimiter + logDelimiter.length();
            int endIndex = startIndex + timestampPattern.length();
            String logTimeStr = line.substring(startIndex, endIndex);
            try {
                Date logTime = df.parse(logTimeStr);
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("timestamp", String.valueOf(logTime.getTime()));
                event.setHeaders(headers);
            } catch (ParseException e) {
                LOGGER.warn("The log format or the pattern of timestamp isn't correct. Please check the configuration and the log follows." + line);
                return true;
            }
        }
        return false;
    }

}
