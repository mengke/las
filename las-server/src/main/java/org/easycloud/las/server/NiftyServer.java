package org.easycloud.las.server;

import com.facebook.nifty.core.NettyConfigBuilder;
import com.facebook.nifty.core.NettyServerTransport;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import org.apache.thrift.TCompositeProcessor;
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
public class NiftyServer extends LasServer implements Serializable {



    @Override
    public void startServer(TCompositeProcessor compositeProcessor) {
        // Build the server definition
        ThriftServerDef serverDef = new ThriftServerDefBuilder()
                .listen(serverPort)

                .withProcessor(compositeProcessor)
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
