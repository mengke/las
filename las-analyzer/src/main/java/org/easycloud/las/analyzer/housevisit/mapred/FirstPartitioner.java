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
 */

package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Partitioner;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.io.TextLongPair;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-26
 * Time: 上午10:52
 */
public class FirstPartitioner extends MapReduceBase implements Partitioner<TextLongPair, HouseVisitEntry> {

    @Override
    public int getPartition(TextLongPair key, HouseVisitEntry value, int numPartitions) {
        return Math.abs(key.getText().hashCode() * 127) % numPartitions;
    }
}
