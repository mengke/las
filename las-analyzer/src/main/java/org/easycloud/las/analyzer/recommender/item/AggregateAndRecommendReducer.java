package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.easycloud.las.analyzer.recommender.ByValueRecommendedItemComparator;
import org.easycloud.las.analyzer.recommender.RecommendedItem;
import org.easycloud.las.analyzer.recommender.RecommendedItemsWritable;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 上午9:56
 */
public class AggregateAndRecommendReducer extends MapReduceBase
        implements Reducer<Text, VectorWritable, Text, RecommendedItemsWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateAndRecommendReducer.class);

    private int recommendationsPerUser = 10;
    static final String NUM_RECOMMENDATIONS = "las.num.recommendations";
    static final int DEFAULT_NUM_RECOMMENDATIONS = 10;

    @Override
    public void configure(JobConf job) {
        super.configure(job);
        recommendationsPerUser = job.getInt(NUM_RECOMMENDATIONS,
                DEFAULT_NUM_RECOMMENDATIONS);
    }

    @Override
    public void reduce(Text userId, Iterator<VectorWritable> values,
                       OutputCollector<Text, RecommendedItemsWritable> collector, Reporter reporter) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("AggregateAndRecommendReducer: processing the key [ " + userId + "]");
        }

        List<RecommendedItem> recommendations = ItemRecommender.considerRecommendation(values, recommendationsPerUser);
        collector.collect(userId, new RecommendedItemsWritable(recommendations));
    }


}
