package org.easycloud.las.server;

import org.apache.thrift.TCompositeProcessor;
import org.apache.thrift.TProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-10
 * Time: 上午9:49
 */
public abstract class LasServer {

    protected Map<String, TProcessor> processors = new HashMap<String, TProcessor>();

    protected int serverPort;

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setProcessors(Map<String, TProcessor> processors) {
        this.processors = processors;
    }

    public void start() {
        // Create the processor
        TCompositeProcessor compositeProcessor = new TCompositeProcessor();

        for (Map.Entry<String, TProcessor> processor : processors.entrySet()) {
            compositeProcessor.registerProcessor(processor.getKey(), processor.getValue());
        }
        startServer(compositeProcessor);
    }

    protected abstract void startServer(TCompositeProcessor compositeProcessor);
}
