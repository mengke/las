package org.easycloud.las.analyzer.housevisit;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

import static org.easycloud.las.analyzer.util.CompareUtil.compare;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-6-17
 */
public class HouseVisitEntry implements WritableComparable<HouseVisitEntry> {

    private Text userId;
    private byte userType;
    private long visitDttm;
    private Text houseCode;
    private byte houseType;

    public HouseVisitEntry() {
        set(new Text(), (byte) 0, 0l, new Text(), (byte) 0);
    }

    public HouseVisitEntry(String userId, byte userType, long visitDttm, String houseCode, byte houseType) {
        set(new Text(userId), userType, visitDttm, new Text(houseCode), houseType);
    }

    public void set(Text userId, byte userType, long visitDttm, Text houseCode, byte houseType) {
        this.userId = userId;
        this.userType = userType;
        this.visitDttm = visitDttm;
        this.houseCode = houseCode;
        this.houseType = houseType;
    }

    public Text getUserId() {
        return userId;
    }

    public byte getUserType() {
        return userType;
    }

    public long getVisitDttm() {
        return visitDttm;
    }

    public Text getHouseCode() {
        return houseCode;
    }

    public byte getHouseType() {
        return houseType;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        userId.write(dataOutput);
        dataOutput.writeByte(userType);
        dataOutput.writeLong(visitDttm);
        houseCode.write(dataOutput);
        dataOutput.writeByte(houseType);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        userId.readFields(dataInput);
        userType = dataInput.readByte();
        visitDttm = dataInput.readLong();
        houseCode.readFields(dataInput);
        houseType = dataInput.readByte();
    }

    @Override
    public int compareTo(HouseVisitEntry o) {
        int cmp = userId.compareTo(o.getUserId());
        if (cmp != 0) {
            return cmp;
        }
        cmp = compare(userType, o.userType);
        if (cmp != 0) {
            return cmp;
        }
        cmp = compare(visitDttm, o.visitDttm);
        if (cmp != 0) {
            return cmp;
        }
        cmp = houseCode.compareTo(o.getHouseCode());
        if (cmp != 0) {
            return cmp;
        }
        return compare(houseType, o.houseType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseVisitEntry that = (HouseVisitEntry) o;

        if (userType != that.userType) return false;
        if (visitDttm != that.visitDttm) return false;
        if (!houseCode.equals(that.houseCode)) return false;
        if (!userId.equals(that.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + (int) userType;
        result = 31 * result + (int) (visitDttm ^ (visitDttm >>> 32));
        result = 31 * result + houseCode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{userId:" + userId + ", userType:" + userType +
                ", houseCode:" + houseCode + ", houseType:" + houseType + ", visitDttm:" + new Date(visitDttm) + "}";
    }

}
