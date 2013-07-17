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

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-8
 * Time: 下午4:27
 */
public abstract class TFilterProtocol extends TProtocol {

    private final TProtocol delegate;

    public TFilterProtocol(TProtocol tProtocol) {
        super(tProtocol.getTransport());
        this.delegate = tProtocol;
    }

    @Override
    public void writeMessageBegin(TMessage tMessage) throws TException {
        delegate.writeMessageBegin(tMessage);
    }

    @Override
    public void writeMessageEnd() throws TException {
        delegate.writeMessageEnd();
    }

    @Override
    public void writeStructBegin(TStruct tStruct) throws TException {
        delegate.writeStructBegin(tStruct);
    }

    @Override
    public void writeStructEnd() throws TException {
        delegate.writeStructEnd();
    }

    @Override
    public void writeFieldBegin(TField tField) throws TException {
        delegate.writeFieldBegin(tField);
    }

    @Override
    public void writeFieldEnd() throws TException {
        delegate.writeFieldEnd();
    }

    @Override
    public void writeFieldStop() throws TException {
        delegate.writeFieldStop();
    }

    @Override
    public void writeMapBegin(TMap tMap) throws TException {
        delegate.writeMapBegin(tMap);
    }

    @Override
    public void writeMapEnd() throws TException {
        delegate.writeMapEnd();
    }

    @Override
    public void writeListBegin(TList tList) throws TException {
        delegate.writeListBegin(tList);
    }

    @Override
    public void writeListEnd() throws TException {
        delegate.writeListEnd();
    }

    @Override
    public void writeSetBegin(TSet tSet) throws TException {
        delegate.writeSetBegin(tSet);
    }

    @Override
    public void writeSetEnd() throws TException {
        delegate.writeSetEnd();
    }

    @Override
    public void writeBool(boolean b) throws TException {
        delegate.writeBool(b);
    }

    @Override
    public void writeByte(byte b) throws TException {
        delegate.writeByte(b);
    }

    @Override
    public void writeI16(short i) throws TException {
        delegate.writeI16(i);
    }

    @Override
    public void writeI32(int i) throws TException {
        delegate.writeI32(i);
    }

    @Override
    public void writeI64(long l) throws TException {
        delegate.writeI64(l);
    }

    @Override
    public void writeDouble(double v) throws TException {
        delegate.writeDouble(v);
    }

    @Override
    public void writeString(String s) throws TException {
        delegate.writeString(s);
    }

    @Override
    public void writeBinary(ByteBuffer byteBuffer) throws TException {
        delegate.writeBinary(byteBuffer);
    }

    @Override
    public TMessage readMessageBegin() throws TException {
        return delegate.readMessageBegin();
    }

    @Override
    public void readMessageEnd() throws TException {
        delegate.readMessageEnd();
    }

    @Override
    public TStruct readStructBegin() throws TException {
        return delegate.readStructBegin();
    }

    @Override
    public void readStructEnd() throws TException {
        delegate.readStructEnd();
    }

    @Override
    public TField readFieldBegin() throws TException {
        return delegate.readFieldBegin();
    }

    @Override
    public void readFieldEnd() throws TException {
        delegate.readFieldEnd();
    }

    @Override
    public TMap readMapBegin() throws TException {
        return delegate.readMapBegin();
    }

    @Override
    public void readMapEnd() throws TException {
        delegate.readMapEnd();
    }

    @Override
    public TList readListBegin() throws TException {
        return delegate.readListBegin();
    }

    @Override
    public void readListEnd() throws TException {
        delegate.readListEnd();
    }

    @Override
    public TSet readSetBegin() throws TException {
        return delegate.readSetBegin();
    }

    @Override
    public void readSetEnd() throws TException {
        delegate.readSetEnd();
    }

    @Override
    public boolean readBool() throws TException {
        return delegate.readBool();
    }

    @Override
    public byte readByte() throws TException {
        return delegate.readByte();
    }

    @Override
    public short readI16() throws TException {
        return delegate.readI16();
    }

    @Override
    public int readI32() throws TException {
        return delegate.readI32();
    }

    @Override
    public long readI64() throws TException {
        return delegate.readI64();
    }

    @Override
    public double readDouble() throws TException {
        return delegate.readDouble();
    }

    @Override
    public String readString() throws TException {
        return delegate.readString();
    }

    @Override
    public ByteBuffer readBinary() throws TException {
        return delegate.readBinary();
    }
}
