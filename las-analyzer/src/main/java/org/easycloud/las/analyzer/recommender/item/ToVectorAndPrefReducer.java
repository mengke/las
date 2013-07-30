package org.easycloud.las.analyzer.recommender.item;

import com.google.common.collect.Lists;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.easycloud.las.analyzer.util.RandomAccessVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 上午8:54
 */
public class ToVectorAndPrefReducer extends MapReduceBase implements
        Reducer<Text,VectorOrPrefWritable,Text,VectorAndPrefsWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToVectorAndPrefReducer.class);

    @Override
    public void reduce(Text key, Iterator<VectorOrPrefWritable> values,
                       OutputCollector<Text, VectorAndPrefsWritable> collector, Reporter reporter) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("ToVectorAndPrefReducer: processing the key [ " + key + "]");
        }

        List<String> userIds = Lists.newArrayList();
        List<Double> prefValues = Lists.newArrayList();
        RandomAccessVector<String> similarityMatrixColumn = null;

        while (values.hasNext()) {
            VectorOrPrefWritable value = values.next();
            if (value.getVector() == null) {
                // Then this is a user-pref value
                userIds.add(value.getUserID());
                prefValues.add(value.getValue());
            } else {
                // Then this is the column vector
                if (similarityMatrixColumn != null) {
                    throw new IllegalStateException("Found two similarity-matrix columns for item index " + key);
                }
                similarityMatrixColumn = value.getVector();
            }
        }

        if (similarityMatrixColumn == null) {
            return;
        }
        VectorAndPrefsWritable vectorAndPrefs = new VectorAndPrefsWritable(similarityMatrixColumn, userIds, prefValues);
        collector.collect(key, vectorAndPrefs);
    }
}
