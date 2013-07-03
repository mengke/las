package org.easycloud.las.analyzer.housevisit.mapred;

import com.mongodb.hadoop.io.MongoUpdateWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.bson.BasicBSONObject;
import org.easycloud.las.analyzer.housevisit.HouseVisitArrayWritable;
import org.easycloud.las.analyzer.housevisit.HouseVisitRecord;
import org.easycloud.las.analyzer.io.TextBytePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-2
 * Time: 下午2:24
 */
public class MongodbReducer extends MapReduceBase
        implements Reducer<TextBytePair, HouseVisitArrayWritable, NullWritable, MongoUpdateWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongodbReducer.class);

    @Override
    public void reduce(TextBytePair key, Iterator<HouseVisitArrayWritable> value,
                       OutputCollector<NullWritable, MongoUpdateWritable> collector,
                       Reporter reporter) throws IOException {

        BasicBSONObject query = new BasicBSONObject("_id", key.getText().toString());
        while (value.hasNext()) {
            HouseVisitArrayWritable houseVisitArray = value.next();
            List<BasicBSONObject> houseRecordList = new ArrayList<BasicBSONObject>();
            for (Writable writable : houseVisitArray.get()) {
                HouseVisitRecord houseVisitRecord = (HouseVisitRecord) writable;
                BasicBSONObject basicBSONObject = new BasicBSONObject("hid", houseVisitRecord.getHouseCode().toString())
                        .append("visitDttm", houseVisitRecord.getVisitDttm())
                        .append("houseType", houseVisitRecord.getHouseType());
                houseRecordList.add(basicBSONObject);
            }
            BasicBSONObject update = new BasicBSONObject("_id", key.getText().toString())
                    .append("userType", key.getByteValue())
                    .append("houseVisits", houseRecordList);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Query Object is " + query);
                LOGGER.debug("Update Object is " + update);
            }
            collector.collect(null, new MongoUpdateWritable(query, update, true, false));
        }
    }
}
