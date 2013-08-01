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
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-29
 * Time: 下午4:12
 */
public class UserVectorToCooccurrenceMapper extends MapReduceBase
        implements Mapper<Text, VectorWritable, Text, Text> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserVectorToCooccurrenceMapper.class);

    @Override
    public void map(Text userId, VectorWritable userVector,
                       OutputCollector<Text, Text> collector, Reporter reporter) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("UserVectorToCooccurrenceMapper: processing the key [ " + userId + "]");
        }
        Iterator<Map.Entry<String, Integer>> iter = userVector.get().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            if (entry.getValue() > 0) {
                String itemId1 = entry.getKey();
                Iterator<Map.Entry<String, Integer>> iter2 = userVector.get().iterator();
                while (iter2.hasNext()) {
                    Map.Entry<String, Integer> entry2 = iter2.next();
                    if (entry2.getValue() > 0) {
                        String itemId2 = entry2.getKey();
                        collector.collect(new Text(itemId1), new Text(itemId2));
                    }
                }
            }
        }
    }
}
