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
        return new CombineFileRecordReader(conf, (CombineFileSplit) split, reporter, DefaultCombineFileRecordReader.class);
    }

    public static class DefaultCombineFileRecordReader implements RecordReader<LongWritable, Text> {
        private final LineRecordReader linerecord;

        public DefaultCombineFileRecordReader(CombineFileSplit split, Configuration conf, Reporter reporter, Integer index) throws IOException {
            FileSplit filesplit = new FileSplit(split.getPath(index), split.getOffset(index), split.getLength(index), split.getLocations());
            linerecord = new LineRecordReader(conf, filesplit);
        }

        @Override
        public void close() throws IOException {
            linerecord.close();

        }

        @Override
        public LongWritable createKey() {
            return linerecord.createKey();
        }

        @Override
        public Text createValue() {
            return linerecord.createValue();
        }

        @Override
        public long getPos() throws IOException {
            return linerecord.getPos();
        }

        @Override
        public float getProgress() throws IOException {
            return linerecord.getProgress();
        }

        @Override
        public boolean next(LongWritable key, Text value) throws IOException {
            return linerecord.next(key, value);
        }

    }
}