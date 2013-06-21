package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.housevisit.HouseVisitParser;

import java.io.IOException;

import static org.easycloud.las.analyzer.Constants.LOG_PARSED;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-31
 */
public class HouseVisitingMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, HouseVisitEntry> {

	@Override
	public void map(LongWritable longWritable, Text text, OutputCollector<Text, HouseVisitEntry> collector, Reporter reporter) throws IOException {
		HouseVisitParser parser = new HouseVisitParser();
		parser.parse(text);

		if (parser.getParseStatus() == LOG_PARSED) {
			HouseVisitEntry userVisit = parser.getHouseVisitEntry();
			collector.collect(userVisit.getUserId(), userVisit);
		}
	}
}
