package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.easycloud.las.analyzer.io.TextLongPair;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-26
 * Time: 上午11:45
 */
public class GroupComparator extends WritableComparator {
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