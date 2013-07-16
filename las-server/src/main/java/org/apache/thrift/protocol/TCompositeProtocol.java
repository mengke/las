package org.apache.thrift.protocol;

import org.apache.thrift.TException;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-8
 * Time: 下午4:36
 */
public class TCompositeProtocol extends TFilterProtocol {

    public static final String SERVICE_METHOD_SEPARATOR = ":";

    private final String serviceName;

    public TCompositeProtocol(TProtocol tProtocol, String serviceName) {
        super(tProtocol);
        this.serviceName = serviceName;
    }

    @Override
    public void writeMessageBegin(TMessage tMessage) throws TException {
        if (tMessage.type == TMessageType.CALL || tMessage.type == TMessageType.ONEWAY) {
            super.writeMessageBegin(new TMessage(
                    serviceName + SERVICE_METHOD_SEPARATOR + tMessage.name,
                    tMessage.type,
                    tMessage.seqid));
        } else {
            super.writeMessageBegin(tMessage);
        }
    }
}
