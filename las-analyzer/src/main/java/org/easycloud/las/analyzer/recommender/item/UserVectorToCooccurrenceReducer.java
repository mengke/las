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
 * Date: 13-7-29
 * Time: 下午4:29
 */
public class UserVectorToCooccurrenceReducer extends MapReduceBase
        implements Reducer<Text, Text, Text, VectorWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserVectorToCooccurrenceReducer.class);

    @Override
    public void reduce(Text itemId1, Iterator<Text> itemId2s,
                       OutputCollector<Text, VectorWritable> collector, Reporter reporter) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("UserVectorToCooccurrenceReducer: processing the key [ " + itemId1 + "]");
        }
        RandomAccessVector<String> cooccurrenceRow = new RandomAccessVector<String>(100);
        while (itemId2s.hasNext()) {
            String itemId2 = itemId2s.next().toString();
            cooccurrenceRow.set(itemId2,
                    cooccurrenceRow.get(itemId2) + 1);
        }
        collector.collect(itemId1, new VectorWritable(cooccurrenceRow));
    }
}
