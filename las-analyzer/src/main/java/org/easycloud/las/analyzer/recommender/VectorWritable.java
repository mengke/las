package org.easycloud.las.analyzer.recommender;

import org.apache.hadoop.io.Writable;
import org.easycloud.las.analyzer.util.RandomAccessVector;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-29
 * Time: 下午3:12
 */
public class VectorWritable implements Writable {

    private RandomAccessVector<String> vector;

    public VectorWritable() {
    }

    public VectorWritable(RandomAccessVector<String> vector) {
        this.vector = vector;
    }

    public RandomAccessVector<String> get() {
        return vector;
    }

    public void set(RandomAccessVector<String> vector) {
        this.vector = vector;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(vector.size());
        Iterator<Map.Entry<String, Double>> iter = vector.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Double> entry = iter.next();
            if (entry.getValue() != 0.0) {
                out.writeUTF(entry.getKey());
                out.writeDouble(entry.getValue());
            }
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        int size = in.readInt();
        RandomAccessVector<String> v = new RandomAccessVector<String>(size);
        for (int i = 0; i < size; i++) {
            String key = in.readUTF();
            double value = in.readDouble();
            v.set(key, value);
        }
        vector = v;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VectorWritable that = (VectorWritable) o;

        if (vector != null ? !vector.equals(that.vector) : that.vector != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return vector != null ? vector.hashCode() : 0;
    }

    @Override
    public String toString() {
        return vector.toString();
    }
}
