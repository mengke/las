package org.easycloud.las.analyzer.housevisit.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.housevisit.HouseVisitParser;
import org.easycloud.las.analyzer.io.TextLongPair;

import java.io.IOException;

import static org.easycloud.las.analyzer.Constants.LOG_PARSED;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-21
 * Time: 上午10:12
 */
public class HouseVisitingMapper extends Mapper<LongWritable, Text, TextLongPair, HouseVisitEntry> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        HouseVisitParser parser = new HouseVisitParser();
        parser.parse(value);

        if (parser.getParseStatus() == LOG_PARSED) {
            HouseVisitEntry houseVisitEntry = parser.getHouseVisitEntry();
            context.write(new TextLongPair(houseVisitEntry.getUserId().toString(),
                    houseVisitEntry.getVisitDttm()), houseVisitEntry);
        }
    }
}
