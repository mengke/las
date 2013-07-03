package org.easycloud.las.analyzer.housevisit;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-21
 * Time: 上午11:35
 */
public class HouseVisitRecord implements Writable {

    private Text houseCode;
    private byte houseType;
    private long visitDttm;

    public HouseVisitRecord() {
        set(new Text(), (byte) 0, 0l);
    }

    public HouseVisitRecord(String houseCode, byte houseType, long visitDttm) {
        set(new Text(houseCode), houseType, visitDttm);
    }

    public void set(Text houseCode, byte houseType, long visitDttm) {
        this.houseCode = houseCode;
        this.houseType = houseType;
        this.visitDttm = visitDttm;
    }

    public Text getHouseCode() {
        return houseCode;
    }

    public byte getHouseType() {
        return houseType;
    }

    public long getVisitDttm() {
        return visitDttm;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        houseCode.write(dataOutput);
        dataOutput.writeByte(houseType);
        dataOutput.writeLong(visitDttm);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        houseCode.readFields(dataInput);
        houseType = dataInput.readByte();
        visitDttm = dataInput.readLong();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseVisitRecord that = (HouseVisitRecord) o;

        if (houseType != that.houseType) return false;
        if (visitDttm != that.visitDttm) return false;
        if (!houseCode.equals(that.houseCode)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = houseCode.hashCode();
        result = 31 * result + (int) houseType;
        result = 31 * result + (int) (visitDttm ^ (visitDttm >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return houseCode + "|" + houseType + "|" + visitDttm;
    }
}
