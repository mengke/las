package org.easycloud.las.agent;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.client.avro.BufferedLineReader;
import org.apache.flume.client.avro.LineReader;
import org.apache.flume.event.EventBuilder;
import org.easycloud.las.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class AvroClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvroClient.class);
	static final int DEFAULT_AVRO_PORT = 41414;

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
//			logFile.delete();
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

}
