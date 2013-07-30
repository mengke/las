package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-26
 * Time: 下午3:28
 */
public class VisitHistoryToUserVectorReducer extends MapReduceBase implements Reducer<Text, Text, Text, VectorWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitHistoryToUserVectorReducer.class);

    @Override
    public void reduce(Text userId, Iterator<Text> itemIds,
                       OutputCollector<Text, VectorWritable> collector, Reporter reporter) throws IOException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("VisitHistoryToUserVectorReducer: processing the key [ " + userId + "]");
        }
        RandomAccessVector<String> userVector = new RandomAccessVector<String>(100);
        while (itemIds.hasNext()) {
            Text itemId = itemIds.next();
            userVector.set(itemId.toString(), 1.0f);
        }
        collector.collect(userId, new VectorWritable(userVector));
    }
}
