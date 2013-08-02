package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.ReduceDriver;
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
 * Time: 下午2:14
 */
public class ToVectorAndPrefTests {

    private ReduceDriver<Text, VectorOrPrefWritable, Text, VectorAndPrefsWritable> reduceDriver;

    private String houseCode1, houseCode2, houseCode3;
    private byte houseType1, houseType2, houseType3;
    private String userCode1, userCode2;

    @Before
    public void setUp() throws Exception {
        ToVectorAndPrefReducer reducer = new ToVectorAndPrefReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);

        houseCode1 = "BJFT85558816";
        houseType1 = HOUSE_TYPE_SELL;

        houseCode2 = "BJCP85062036";
        houseType2 = HOUSE_TYPE_RENT;

        houseCode3 = "BJFT84662905";
        houseType3 = HOUSE_TYPE_RENT;

        userCode1 = "6e2b58d1ed29f167976f9c88c835951d";
        userCode2 = "21a5a5dcde0ee2805d781591e5cc2e79";
    }

    @Test
    public void testReducer() throws Exception {
        List<VectorOrPrefWritable> inputValues = new ArrayList<VectorOrPrefWritable>();

        String house1 = houseCode1 + "/" + houseType1;
        String house2 = houseCode2 + "/" + houseType2;
        String house3 = houseCode3 + "/" + houseType3;

        RandomAccessVector<String> cooccurrenceRow = new RandomAccessVector<String>();
        cooccurrenceRow.set(house1, 1);
        cooccurrenceRow.set(house2, 2);
        cooccurrenceRow.set(house3, 2);
        VectorOrPrefWritable input1 = new VectorOrPrefWritable(cooccurrenceRow);
        inputValues.add(input1);
        VectorOrPrefWritable input2 = new VectorOrPrefWritable(userCode1, 1);
        inputValues.add(input2);
        VectorOrPrefWritable input3 = new VectorOrPrefWritable(userCode2, 1);
        inputValues.add(input3);

        reduceDriver.withInput(new Text(house2), inputValues);

        List<String> userCodes = new ArrayList<String>();
        userCodes.add(userCode1);
        userCodes.add(userCode2);

        List<Integer> prefValues = new ArrayList<Integer>();
        prefValues.add(1);
        prefValues.add(1);
        VectorAndPrefsWritable outputValue = new VectorAndPrefsWritable(cooccurrenceRow, userCodes, prefValues);

        reduceDriver.withOutput(new Text(house2), outputValue);

        reduceDriver.runTest();
    }
}
