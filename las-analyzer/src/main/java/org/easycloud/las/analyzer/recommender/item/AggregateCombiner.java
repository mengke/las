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
 * Date: 13-7-30
 * Time: 上午9:45
 */
public class AggregateCombiner extends MapReduceBase implements
        Reducer<Text, VectorWritable, Text, VectorWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateCombiner.class);

    @Override
    public void reduce(Text userId, Iterator<VectorWritable> values,
                       OutputCollector<Text, VectorWritable> collector, Reporter reporter) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("AggregateCombiner: processing the key [ " + userId + "]");
        }
        RandomAccessVector<String> partial = null;
        while(values.hasNext()) {
            VectorWritable vectorWritable = values.next();
            partial = partial == null ? vectorWritable.get() : partial
                    .plus(vectorWritable.get());
        }
        collector.collect(userId, new VectorWritable(partial));
    }
}
