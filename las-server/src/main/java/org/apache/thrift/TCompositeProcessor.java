package org.apache.thrift;

import org.apache.thrift.protocol.TFilterProtocol;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.apache.thrift.protocol.TCompositeProtocol.SERVICE_METHOD_SEPARATOR;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-8
 * Time: 下午4:03
 */
public class TCompositeProcessor implements TProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TCompositeProcessor.class);

    private final Map<String, TProcessor> delegates = new HashMap<String, TProcessor>();

    public void registerProcessor(String serviceName, TProcessor processor) {
        assert serviceName != null && serviceName.trim().length() > 0;
        assert processor != null;
        delegates.put(serviceName, processor);
    }

    @Override
    public boolean process(TProtocol in, TProtocol out) throws TException {
        TMessage message = in.readMessageBegin();
        String messageName = message.name;
        int sepIndex = messageName.indexOf(SERVICE_METHOD_SEPARATOR);
        if (sepIndex < 0) {
            String errorMessage = "TCompositeProcessor: service name not found in the message name[" + messageName + "]";
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(errorMessage);
            }
            throw new TException(errorMessage);
        }
        String serviceName = messageName.substring(0, sepIndex);
        String methodName = messageName.substring(serviceName.length() + SERVICE_METHOD_SEPARATOR.length());
        TProcessor processor = delegates.get(serviceName);
        if (processor == null) {
            String errorMessage = "TCompositeProcessor: service name[" + messageName + "] not registered. Check if the method registerProcessor's called.";
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(errorMessage);
            }
            throw new TException(errorMessage);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("TCompositeProcessor: processing " + serviceName + ":" + methodName);
        }
        TMessage processMessage = new TMessage(methodName, message.type, message.seqid);

        return processor.process(new TProtocolWithMessageBegin(in, processMessage), out);
    }

    private class TProtocolWithMessageBegin extends TFilterProtocol {

        private final TMessage messageBegin;

        public TProtocolWithMessageBegin(TProtocol tProtocol, TMessage messageBegin) {
            super(tProtocol);
            this.messageBegin = messageBegin;
        }

        @Override
        public TMessage readMessageBegin() throws TException {
            return messageBegin;
        }
    }
}
