package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.housevisit.HouseVisitRecord;
import org.easycloud.las.analyzer.housevisit.UserVisitsRecord;
import org.easycloud.las.analyzer.util.CompareUtil;

import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-6-18
 *
 * TODO see MapReduce Features: Sorting
 */
public class HouseVisitingReducer extends MapReduceBase
				implements Reducer<Text, HouseVisitEntry, Text, UserVisitsRecord> {

	@Override
	public void reduce(Text key, Iterator<HouseVisitEntry> values,
										 OutputCollector<Text, UserVisitsRecord> output, Reporter reporter) throws IOException {
		List<HouseVisitRecord> houseVisitRecordList = new ArrayList<HouseVisitRecord>();

		byte userType = -1;

		while (values.hasNext()) {
			HouseVisitEntry entry = values.next();
			if (userType == -1) {
				userType = entry.getUserType();
			}
			HouseVisitRecord record = new HouseVisitRecord(entry.getHouseCode().toString(),
							entry.getHouseType(), entry.getVisitDttm());
			houseVisitRecordList.add(record);
		}

		Collections.sort(houseVisitRecordList, new Comparator<HouseVisitRecord>() {
			@Override
			public int compare(HouseVisitRecord o1, HouseVisitRecord o2) {
				return CompareUtil.compare(o1.getVisitDttm(), o2.getVisitDttm());
			}
		});
		Collections.reverse(houseVisitRecordList);

		assert userType != -1;
		UserVisitsRecord record = new UserVisitsRecord(userType,
						houseVisitRecordList.toArray(new HouseVisitRecord[houseVisitRecordList.size()]));
		output.collect(key, record);
	}
}
