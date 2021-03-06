package org.easycloud.las.analyzer.recommender;

import com.google.common.collect.Lists;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 上午10:09
 */
public final class RecommendedItemsWritable implements Writable {

    private List<RecommendedItem> recommended;

    public RecommendedItemsWritable() {
        // do nothing
    }

    public RecommendedItemsWritable(List<RecommendedItem> recommended) {
        this.recommended = recommended;
    }

    public List<RecommendedItem> getRecommendedItems() {
        return recommended;
    }

    public void set(List<RecommendedItem> recommended) {
        this.recommended = recommended;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(recommended.size());
        for (RecommendedItem item : recommended) {
            out.writeUTF(item.getItemId());
            out.writeInt(item.getPrefValue());
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        int size = in.readInt();
        recommended = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) {
            String itemID = in.readUTF();
            int value = in.readInt();
            RecommendedItem recommendedItem = new RecommendedItem(itemID, value);
            recommended.add(recommendedItem);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecommendedItemsWritable that = (RecommendedItemsWritable) o;

        if (recommended != null ? !recommended.equals(that.recommended) : that.recommended != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return recommended != null ? recommended.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(200);
        result.append('[');
        boolean first = true;
        for (RecommendedItem item : recommended) {
            if (first) {
                first = false;
            } else {
                result.append(',');
            }
            result.append(String.valueOf(item.getItemId()));
            result.append(':');
            result.append(String.valueOf(item.getPrefValue()));
        }
        result.append(']');
        return result.toString();
    }
}
