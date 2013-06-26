package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Partitioner;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.io.TextLongPair;
import org.easycloud.las.analyzer.util.CompareUtil;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-26
 * Time: 上午10:52
 */
public class FirstPartitioner extends MapReduceBase implements Partitioner<TextLongPair, HouseVisitEntry> {

    @Override
    public int getPartition(TextLongPair key, HouseVisitEntry value, int numPartitions) {
        return Math.abs(key.getText().hashCode() * 127) % numPartitions;
    }
}
