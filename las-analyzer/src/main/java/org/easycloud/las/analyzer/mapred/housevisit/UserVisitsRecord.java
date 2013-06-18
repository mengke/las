package org.easycloud.las.analyzer.mapred.housevisit;

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

	private Text userId;
	private ByteWritable userType;

	private HouseVisitRecordArrayWritable houseVisitRecords;

	public UserVisitsRecord() {
		set(new Text(), new ByteWritable(), new HouseVisitRecordArrayWritable());
	}

	public UserVisitsRecord(String userId, byte userType, HouseVisitRecord[] houseVisitRecords) {
		set(new Text(userId), new ByteWritable(userType), new HouseVisitRecordArrayWritable(houseVisitRecords));
	}

	public void set(Text userId, ByteWritable userType, HouseVisitRecordArrayWritable houseVisitRecords) {
		this.userId = userId;
		this.userType = userType;
		this.houseVisitRecords = houseVisitRecords;
	}

	public Text getUserId() {
		return userId;
	}

	public ByteWritable getUserType() {
		return userType;
	}

	public HouseVisitRecordArrayWritable getHouseVisitRecords() {
		return houseVisitRecords;
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		userId.write(dataOutput);
		userType.write(dataOutput);
		houseVisitRecords.write(dataOutput);
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		userId.readFields(dataInput);
		userType.readFields(dataInput);
		houseVisitRecords.readFields(dataInput);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserVisitsRecord)) return false;

		UserVisitsRecord that = (UserVisitsRecord) o;

		if (!houseVisitRecords.equals(that.houseVisitRecords)) return false;
		if (!userId.equals(that.userId)) return false;
		if (!userType.equals(that.userType)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId.hashCode();
		result = 31 * result + userType.hashCode();
		result = 31 * result + houseVisitRecords.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "UserVisitsRecord{" +
						"userId=" + userId +
						", userType=" + userType +
						", houseVisitRecords=" + houseVisitRecords +
						'}';
	}

	public static class HouseVisitRecord implements Writable {

		private Text houseCode;
		private ByteWritable houseType;
		private LongWritable visitDttm;

		public HouseVisitRecord() {
			set(new Text(), new ByteWritable(), new LongWritable());
		}

		public HouseVisitRecord(String houseCode, byte houseType, long visitDttm) {
			set(new Text(houseCode), new ByteWritable(houseType), new LongWritable(visitDttm));
		}

		public HouseVisitRecord(Text houseCode, ByteWritable houseType, LongWritable visitDttm) {
			set(houseCode, houseType, visitDttm);
		}

		public void set(Text houseCode, ByteWritable houseType, LongWritable visitDttm) {
			this.houseCode = houseCode;
			this.houseType = houseType;
			this.visitDttm = visitDttm;
		}

		public Text getHouseCode() {
			return houseCode;
		}

		public ByteWritable getHouseType() {
			return houseType;
		}

		public LongWritable getVisitDttm() {
			return visitDttm;
		}

		@Override
		public void write(DataOutput dataOutput) throws IOException {
			houseCode.write(dataOutput);
			houseType.write(dataOutput);
			visitDttm.write(dataOutput);
		}

		@Override
		public void readFields(DataInput dataInput) throws IOException {
			houseCode.readFields(dataInput);
			houseType.readFields(dataInput);
			visitDttm.readFields(dataInput);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof HouseVisitRecord)) return false;

			HouseVisitRecord that = (HouseVisitRecord) o;

			if (!houseCode.equals(that.houseCode)) return false;
			if (!houseType.equals(that.houseType)) return false;
			if (!visitDttm.equals(that.visitDttm)) return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = houseCode.hashCode();
			result = 31 * result + houseType.hashCode();
			result = 31 * result + visitDttm.hashCode();
			return result;
		}

		@Override
		public String toString() {
			return "HouseVisitRecord{" +
							"houseCode=" + houseCode +
							", houseType=" + houseType +
							", visitDttm=" + visitDttm +
							'}';
		}
	}

	private class HouseVisitRecordArrayWritable extends ArrayWritable {
		public HouseVisitRecordArrayWritable() {
			super(HouseVisitRecord.class);
		}

		private HouseVisitRecordArrayWritable(Writable[] houseVisitRecords) {
			super(HouseVisitRecord.class, houseVisitRecords);
		}

		@Override
		public int hashCode() {
			int result = 0;
			for (HouseVisitRecord record : (HouseVisitRecord[]) this.toArray()) {
				result = 31 * result + record.houseCode.hashCode();
				result = 31 * result + record.houseType.hashCode();
				result = 31 * result + record.visitDttm.hashCode();
			}
			return result;
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) return true;
			if (!(other instanceof HouseVisitRecordArrayWritable)) return false;

			HouseVisitRecordArrayWritable that = (HouseVisitRecordArrayWritable) other;
			HouseVisitRecord[] thisArray = (HouseVisitRecord[]) this.toArray();
			HouseVisitRecord[] thatArray = (HouseVisitRecord[]) that.toArray();
			if (thisArray.length != thatArray.length) return false;

			for (int i = 0; i < thisArray.length; i++) {
				HouseVisitRecord thisElement = thisArray[i];
				HouseVisitRecord thatElement = thatArray[i];
				if (thisElement.equals(thatElement)) return false;
			}
			return true;
		}

		@Override
		public String toString() {
			HouseVisitRecord[] records = (HouseVisitRecord[]) this.toArray();
			StringBuffer sb = new StringBuffer("[");
			for (HouseVisitRecord record : records) {
				sb.append("{").append(record.getHouseCode()).append(", ");
				sb.append(record.getHouseType()).append(", ").append(record.getVisitDttm()).append("},");
			}
			sb.substring(0, sb.length() - 1);
			sb.append("]");
			return sb.toString();
		}
	}
}
