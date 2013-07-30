package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.MapReduceDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easycloud.las.analyzer.Constants.HOUSE_TYPE_RENT;
import static org.easycloud.las.analyzer.Constants.HOUSE_TYPE_SELL;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 上午11:41
 */
public class UserVectorToCooccurrenceTests {

    private MapDriver<Text, VectorWritable, Text, Text> mapDriver;
    private ReduceDriver<Text, Text, Text, VectorWritable> reduceDriver;

    private MapReduceDriver<Text, VectorWritable, Text, Text, Text, VectorWritable> mapReduceDriver;

    private String userCode1, userCode2;
    private String houseCode1, houseCode2, houseCode3;
    private byte houseType1, houseType2, houseType3;

    @Before
    public void setUp() throws Exception {
        UserVectorToCooccurrenceMapper mapper = new UserVectorToCooccurrenceMapper();
        mapDriver = MapDriver.newMapDriver(mapper);

        UserVectorToCooccurrenceReducer reducer = new UserVectorToCooccurrenceReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);

        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);

        userCode1 = "6e2b58d1ed29f167976f9c88c835951d";
        userCode2 = "21a5a5dcde0ee2805d781591e5cc2e79";

        houseCode1 = "BJFT85558816";
        houseType1 = HOUSE_TYPE_SELL;

        houseCode2 = "BJCP85062036";
        houseType2 = HOUSE_TYPE_RENT;

        houseCode3 = "BJFT84662905";
        houseType3 = HOUSE_TYPE_RENT;
    }

    @Test
    public void testMapper() throws Exception {
        RandomAccessVector<String> userVector = new RandomAccessVector<String>(2);
        String house1 = houseCode1 + "/" + houseType1;
        String house3 = houseCode3 + "/" + houseType3;
        userVector.set(house1, 1.0);
        userVector.set(house3, 1.0);
        mapDriver.withInput(new Text(userCode1), new VectorWritable(userVector));
        mapDriver.addOutput(new Text(house1), new Text(house1));
        mapDriver.addOutput(new Text(house1), new Text(house3));
        mapDriver.addOutput(new Text(house3), new Text(house1));
        mapDriver.addOutput(new Text(house3), new Text(house3));

        mapDriver.runTest(false);
    }

    @Test
    public void testReducer() throws Exception {
        List<Text> inputValue = new ArrayList<Text>();
        String house1 = houseCode1 + "/" + houseType1;
        String house3 = houseCode3 + "/" + houseType3;
        inputValue.add(new Text(house1));
        inputValue.add(new Text(house3));
        inputValue.add(new Text(house3));
        reduceDriver.withInput(new Text(house1), inputValue);
        RandomAccessVector<String> outputValue = new RandomAccessVector<String>(2);
        outputValue.set(house1, 1.0);
        outputValue.set(house3, 2.0);
        reduceDriver.withOutput(new Text(house1), new VectorWritable(outputValue));

        reduceDriver.runTest();
    }

    @Test
    public void testMapReduce() throws Exception {

        String house1 = houseCode1 + "/" + houseType1;
        String house2 = houseCode2 + "/" + houseType2;
        String house3 = houseCode3 + "/" + houseType3;

        RandomAccessVector<String> userVector1 = new RandomAccessVector<String>(3);
        userVector1.set(house1, 1.0);
        userVector1.set(house2, 1.0);
        userVector1.set(house3, 1.0);

        RandomAccessVector<String> userVector2 = new RandomAccessVector<String>(1);
        userVector2.set(house2, 1.0);
        userVector2.set(house3, 1.0);

        mapReduceDriver.addInput(new Text(userCode1), new VectorWritable(userVector1));
        mapReduceDriver.addInput(new Text(userCode2), new VectorWritable(userVector2));

        RandomAccessVector<String> cooccurrenceRow1 = new RandomAccessVector<String>();
        cooccurrenceRow1.set(house1, 1.0);
        cooccurrenceRow1.set(house2, 1.0);
        cooccurrenceRow1.set(house3, 1.0);

        RandomAccessVector<String> cooccurrenceRow2 = new RandomAccessVector<String>();
        cooccurrenceRow2.set(house1, 1.0);
        cooccurrenceRow2.set(house2, 2.0);
        cooccurrenceRow2.set(house3, 2.0);

        RandomAccessVector<String> cooccurrenceRow3 = new RandomAccessVector<String>();
        cooccurrenceRow3.set(house1, 1.0);
        cooccurrenceRow3.set(house2, 2.0);
        cooccurrenceRow3.set(house3, 2.0);

        mapReduceDriver.addOutput(new Text(house1), new VectorWritable(cooccurrenceRow1));
        mapReduceDriver.addOutput(new Text(house2), new VectorWritable(cooccurrenceRow2));
        mapReduceDriver.addOutput(new Text(house3), new VectorWritable(cooccurrenceRow3));

        mapReduceDriver.runTest(false);

    }
}
