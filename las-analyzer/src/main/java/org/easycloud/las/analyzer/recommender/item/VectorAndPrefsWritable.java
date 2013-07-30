package org.easycloud.las.analyzer.recommender.item;

import com.google.common.collect.Lists;
import org.apache.hadoop.io.Writable;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 上午8:55
 */
public class VectorAndPrefsWritable implements Writable {

    private RandomAccessVector<String> vector;
    private List<String> userIDs;
    private List<Double> values;

    public VectorAndPrefsWritable() {
    }

    public VectorAndPrefsWritable(RandomAccessVector<String> vector, List<String> userIDs, List<Double> values) {
        this.vector = vector;
        this.userIDs = userIDs;
        this.values = values;
    }

    public RandomAccessVector<String> getVector() {
        return vector;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public List<Double> getValues() {
        return values;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        VectorWritable vw = new VectorWritable(vector);
        vw.write(out);
        out.writeInt(userIDs.size());
        for (int i = 0; i < userIDs.size(); i++) {
            out.writeUTF(userIDs.get(i));
            out.writeDouble(values.get(i));
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        VectorWritable writable = new VectorWritable();
        writable.readFields(in);
        vector = writable.get();
        int size = in.readInt();
        userIDs = Lists.newArrayListWithCapacity(size);
        values = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) {
            userIDs.add(in.readUTF());
            values.add(in.readDouble());
        }
    }

    @Override
    public String toString() {
        return vector + "\t" + userIDs + '\t' + values;
    }
}