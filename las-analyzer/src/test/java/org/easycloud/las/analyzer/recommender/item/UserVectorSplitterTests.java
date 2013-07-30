package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;
import org.junit.Before;
import org.junit.Test;

import static org.easycloud.las.analyzer.Constants.HOUSE_TYPE_RENT;
import static org.easycloud.las.analyzer.Constants.HOUSE_TYPE_SELL;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 下午1:57
 */
public class UserVectorSplitterTests {

    private MapDriver<Text, VectorWritable, Text, VectorOrPrefWritable> mapDriver;

    private String houseCode1, houseCode2, houseCode3;
    private byte houseType1, houseType2, houseType3;
    private String userCode1;

    @Before
    public void setUp() throws Exception {
        UserVectorSplitterMapper mapper = new UserVectorSplitterMapper();
        mapDriver = MapDriver.newMapDriver(mapper);

        userCode1 = "6e2b58d1ed29f167976f9c88c835951d";

        houseCode1 = "BJFT85558816";
        houseType1 = HOUSE_TYPE_SELL;

        houseCode2 = "BJCP85062036";
        houseType2 = HOUSE_TYPE_RENT;

        houseCode3 = "BJFT84662905";
        houseType3 = HOUSE_TYPE_RENT;
    }

    @Test
    public void testMapper() throws Exception {
        String house2 = houseCode2 + "/" + houseType2;
        String house3 = houseCode3 + "/" + houseType3;

        RandomAccessVector<String> userVector1 = new RandomAccessVector<String>(2);
        userVector1.set(house2, 1.0);
        userVector1.set(house3, 1.0);

        mapDriver.withInput(new Text(userCode1), new VectorWritable(userVector1));
        VectorOrPrefWritable outputValue2 = new VectorOrPrefWritable(userCode1, 1.0);
        VectorOrPrefWritable outputValue3 = new VectorOrPrefWritable(userCode1, 1.0);
        mapDriver.addOutput(new Text(house2), outputValue2);
        mapDriver.addOutput(new Text(house3), outputValue3);

        mapDriver.runTest(false);
    }
}
