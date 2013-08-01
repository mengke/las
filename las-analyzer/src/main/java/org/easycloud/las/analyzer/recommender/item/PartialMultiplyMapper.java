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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 上午9:26
 */
public class PartialMultiplyMapper extends MapReduceBase
        implements Mapper<Text, VectorAndPrefsWritable, Text, VectorWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartialMultiplyMapper.class);

    @Override
    public void map(Text key, VectorAndPrefsWritable value,
                    OutputCollector<Text, VectorWritable> collector, Reporter reporter) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("PartialMultiplyMapper: processing the key [ " + key + "]");
        }
        RandomAccessVector<String> cooccurrenceColumn = value.getVector();
        List<String> userIDs = value.getUserIDs();
        List<Integer> prefValues = value.getValues();
        VectorWritable outputValue = new VectorWritable();

        for (int i = 0; i < userIDs.size(); i++) {
            String userID = userIDs.get(i);
            int prefValue = prefValues.get(i);
            if (prefValue > 0) {
                RandomAccessVector<String> partialProduct = cooccurrenceColumn.times(prefValue);
                outputValue.set(partialProduct);
                collector.collect(new Text(userID), outputValue);
            }
        }
    }
}
