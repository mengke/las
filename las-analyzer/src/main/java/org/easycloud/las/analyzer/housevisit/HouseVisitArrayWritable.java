package org.easycloud.las.analyzer.housevisit;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Writable;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-21
 * Time: 上午11:49
 */
public class HouseVisitArrayWritable extends ArrayWritable {

    public HouseVisitArrayWritable() {
        super(HouseVisitRecord.class);
    }

    public HouseVisitArrayWritable(Writable[] houseVisitRecords) {
        super(HouseVisitRecord.class, houseVisitRecords);
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (HouseVisitRecord record : (HouseVisitRecord[]) this.toArray()) {
            result = 31 * result + record.getHouseCode().hashCode();
            result = 31 * result + record.getHouseType();
            result = 31 * result + (int) (record.getVisitDttm() ^ (record.getVisitDttm() >>> 32));
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof HouseVisitArrayWritable)) return false;

        HouseVisitArrayWritable that = (HouseVisitArrayWritable) other;
        HouseVisitRecord[] thisArray = (HouseVisitRecord[]) this.toArray();
        HouseVisitRecord[] thatArray = (HouseVisitRecord[]) that.toArray();
        if (thisArray.length != thatArray.length) return false;

        for (int i = 0; i < thisArray.length; i++) {
            HouseVisitRecord thisElement = thisArray[i];
            HouseVisitRecord thatElement = thatArray[i];
            if (!thisElement.equals(thatElement)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        HouseVisitRecord[] records = (HouseVisitRecord[]) this.toArray();
        return Arrays.toString(records);
    }
}
