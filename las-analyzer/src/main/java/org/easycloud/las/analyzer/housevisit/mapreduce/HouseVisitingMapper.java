package org.easycloud.las.analyzer.housevisit.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.housevisit.HouseVisitParser;
import org.easycloud.las.analyzer.io.TextLongPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.easycloud.las.analyzer.Constants.LOG_PARSED;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-21
 * Time: 上午10:12
 */
public class HouseVisitingMapper extends Mapper<LongWritable, Text, TextLongPair, HouseVisitEntry> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseVisitingMapper.class);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        HouseVisitParser parser = new HouseVisitParser();
        try {
            parser.parse(value);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("HouseVisitingMapper: the log record parsed. " + value);
            }
            if (parser.getParseStatus() == LOG_PARSED) {
                HouseVisitEntry houseVisitEntry = parser.getHouseVisitEntry();
                context.write(new TextLongPair(houseVisitEntry.getUserId().toString(),
                        houseVisitEntry.getVisitDttm()), houseVisitEntry);
            }
        } catch (Exception e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("An unknown error occurred when parsing the log record. The exception message follows.", e);
            }
        }


    }
}
