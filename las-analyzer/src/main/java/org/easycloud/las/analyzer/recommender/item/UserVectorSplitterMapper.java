package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-29
 * Time: 下午5:32
 */
public class UserVectorSplitterMapper extends MapReduceBase
        implements Mapper<Text, VectorWritable, Text, VectorOrPrefWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserVectorSplitterMapper.class);

    @Override
    public void map(Text key, VectorWritable value,
                    OutputCollector<Text, VectorOrPrefWritable> collector, Reporter reporter) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("UserVectorSplitterMapper: processing the key [ " + key + "]");
        }
        String userID = key.toString();
        RandomAccessVector<String> userVector = value.get();
        Iterator<Map.Entry<String, Double>> it = userVector.iterator();
        Text outputKey = new Text();
        while (it.hasNext()) {
            Map.Entry<String, Double> entry = it.next();
            String itemId = entry.getKey();
            double prefValue = entry.getValue();
            outputKey.set(itemId);
            collector.collect(outputKey,
                    new VectorOrPrefWritable(userID, prefValue));
        }
    }
}
