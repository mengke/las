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
