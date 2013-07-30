package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-29
 * Time: 下午4:50
 */
public class CooccurrenceColumnWrapperMapper extends MapReduceBase
        implements Mapper<Text, VectorWritable, Text, VectorOrPrefWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CooccurrenceColumnWrapperMapper.class);

    @Override
    public void map(Text key, VectorWritable value,
                    OutputCollector<Text, VectorOrPrefWritable> collector, Reporter reporter) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CooccurrenceColumnWrapperMapper: processing the key [ " + key + "]");
        }
        collector.collect(key, new VectorOrPrefWritable(value.get()));
    }
}
