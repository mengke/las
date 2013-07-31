package org.easycloud.las.analyzer.recommender.item;

import com.mongodb.hadoop.io.MongoUpdateWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.bson.BasicBSONObject;
import org.easycloud.las.analyzer.recommender.RecommendedItem;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-31
 * Time: 下午1:36
 */
public class AggregateAndRecommendMongodbReducer extends MapReduceBase
        implements Reducer<Text, VectorWritable, NullWritable, MongoUpdateWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateAndRecommendMongodbReducer.class);

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
                       OutputCollector<NullWritable, MongoUpdateWritable> collector, Reporter reporter) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("AggregateAndRecommendMongodbReducer: processing the key [ " + userId + "]");
        }

        List<RecommendedItem> recommendationItems = ItemRecommender.considerRecommendation(values, recommendationsPerUser);
        BasicBSONObject query = new BasicBSONObject("_id", userId.toString());
        List<BasicBSONObject> recommendations = new ArrayList<BasicBSONObject>();
        for (RecommendedItem recommendedItem : recommendationItems) {
            BasicBSONObject recommendation = new BasicBSONObject("itemId", recommendedItem.getItemId())
                    .append("prefValue", recommendedItem.getPrefValue());
            recommendations.add(recommendation);
        }
        BasicBSONObject update = new BasicBSONObject("_id", userId.toString())
                .append("recommendations", recommendations);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Query Object is " + query);
            LOGGER.debug("Update Object is " + update);
        }

        collector.collect(null, new MongoUpdateWritable(query, update, true, false));

    }
}
