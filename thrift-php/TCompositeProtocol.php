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
use Thrift\Type\TMessageType;
use Thrift\Factory\TStringFuncFactory;

class TCompositeProtocol extends TFilterProtocol {

    const SERVICE_METHOD_SEPARATOR = ":";

    private $serviceName;

    function __construct($tProtocol, $serviceName)
    {
        parent::__construct($tProtocol);
        $this->serviceName = $serviceName;
    }

    public function writeMessageBegin($name, $type, $seqid)
    {
        if ($type == TMessageType::CALL || $type == TMessageType::ONEWAY) {
            parent::writeMessageBegin($this->serviceName.self::SERVICE_METHOD_SEPARATOR.$name, $type, $seqid);
        } else {
            parent::writeMessageBegin($name, $type, $seqid);
        }

    }


}