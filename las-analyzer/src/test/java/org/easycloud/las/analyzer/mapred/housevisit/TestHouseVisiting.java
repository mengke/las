package org.easycloud.las.analyzer.mapred.housevisit;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easycloud.las.analyzer.Constants.*;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-31
 */
public class TestHouseVisiting {

	private MapDriver<LongWritable, Text, Text, HouseVisitEntry> mapDriver;
	private ReduceDriver<Text, HouseVisitEntry, Text, UserVisitsRecord> reduceDriver;

	@Before
	public void setUp() {
		HouseVisitingMapper mapper = new HouseVisitingMapper();
		mapDriver = MapDriver.newMapDriver(mapper);

		HouseVisitingReducer reducer = new HouseVisitingReducer();
		reduceDriver = ReduceDriver.newReduceDriver(reducer);
	}

	@Test
	public void testMapperSellHouses() throws IOException, ParseException {
		Text value = new Text("222.131.16.2   2013-04-19 16:40:16   c303892ea3e318ebb75e656d7d82069a   $   " +
						"http://beijing.homelink.com.cn/ershoufang/BJHD85772437.shtml   $   20049371   $");
		mapDriver.withInput(new LongWritable(123), value);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = df.parse("2013-04-19 16:40:16");
		mapDriver.withOutput(new Text("c303892ea3e318ebb75e656d7d82069a"),
						new HouseVisitEntry("c303892ea3e318ebb75e656d7d82069a", USER_TYPE_ANONYMOUS, date.getTime(), "BJHD85772437", HOUSE_TYPE_SELL));
		mapDriver.runTest();
	}


	@Test
	public void testMapperRentHouses() throws IOException, ParseException {
		Text value = new Text("202.108.158.179   2013-05-06 02:00:55   1ec6b0d8764b5a9a2614dde9d94b51ef   http://beijing.homelink.com.cn/zufang/BJCP85578551.shtml   " +
						"http://beijing.homelink.com.cn/zufang/BJCP85578551.shtml   622ce3116a90b41e939dc1f1517eee72   $   $");
		mapDriver.withInput(new LongWritable(321), value);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = df.parse("2013-05-06 02:00:55");
		mapDriver.withOutput(new Text("622ce3116a90b41e939dc1f1517eee72"),
						new HouseVisitEntry("622ce3116a90b41e939dc1f1517eee72", USER_TYPE_LOGIN, date.getTime(), "BJCP85578551", HOUSE_TYPE_RENT));
		mapDriver.runTest();
	}

	@Test
	public void testMapperWithNoOutput() throws IOException {
		Text value = new Text("222.131.16.2   2013-04-19 16:40:16   c303892ea3e318ebb75e656d7d82069a" +
						"   $   http://beijing.homelink.com.cn/agent/comment_house.php?bd_id=&bbd_id=&community=&hid=bjft85644648" +
						"   $   20049371   $");
		mapDriver.withInput(new LongWritable(123), value);
		mapDriver.runTest();
	}

	@Test
	public void testReduce() throws IOException {
		List<HouseVisitEntry> houseVisitEntryList = new ArrayList<HouseVisitEntry>();
		Date date = new Date();
		long dttm1 = date.getTime() - 10l;
		HouseVisitEntry entry1 = new HouseVisitEntry("622ce3116a90b41e939dc1f1517eee72", USER_TYPE_LOGIN, dttm1, "BJCP85578551", HOUSE_TYPE_RENT);
		houseVisitEntryList.add(entry1);
		long dttm2 = date.getTime() - 100l;
		HouseVisitEntry entry2 = new HouseVisitEntry("622ce3116a90b41e939dc1f1517eee72", USER_TYPE_LOGIN, dttm2, "BJCP85578552", HOUSE_TYPE_SELL);
		houseVisitEntryList.add(entry2);
		long dttm3 = date.getTime() - 1000l;
		HouseVisitEntry entry3 = new HouseVisitEntry("622ce3116a90b41e939dc1f1517eee72", USER_TYPE_LOGIN, dttm3, "BJCP85578550", HOUSE_TYPE_SELL);
		houseVisitEntryList.add(entry3);
		reduceDriver.withInput(new Text("622ce3116a90b41e939dc1f1517eee72"), houseVisitEntryList);

		UserVisitsRecord.HouseVisitRecord record3 = new UserVisitsRecord.HouseVisitRecord("BJCP85578550", HOUSE_TYPE_SELL, dttm3);
		UserVisitsRecord.HouseVisitRecord record2 = new UserVisitsRecord.HouseVisitRecord("BJCP85578552", HOUSE_TYPE_SELL, dttm2);
		UserVisitsRecord.HouseVisitRecord record1 = new UserVisitsRecord.HouseVisitRecord("BJCP85578551", HOUSE_TYPE_RENT, dttm1);

		UserVisitsRecord record = new UserVisitsRecord("622ce3116a90b41e939dc1f1517eee72", USER_TYPE_LOGIN, new UserVisitsRecord.HouseVisitRecord[] {record3, record2, record1});

		reduceDriver.withOutput(new Text("622ce3116a90b41e939dc1f1517eee72"), record);

		reduceDriver.runTest();
	}



}
