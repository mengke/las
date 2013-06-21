package org.easycloud.las.analyzer.housevisit;

import org.apache.hadoop.io.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-6-18
 */
public class UserVisitsRecord implements Writable {

	private byte userType;
	private HouseVisitArrayWritable houseVisitRecords;

	public UserVisitsRecord() {
		set((byte) 0, new HouseVisitArrayWritable());
	}

	public UserVisitsRecord(byte userType, HouseVisitRecord[] houseVisitRecords) {
		set(userType, new HouseVisitArrayWritable(houseVisitRecords));
	}

	public void set(byte userType, HouseVisitArrayWritable houseVisitRecords) {
		this.userType = userType;
		this.houseVisitRecords = houseVisitRecords;
	}

	public byte getUserType() {
		return userType;
	}

	public HouseVisitArrayWritable getHouseVisitRecords() {
		return houseVisitRecords;
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(userType);
		houseVisitRecords.write(dataOutput);
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		userType = dataInput.readByte();
		houseVisitRecords.readFields(dataInput);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserVisitsRecord that = (UserVisitsRecord) o;

        if (userType != that.userType) return false;
        if (houseVisitRecords != null ? !houseVisitRecords.equals(that.houseVisitRecords) : that.houseVisitRecords != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) userType;
        result = 31 * result + (houseVisitRecords != null ? houseVisitRecords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserVisitsRecord{" +
                "userType=" + userType +
                ", houseVisitRecords=" + houseVisitRecords +
                '}';
    }


}
