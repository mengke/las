package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;
import org.junit.Before;
import org.junit.Test;

import static org.easycloud.las.analyzer.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 下午1:49
 */
public class CooccurrenceColumnWrapperTests {

    private MapDriver<Text, VectorWritable, Text, VectorOrPrefWritable> mapDriver;

    private String houseCode1, houseCode2, houseCode3;
    private byte houseType1, houseType2, houseType3;

    @Before
    public void setUp() throws Exception {
        CooccurrenceColumnWrapperMapper mapper = new CooccurrenceColumnWrapperMapper();
        mapDriver = MapDriver.newMapDriver(mapper);

        houseCode1 = "BJFT85558816";
        houseType1 = HOUSE_TYPE_SELL;

        houseCode2 = "BJCP85062036";
        houseType2 = HOUSE_TYPE_RENT;

        houseCode3 = "BJFT84662905";
        houseType3 = HOUSE_TYPE_RENT;
    }

    @Test
    public void testMapper() throws Exception {

        String house1 = houseCode1 + "/" + houseType1;
        String house2 = houseCode2 + "/" + houseType2;
        String house3 = houseCode3 + "/" + houseType3;

        RandomAccessVector<String> cooccurrenceRow = new RandomAccessVector<String>();
        cooccurrenceRow.set(house1, 1);
        cooccurrenceRow.set(house2, 2);
        cooccurrenceRow.set(house3, 2);

        mapDriver.withInput(new Text(house1), new VectorWritable(cooccurrenceRow));

        VectorOrPrefWritable ouputValue = new VectorOrPrefWritable(cooccurrenceRow);
        mapDriver.withOutput(new Text(house1), ouputValue);
    }
}
