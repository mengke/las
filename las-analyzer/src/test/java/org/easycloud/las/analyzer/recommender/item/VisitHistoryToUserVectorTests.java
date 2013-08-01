package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.MapReduceDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.easycloud.las.analyzer.housevisit.HouseVisitArrayWritable;
import org.easycloud.las.analyzer.housevisit.HouseVisitRecord;
import org.easycloud.las.analyzer.io.TextBytePair;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easycloud.las.analyzer.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 上午10:57
 */
public class VisitHistoryToUserVectorTests {

    private MapDriver<TextBytePair, HouseVisitArrayWritable, Text, Text> mapDriver;
    private ReduceDriver<Text, Text, Text, VectorWritable> reduceDriver;

    private MapReduceDriver<TextBytePair, HouseVisitArrayWritable, Text, Text, Text, VectorWritable> mapReduceDriver;

    private HouseVisitRecord record1, record2, record3;
    private HouseVisitArrayWritable houseVisitArray1, houseVisitArray2;
    private TextBytePair user1, user2;
    private String userCode1, userCode2;
    private String houseCode1, houseCode2, houseCode3;
    private byte houseType1, houseType2, houseType3;

    @Before
    public void setUp() throws Exception {
        VisitHistoryToItemPrefsMapper mapper = new VisitHistoryToItemPrefsMapper();
        mapDriver = MapDriver.newMapDriver(mapper);

        VisitHistoryToUserVectorReducer reducer = new VisitHistoryToUserVectorReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);

        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        houseCode1 = "BJFT85558816";
        houseType1 = HOUSE_TYPE_SELL;

        houseCode2 = "BJCP85062036";
        houseType2 = HOUSE_TYPE_RENT;

        houseCode3 = "BJFT84662905";
        houseType3 = HOUSE_TYPE_RENT;

        Date date1 = df.parse("2013-06-04 03:00:33");
        record1 = new HouseVisitRecord(houseCode1, houseType1, date1.getTime());
        Date date2 = df.parse("2013-06-04 03:00:41");
        record2 = new HouseVisitRecord(houseCode2, houseType2, date2.getTime());
        Date date3 = df.parse("2013-06-04 03:01:10");
        record3 = new HouseVisitRecord(houseCode3, houseType3, date3.getTime());

        houseVisitArray1 = new HouseVisitArrayWritable(new HouseVisitRecord[]{record3, record1});
        houseVisitArray2 = new HouseVisitArrayWritable(new HouseVisitRecord[]{record2});

        userCode1 = "6e2b58d1ed29f167976f9c88c835951d";
        userCode2 = "21a5a5dcde0ee2805d781591e5cc2e79";

        user1 = new TextBytePair(userCode1, USER_TYPE_LOGIN);
        user2 = new TextBytePair(userCode2, USER_TYPE_ANONYMOUS);

    }

    @Test
    public void testMapper() throws IOException {
        mapDriver.withInput(user1, houseVisitArray1);
        mapDriver.addOutput(new Text(userCode1), new Text(houseCode3 + "/" + houseType3));
        mapDriver.addOutput(new Text(userCode1), new Text(houseCode1 + "/" + houseType1));
        mapDriver.runTest(false);
    }

    @Test
    public void testReducer() throws IOException {
        List<Text> values = new ArrayList<Text>();
        values.add(new Text(houseCode3 + "/" + houseType3));
        values.add(new Text(houseCode1 + "/" + houseType1));
        reduceDriver.addInput(new Text(userCode1), values);
        RandomAccessVector<String> userVector = new RandomAccessVector<String>(2);
        userVector.set(houseCode3 + "/" + houseType3, 1);
        userVector.set(houseCode1 + "/" + houseType1, 1);
        reduceDriver.addOutput(new Text(userCode1), new VectorWritable(userVector));
        reduceDriver.runTest();
    }

    @Test
    public void testMapReduce() throws IOException {
        mapReduceDriver.withInput(user1, houseVisitArray1);
        mapReduceDriver.withInput(user2, houseVisitArray2);
        RandomAccessVector<String> userVector1 = new RandomAccessVector<String>(2);
        userVector1.set(houseCode3 + "/" + houseType3, 1);
        userVector1.set(houseCode1 + "/" + houseType1, 1);
        RandomAccessVector<String> userVector2 = new RandomAccessVector<String>(1);
        userVector2.set(houseCode2 + "/" + houseType2, 1);
        mapReduceDriver.withOutput(new Text(userCode1), new VectorWritable(userVector1));
        mapReduceDriver.withOutput(new Text(userCode2), new VectorWritable(userVector2));

        mapReduceDriver.runTest(false);
    }
}
