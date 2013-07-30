package org.easycloud.las.analyzer.recommender.item;

import org.apache.hadoop.io.Writable;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-29
 * Time: 下午4:43
 */
public class VectorOrPrefWritable implements Writable {

    private RandomAccessVector<String> vector;
    private String userID;
    private double value;

    public VectorOrPrefWritable() {
    }

    public VectorOrPrefWritable(RandomAccessVector<String> vector) {
        this.vector = vector;
    }

    public VectorOrPrefWritable(String userID, double value) {
        this.userID = userID;
        this.value = value;
    }

    public RandomAccessVector<String> getVector() {
        return vector;
    }

    public String getUserID() {
        return userID;
    }

    public double getValue() {
        return value;
    }

    void set(RandomAccessVector<String> vector) {
        this.vector = vector;
        this.userID = "";
        this.value = Double.NaN;
    }

    public void set(String userID, double value) {
        this.vector = null;
        this.userID = userID;
        this.value = value;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        if (vector == null) {
            out.writeBoolean(false);
            out.writeUTF(userID);
            out.writeDouble(value);
        } else {
            out.writeBoolean(true);
            VectorWritable vw = new VectorWritable(vector);
            vw.write(out);
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        boolean hasVector = in.readBoolean();
        if (hasVector) {
            VectorWritable writable = new VectorWritable();
            writable.readFields(in);
            set(writable.get());
        } else {
            String theUserID = in.readUTF();
            double theValue = in.readDouble();
            set(theUserID, theValue);
        }
    }

    @Override
    public String toString() {
        return vector == null ? userID + ":" + value : vector.toString();
    }
}
