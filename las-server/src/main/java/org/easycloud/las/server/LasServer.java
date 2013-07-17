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
 */

package org.easycloud.las.server;

import org.apache.thrift.TCompositeProcessor;
import org.apache.thrift.TProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.easycloud.las.core.util.Assert.assertStateNotEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-10
 * Time: 上午9:49
 */
public abstract class LasServer {

    private static Logger LOGGER = LoggerFactory.getLogger(LasServer.class);

    protected Map<String, TProcessor> processors = new HashMap<String, TProcessor>();

    protected int serverPort;

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setProcessors(Map<String, TProcessor> processors) {
        this.processors = processors;
    }

    public void start() {
        assertStateNotEmpty(processors, "LasServer processors must be initialized. " +
                "Please check the bean named bootServer in las-server.xml");

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("The count of processor is [" + processors.size() + "]");
            for (Map.Entry<String, TProcessor> processor : processors.entrySet()) {
                LOGGER.info(processor.getKey());
            }
        }

        // Create the processor
        TCompositeProcessor compositeProcessor = new TCompositeProcessor();

        for (Map.Entry<String, TProcessor> processor : processors.entrySet()) {
            compositeProcessor.registerProcessor(processor.getKey(), processor.getValue());
        }
        startServer(compositeProcessor);
    }

    public abstract String getServerType();

    protected abstract void startServer(TCompositeProcessor compositeProcessor);
}
