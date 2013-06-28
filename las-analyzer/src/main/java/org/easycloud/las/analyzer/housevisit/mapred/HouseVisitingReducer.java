package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.easycloud.las.analyzer.housevisit.HouseVisitArrayWritable;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.housevisit.HouseVisitRecord;
import org.easycloud.las.analyzer.io.TextBytePair;
import org.easycloud.las.analyzer.io.TextLongPair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-6-18
 *
 */
public class HouseVisitingReducer extends MapReduceBase
				implements Reducer<TextLongPair, HouseVisitEntry, TextBytePair, HouseVisitArrayWritable> {

	@Override
	public void reduce(TextLongPair key, Iterator<HouseVisitEntry> values,
										 OutputCollector<TextBytePair, HouseVisitArrayWritable> output, Reporter reporter) throws IOException {
		List<HouseVisitRecord> houseVisitRecordList = new ArrayList<HouseVisitRecord>();

		byte userType = -1;

		while (values.hasNext()) {
			HouseVisitEntry entry = values.next();
			if (userType == -1) {
				userType = entry.getUserType();
			}
            houseVisitRecordList.add(new HouseVisitRecord(entry.getHouseCode().toString(),
                    entry.getHouseType(), entry.getVisitDttm()));
		}

        output.collect(new TextBytePair(key.getText().toString(), userType),
                new HouseVisitArrayWritable(houseVisitRecordList.toArray(new HouseVisitRecord[houseVisitRecordList.size()])));
	}
}
