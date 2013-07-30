package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.MapReduceDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.easycloud.las.analyzer.recommender.RecommendedItem;
import org.easycloud.las.analyzer.recommender.RecommendedItemsWritable;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.easycloud.las.analyzer.Constants.HOUSE_TYPE_RENT;
import static org.easycloud.las.analyzer.Constants.HOUSE_TYPE_SELL;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 下午2:28
 */
public class AggregateAndRecommendTests {

    private MapDriver<Text, VectorAndPrefsWritable, Text, VectorWritable> mapDriver;
    private ReduceDriver<Text, VectorWritable, Text, RecommendedItemsWritable> reduceDriver;

    private String houseCode1, houseCode2, houseCode3;
    private byte houseType1, houseType2, houseType3;
    private String userCode1, userCode2;

    @Before
    public void setUp() throws Exception {
        PartialMultiplyMapper mapper = new PartialMultiplyMapper();
        mapDriver = MapDriver.newMapDriver(mapper);

        AggregateAndRecommendReducer reducer = new AggregateAndRecommendReducer();
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
    public void testMapper() throws Exception {

        String house1 = houseCode1 + "/" + houseType1;
        String house2 = houseCode2 + "/" + houseType2;
        String house3 = houseCode3 + "/" + houseType3;

        RandomAccessVector<String> cooccurrenceColumn = new RandomAccessVector<String>();
        cooccurrenceColumn.set(house1, 1.0);
        cooccurrenceColumn.set(house2, 2.0);
        cooccurrenceColumn.set(house3, 2.0);
        List<String> userCodes = new ArrayList<String>();
        userCodes.add(userCode1);
        userCodes.add(userCode2);

        List<Double> prefValues = new ArrayList<Double>();
        prefValues.add(1.0);
        prefValues.add(1.0);
        VectorAndPrefsWritable inputValue = new VectorAndPrefsWritable(cooccurrenceColumn, userCodes, prefValues);
        mapDriver.withInput(new Text(house2), inputValue);
        mapDriver.addOutput(new Text(userCode1), new VectorWritable(cooccurrenceColumn));
        mapDriver.addOutput(new Text(userCode2), new VectorWritable(cooccurrenceColumn));

        mapDriver.runTest(false);
    }

    @Test
    public void testReducer() throws Exception {

        String house1 = houseCode1 + "/" + houseType1;
        String house2 = houseCode2 + "/" + houseType2;
        String house3 = houseCode3 + "/" + houseType3;

        RandomAccessVector<String> cooccurrenceColumn1 = new RandomAccessVector<String>();
        cooccurrenceColumn1.set(house1, 1.0);
        cooccurrenceColumn1.set(house2, 2.0);
        cooccurrenceColumn1.set(house3, 2.0);
        VectorWritable input1 = new VectorWritable(cooccurrenceColumn1);

        RandomAccessVector<String> cooccurrenceColumn2 = new RandomAccessVector<String>();
        cooccurrenceColumn2.set(house1, 0.0);
        cooccurrenceColumn2.set(house2, 1.0);
        cooccurrenceColumn2.set(house3, 1.0);
        VectorWritable input2 = new VectorWritable(cooccurrenceColumn2);

        List<VectorWritable> inputValues = new ArrayList<VectorWritable>(2);
        inputValues.add(input1);
        inputValues.add(input2);

        reduceDriver.withInput(new Text(userCode1), inputValues);
        RecommendedItem item2 = new RecommendedItem(house2, 3.0);
        RecommendedItem item3 = new RecommendedItem(house3, 3.0);
        RecommendedItem item1 = new RecommendedItem(house1, 1.0);
        List<RecommendedItem> outputValues = new ArrayList<RecommendedItem>(3);
        outputValues.add(item2);
        outputValues.add(item3);
        outputValues.add(item1);

        reduceDriver.withOutput(new Text(userCode1), new RecommendedItemsWritable(outputValues));

        reduceDriver.runTest();

    }
}
