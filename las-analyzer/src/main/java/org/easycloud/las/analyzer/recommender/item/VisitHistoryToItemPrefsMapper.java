package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.easycloud.las.analyzer.housevisit.HouseVisitArrayWritable;
import org.easycloud.las.analyzer.housevisit.HouseVisitRecord;
import org.easycloud.las.analyzer.io.TextBytePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-26
 * Time: 下午3:01
 */
public class VisitHistoryToItemPrefsMapper extends MapReduceBase implements Mapper<TextBytePair, HouseVisitArrayWritable, Text, Text> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitHistoryToItemPrefsMapper.class);

    @Override
    public void map(TextBytePair userId, HouseVisitArrayWritable value,
                    OutputCollector<Text, Text> collector, Reporter reporter) throws IOException {
        Text outputKey = new Text(userId.getText());
        Text outputValue = new Text();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("VisitHistoryToItemPrefsMapper: processing the key [ " + userId + "]");
        }
        for (Writable writable : value.get()) {
            HouseVisitRecord record = (HouseVisitRecord) writable;
            outputValue.set(record.getHouseCode().toString() + "/" + record.getHouseType());
            collector.collect(outputKey, outputValue);
        }
    }
}
