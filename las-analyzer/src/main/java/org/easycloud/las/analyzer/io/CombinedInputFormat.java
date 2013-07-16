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

package org.easycloud.las.analyzer.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.CombineFileInputFormat;
import org.apache.hadoop.mapred.lib.CombineFileRecordReader;
import org.apache.hadoop.mapred.lib.CombineFileSplit;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-25
 * Time: 上午11:16
 */
public class CombinedInputFormat extends CombineFileInputFormat<LongWritable, Text> {

    @SuppressWarnings("unchecked")
    @Override
    public RecordReader<LongWritable, Text> getRecordReader(InputSplit split, JobConf conf, Reporter reporter) throws IOException {
        reporter.setStatus(split.toString());
        return new CombineFileRecordReader(conf, (CombineFileSplit) split, reporter, DefaultCombineFileRecordReader.class);
    }

    public static class DefaultCombineFileRecordReader implements RecordReader<LongWritable, Text> {
        private final LineRecordReader lineReader;

        public DefaultCombineFileRecordReader(CombineFileSplit split, Configuration conf, Reporter reporter, Integer index) throws IOException {
            FileSplit filesplit = new FileSplit(split.getPath(index), split.getOffset(index), split.getLength(index), split.getLocations());
            lineReader = new LineRecordReader(conf, filesplit);
        }

        @Override
        public void close() throws IOException {
            lineReader.close();

        }

        @Override
        public LongWritable createKey() {
            return lineReader.createKey();
        }

        @Override
        public Text createValue() {
            return lineReader.createValue();
        }

        @Override
        public long getPos() throws IOException {
            return lineReader.getPos();
        }

        @Override
        public float getProgress() throws IOException {
            return lineReader.getProgress();
        }

        @Override
        public boolean next(LongWritable key, Text value) throws IOException {
            return lineReader.next(key, value);
        }

    }
}