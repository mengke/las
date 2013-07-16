package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.housevisit.HouseVisitParser;
import org.easycloud.las.analyzer.io.TextLongPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.easycloud.las.analyzer.Constants.LOG_PARSED;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-31
 */
public class HouseVisitingMapper extends MapReduceBase implements Mapper<LongWritable, Text, TextLongPair, HouseVisitEntry> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseVisitingMapper.class);

    @Override
    public void map(LongWritable longWritable, Text text, OutputCollector<TextLongPair, HouseVisitEntry> collector, Reporter reporter) throws IOException {
        HouseVisitParser parser = new HouseVisitParser();
        try {
            parser.parse(text);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("HouseVisitingMapper: the log record parsed. " + text);
            }
            if (parser.getParseStatus() == LOG_PARSED) {
                HouseVisitEntry userVisit = parser.getHouseVisitEntry();
                collector.collect(new TextLongPair(userVisit.getUserId().toString(),
                        userVisit.getVisitDttm()), userVisit);
            }
        } catch (Exception e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("An unknown error occurred when parsing the log record. The exception message follows.", e);
            }
        }
    }
}
