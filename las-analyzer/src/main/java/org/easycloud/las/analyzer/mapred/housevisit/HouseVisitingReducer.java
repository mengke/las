package org.easycloud.las.analyzer.mapred.housevisit;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

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
		List<UserVisitsRecord.HouseVisitRecord> houseVisitRecordList = new ArrayList<UserVisitsRecord.HouseVisitRecord>();

		byte userType = -1;
		String userId = null;

		while (values.hasNext()) {
			HouseVisitEntry entry = values.next();
			if (userType == -1) {
				userType = entry.getUserType().get();
			}
			if (userId == null) {
				userId = entry.getUserId().toString();
			}
			UserVisitsRecord.HouseVisitRecord record = new UserVisitsRecord.HouseVisitRecord(entry.getHouseCode(),
							entry.getHouseType(), entry.getVisitDttm());
			houseVisitRecordList.add(record);
		}

		Collections.sort(houseVisitRecordList, new Comparator<UserVisitsRecord.HouseVisitRecord>() {
			@Override
			public int compare(UserVisitsRecord.HouseVisitRecord o1, UserVisitsRecord.HouseVisitRecord o2) {
				return o1.getVisitDttm().compareTo(o2.getVisitDttm());
			}
		});
		Collections.reverse(houseVisitRecordList);

		assert userType != -1;
		assert userId != null;
		UserVisitsRecord record = new UserVisitsRecord(userId, userType,
						houseVisitRecordList.toArray(new UserVisitsRecord.HouseVisitRecord[houseVisitRecordList.size()]));
		output.collect(key, record);
	}
}
