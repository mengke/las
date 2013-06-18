package org.easycloud.las.analyzer.mapred.housevisit;


import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-6-17
 */
public class HouseVisitEntry implements WritableComparable<HouseVisitEntry> {

	private Text userId;
	private ByteWritable userType;
	private LongWritable visitDttm;
	private Text houseCode;
	private ByteWritable houseType;

	public HouseVisitEntry() {
		set(new Text(), new ByteWritable(), new LongWritable(), new Text(), new ByteWritable());
	}

	public HouseVisitEntry(String userId, byte userType, long visitDttm, String houseCode, byte houseType) {
		set(new Text(userId), new ByteWritable(userType), new LongWritable(visitDttm), new Text(houseCode), new ByteWritable(houseType));
	}

	public void set(Text userId, ByteWritable userType, LongWritable visitDttm, Text houseCode, ByteWritable houseType) {
		this.userId = userId;
		this.userType = userType;
		this.visitDttm = visitDttm;
		this.houseCode = houseCode;
		this.houseType = houseType;
	}

	public Text getUserId() {
		return userId;
	}

	public void setUserId(Text userId) {
		this.userId = userId;
	}

	public ByteWritable getUserType() {
		return userType;
	}

	public void setUserType(ByteWritable userType) {
		this.userType = userType;
	}

	public LongWritable getVisitDttm() {
		return visitDttm;
	}

	public void setVisitDttm(LongWritable visitDttm) {
		this.visitDttm = visitDttm;
	}

	public Text getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = new Text(houseCode);
	}

	public ByteWritable getHouseType() {
		return houseType;
	}

	public void setHouseType(ByteWritable houseType) {
		this.houseType = houseType;
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		userId.write(dataOutput);
		userType.write(dataOutput);
		visitDttm.write(dataOutput);
		houseCode.write(dataOutput);
		houseType.write(dataOutput);
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		userId.readFields(dataInput);
		userType.readFields(dataInput);
		visitDttm.readFields(dataInput);
		houseCode.readFields(dataInput);
		houseType.readFields(dataInput);
	}

	@Override
	public int compareTo(HouseVisitEntry o) {
		int cmp = userId.compareTo(o.getUserId());
		if (cmp != 0) {
			return cmp;
		}
		cmp = userType.compareTo(o.getUserType());
		if (cmp != 0) {
			return cmp;
		}
		cmp = visitDttm.compareTo(o.getVisitDttm());
		if (cmp != 0) {
			return cmp;
		}
		cmp = houseCode.compareTo(o.getHouseCode());
		if (cmp != 0) {
			return cmp;
		}
		return houseType.compareTo(o.getHouseType());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof HouseVisitEntry)) return false;

		HouseVisitEntry that = (HouseVisitEntry) o;

		if (houseCode != null ? !houseCode.equals(that.houseCode) : that.houseCode != null) return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (userType != null ? !userType.equals(that.userType) : that.userType != null) return false;
		if (visitDttm != null ? !visitDttm.equals(that.visitDttm) : that.visitDttm != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		result = 31 * result + (userType != null ? userType.hashCode() : 0);
		result = 31 * result + (visitDttm != null ? visitDttm.hashCode() : 0);
		result = 31 * result + (houseCode != null ? houseCode.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "{userId:" + userId + ", userType:" + userType +
						", houseCode:" + houseCode + ", houseType:" + houseType + ", visitDttm:" + new Date(visitDttm.get()) + "}";
	}
}
