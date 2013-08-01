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

import com.facebook.nifty.core.NettyConfigBuilder;
import com.facebook.nifty.core.NettyServerTransport;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import org.apache.thrift.TCompositeProcessor;
import org.apache.thrift.TProcessor;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.util.HashedWheelTimer;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-9
 * Time: 下午5:28
 */
public class NiftyServer extends LasServer {

    @Override
    public String getServerType() {
        return "Nifty Server";
    }

    @Override
    public void startServer(TProcessor tProcessor) {
        // Build the server definition
        ThriftServerDef serverDef = new ThriftServerDefBuilder()
                .listen(serverPort)

                .withProcessor(tProcessor)
                .build();

        // Create the server transport
        final NettyServerTransport server = new NettyServerTransport(serverDef,
                new NettyConfigBuilder(),
                new DefaultChannelGroup(),
                new HashedWheelTimer());

        // Create netty boss and executor thread pools
        ExecutorService bossExecutor = Executors.newCachedThreadPool();
        ExecutorService workerExecutor = Executors.newCachedThreadPool();

        // Start the server
        server.start(bossExecutor, workerExecutor);

        // Arrange to stop the server at shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    server.stop();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
}
