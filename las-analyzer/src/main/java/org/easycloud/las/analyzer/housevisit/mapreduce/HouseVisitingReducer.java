package org.easycloud.las.analyzer.housevisit.mapreduce;

import org.apache.hadoop.mapreduce.Reducer;
import org.easycloud.las.analyzer.housevisit.HouseVisitArrayWritable;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.housevisit.HouseVisitRecord;
import org.easycloud.las.analyzer.io.TextBytePair;
import org.easycloud.las.analyzer.io.TextLongPair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-21
 * Time: 上午11:25
 */
public class HouseVisitingReducer extends Reducer<TextLongPair, HouseVisitEntry,
        TextBytePair, HouseVisitArrayWritable> {


    @Override
    protected void reduce(TextLongPair key, Iterable<HouseVisitEntry> values, Context context)
            throws IOException, InterruptedException {
        List<HouseVisitRecord> houseVisitRecordList = new ArrayList<HouseVisitRecord>();

        byte userType = -1;
        for (HouseVisitEntry houseVisitEntry : values) {
            if (userType == -1) {
                userType = houseVisitEntry.getUserType();
            }
            houseVisitRecordList.add(new HouseVisitRecord(houseVisitEntry.getHouseCode().toString(),
                    houseVisitEntry.getHouseType(), houseVisitEntry.getVisitDttm()));
        }
        context.write(new TextBytePair(key.getText().toString(), userType),
                new HouseVisitArrayWritable(houseVisitRecordList.toArray(new HouseVisitRecord[houseVisitRecordList.size()])));
    }
}
