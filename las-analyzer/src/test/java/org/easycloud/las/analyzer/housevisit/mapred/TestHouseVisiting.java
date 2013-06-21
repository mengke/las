package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.MapReduceDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.housevisit.HouseVisitRecord;
import org.easycloud.las.analyzer.housevisit.UserVisitsRecord;
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

    private MapReduceDriver<LongWritable, Text, Text, HouseVisitEntry, Text, UserVisitsRecord> mapReduceDriver;

	@Before
	public void setUp() {
		HouseVisitingMapper mapper = new HouseVisitingMapper();
		mapDriver = MapDriver.newMapDriver(mapper);

		HouseVisitingReducer reducer = new HouseVisitingReducer();
		reduceDriver = ReduceDriver.newReduceDriver(reducer);

        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
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
		long dttm1 = date.getTime() + 100l;
		HouseVisitEntry entry1 = new HouseVisitEntry("622ce3116a90b41e939dc1f1517eee72", USER_TYPE_LOGIN, dttm1, "BJCP85578551", HOUSE_TYPE_RENT);
		houseVisitEntryList.add(entry1);
		long dttm2 = date.getTime() + 10l;
		HouseVisitEntry entry2 = new HouseVisitEntry("622ce3116a90b41e939dc1f1517eee72", USER_TYPE_LOGIN, dttm2, "BJCP85578552", HOUSE_TYPE_SELL);
		houseVisitEntryList.add(entry2);
		long dttm3 = date.getTime() + 1000l;
		HouseVisitEntry entry3 = new HouseVisitEntry("622ce3116a90b41e939dc1f1517eee72", USER_TYPE_LOGIN, dttm3, "BJCP85578550", HOUSE_TYPE_SELL);
		houseVisitEntryList.add(entry3);
		reduceDriver.withInput(new Text("622ce3116a90b41e939dc1f1517eee72"), houseVisitEntryList);

        HouseVisitRecord record1 = new HouseVisitRecord("BJCP85578551", HOUSE_TYPE_RENT, dttm1);
        HouseVisitRecord record2 = new HouseVisitRecord("BJCP85578552", HOUSE_TYPE_SELL, dttm2);
		HouseVisitRecord record3 = new HouseVisitRecord("BJCP85578550", HOUSE_TYPE_SELL, dttm3);

		UserVisitsRecord record = new UserVisitsRecord(USER_TYPE_LOGIN, new HouseVisitRecord[] {record3, record1, record2});

		reduceDriver.withOutput(new Text("622ce3116a90b41e939dc1f1517eee72"), record);

		reduceDriver.runTest();
	}

    @Test
    public void testMapReduce() throws IOException, ParseException {
        mapReduceDriver.withInput(new LongWritable(123), new Text("114.249.54.41   2013-06-04 03:00:33" +
                "   7a601aadbb67c0a3739c14a59cd3d242" +
                "   http://beijing.homelink.com.cn/ershoufang/d3b130/p2pg2/" +
                "   http://beijing.homelink.com.cn/ershoufang/BJFT85558816.shtml" +
                "   6e2b58d1ed29f167976f9c88c835951d   $   $"));

        mapReduceDriver.withInput(new LongWritable(124), new Text("114.249.54.42   2013-06-04 03:00:41" +
                "   21a5a5dcde0ee2805d781591e5cc2e79   http://beijing.homelink.com.cn/zufang/BJCP85062036.shtml" +
                "   http://beijing.homelink.com.cn/zufang/BJCP85062036.shtml   $   $   $"));

        mapReduceDriver.withInput(new LongWritable(125), new Text("124.202.190.3   2013-06-04 03:01:02" +
                "   21a5a5dcde0ee2805d781591e5cc2e79   http://beijing.homelink.com.cn/ershoufang/pg2rs天下儒寓/" +
                "   http://beijing.homelink.com.cn/ershoufang/pg3rs天下儒寓/   $   $   $"));

        mapReduceDriver.withInput(new LongWritable(126), new Text("114.249.54.41   2013-06-04 03:01:10" +
                "   7a601aadbb67c0a3739c14a59cd3d242   http://beijing.homelink.com.cn/zufang/d3b130/p2pg2/" +
                "   http://beijing.homelink.com.cn/zufang/BJFT84662905.shtml   6e2b58d1ed29f167976f9c88c835951d   $   $"));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date1 = df.parse("2013-06-04 03:00:33");
        HouseVisitRecord record1 = new HouseVisitRecord("BJFT85558816", HOUSE_TYPE_SELL, date1.getTime());
        Date date2 = df.parse("2013-06-04 03:00:41");
        HouseVisitRecord record2 = new HouseVisitRecord("BJCP85062036", HOUSE_TYPE_RENT, date2.getTime());
        Date date3 = df.parse("2013-06-04 03:01:10");
        HouseVisitRecord record3 = new HouseVisitRecord("BJFT84662905", HOUSE_TYPE_RENT, date3.getTime());

        UserVisitsRecord userRecord1 = new UserVisitsRecord(USER_TYPE_LOGIN, new HouseVisitRecord[] {record3, record1});
        UserVisitsRecord userRecord2 = new UserVisitsRecord(USER_TYPE_ANONYMOUS, new HouseVisitRecord[] {record2});

        mapReduceDriver.addOutput(new Text("6e2b58d1ed29f167976f9c88c835951d"), userRecord1);
        mapReduceDriver.addOutput(new Text("21a5a5dcde0ee2805d781591e5cc2e79"), userRecord2);

        mapReduceDriver.runTest(false);

    }



}
