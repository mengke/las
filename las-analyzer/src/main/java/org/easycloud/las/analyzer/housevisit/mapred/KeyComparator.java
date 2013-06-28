package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.easycloud.las.analyzer.io.TextLongPair;
import org.easycloud.las.analyzer.util.CompareUtil;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-26
 * Time: 上午11:45
 */
public class KeyComparator extends WritableComparator {

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
