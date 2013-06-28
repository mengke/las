package org.easycloud.las.analyzer.housevisit.mapreduce;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Partitioner;
import org.easycloud.las.analyzer.housevisit.HouseVisitEntry;
import org.easycloud.las.analyzer.io.TextLongPair;
import org.easycloud.las.analyzer.util.CompareUtil;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-21
 * Time: 下午2:02
 */
public class FirstPartitioner extends Partitioner<TextLongPair, HouseVisitEntry> {

    @Override
    public int getPartition(TextLongPair key, HouseVisitEntry value, int numPartitions) {
        return Math.abs(key.getText().hashCode() * 127) % numPartitions;
    }

    public static class KeyComparator extends WritableComparator {

        public KeyComparator() {
            super(TextLongPair.class, true);
        }

        @Override
        public int compare(WritableComparable a, WritableComparable b) {
            TextLongPair tlpa = (TextLongPair) a;
            TextLongPair tlpb = (TextLongPair) b;
            int cmp = tlpa.getText().compareTo(tlpb.getText());
            if (cmp != 0) {
                return cmp;
            }
            return -CompareUtil.compare(tlpa.getLongValue(), tlpb.getLongValue());  // dttm desc
        }
    }

    public static class GroupComparator extends WritableComparator {
        protected GroupComparator() {
            super(TextLongPair.class, true);
        }

        @Override
        public int compare(WritableComparable a, WritableComparable b) {
            TextLongPair tlpa = (TextLongPair) a;
            TextLongPair tlpb = (TextLongPair) b;
            return tlpa.getText().compareTo(tlpb.getText());
        }
    }
}
