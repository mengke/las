<?php
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
 *
 * Created by JetBrains PhpStorm.
 * User: ibntab
 * Date: 13-7-8
 * Time: 下午3:32
 */
namespace Thrift\Protocol;

use Thrift\Protocol\TProtocol;
use Thrift\Factory\TStringFuncFactory;

//
abstract class TFilterProtocol extends TProtocol {

    private $delegate;

    public function __construct($delegate) {
        parent::__construct($delegate->getTransport());
        $this->delegate = $delegate;
    }
    /**
     * Writes the message header
     *
     * @param string $name Function name
     * @param int $type message type TMessageType::CALL or TMessageType::REPLY
     * @param int $seqid The sequence id of this message
     */
    public function writeMessageBegin($name, $type, $seqid)
    {
        $this->delegate->writeMessageBegin($name, $type, $seqid);
    }

    /**
     * Close the message
     */
    public function writeMessageEnd()
    {
        $this->delegate->writeMessageEnd();
    }

    /**
     * Writes a struct header.
     *
     * @param string $name Struct name
     * @throws TException on write error
     * @return int How many bytes written
     */
    public function writeStructBegin($name)
    {
        $this->delegate->writeStructBegin($name);
    }

    /**
     * Close a struct.
     *
     * @throws TException on write error
     * @return int How many bytes written
     */
    public function writeStructEnd()
    {
        $this->delegate->writeStructEnd();
    }

    public function writeFieldBegin($fieldName, $fieldType, $fieldId)
    {
        $this->delegate->writeFieldBegin($fieldName, $fieldType, $fieldId);
    }

    public function writeFieldEnd()
    {
        $this->delegate->writeFieldEnd();
    }

    public function writeFieldStop()
    {
        $this->delegate->writeFieldStop();
    }

    public function writeMapBegin($keyType, $valType, $size)
    {
        $this->delegate->writeMapBegin($keyType, $valType, $size);
    }

    public function writeMapEnd()
    {
        $this->delegate->writeMapEnd();
    }

    public function writeListBegin($elemType, $size)
    {
        $this->delegate->writeListBegin($elemType, $size);
    }

    public function writeListEnd()
    {
        $this->delegate->writeListEnd();
    }

    public function writeSetBegin($elemType, $size)
    {
        $this->delegate->writeSetBegin($elemType, $size);
    }

    public function writeSetEnd()
    {
        $this->delegate->writeSetEnd();
    }

    public function writeBool($bool)
    {
        $this->delegate->writeBool($bool);
    }

    public function writeByte($byte)
    {
        $this->delegate->writeByte($byte);
    }

    public function writeI16($i16)
    {
        $this->delegate->writeI16($i16);
    }

    public function writeI32($i32)
    {
        $this->delegate->writeI32($i32);
    }

    public function writeI64($i64)
    {
        $this->delegate->writeI64($i64);
    }

    public function writeDouble($dub)
    {
        $this->delegate->writeDouble($dub);
    }

    public function writeString($str)
    {
        $this->delegate->writeString($str);
    }

    /**
     * Reads the message header
     *
     * @param string $name Function name
     * @param int $type message type TMessageType::CALL or TMessageType::REPLY
     * @parem int $seqid The sequence id of this message
     */
    public function readMessageBegin(&$name, &$type, &$seqid)
    {
        $this->delegate->readMessageBegin($name, $type, $seqid);
    }

    /**
     * Read the close of message
     */
    public function readMessageEnd()
    {
        $this->delegate->readMessageEnd();
    }

    public function readStructBegin(&$name)
    {
        $this->delegate->readStructBegin($name);
    }

    public function readStructEnd()
    {
        $this->delegate->readStructEnd();
    }

    public function readFieldBegin(&$name, &$fieldType, &$fieldId)
    {
        $this->delegate->readFieldBegin($name, $fieldType, $fieldId);
    }

    public function readFieldEnd()
    {
        $this->delegate->readFieldEnd();
    }

    public function readMapBegin(&$keyType, &$valType, &$size)
    {
        $this->delegate->readMapBegin($keyType, $valType, $size);
    }

    public function readMapEnd()
    {
        $this->delegate->readMapEnd();
    }

    public function readListBegin(&$elemType, &$size)
    {
        $this->delegate->readListBegin($elemType, $size);
    }

    public function readListEnd()
    {
        $this->delegate->readListEnd();
    }

    public function readSetBegin(&$elemType, &$size)
    {
        $this->delegate->readSetBegin($elemType, $size);
    }

    public function readSetEnd()
    {
        $this->delegate->readSetEnd();
    }

    public function readBool(&$bool)
    {
        $this->delegate->readBool($bool);
    }

    public function readByte(&$byte)
    {
        $this->delegate->readByte($byte);
    }

    public function readI16(&$i16)
    {
        $this->delegate->readI16($i16);
    }

    public function readI32(&$i32)
    {
        $this->delegate->readI32($i32);
    }

    public function readI64(&$i64)
    {
        $this->delegate->readI64($i64);
    }

    public function readDouble(&$dub)
    {
        $this->delegate->readDouble($dub);
    }

    public function readString(&$str)
    {
        $this->delegate->readString($str);
    }
}