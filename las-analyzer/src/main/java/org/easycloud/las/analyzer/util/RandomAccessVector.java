package org.easycloud.las.analyzer.util;

import org.easycloud.las.analyzer.exception.CardinalityException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-29
 * Time: 上午9:39
 */
public class RandomAccessVector<T> {

    private LinkedHashMap<T, Integer> values;

    public RandomAccessVector() {
        this(0);
    }

    public RandomAccessVector(int size) {
        this.values = new LinkedHashMap<T, Integer>(size);
    }

    public RandomAccessVector(LinkedHashMap<T, Integer> values) {
        this.values = values;
    }

    public RandomAccessVector<T> like() {
        return new RandomAccessVector<T>(size());
    }

    public RandomAccessVector<T> assign(RandomAccessVector<T> vector) {
        values.clear();
        for (Map.Entry<T, Integer> entry : vector.values.entrySet()) {
            if (entry.getValue() != 0) {
                values.put(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public int size() {
        return values != null ? values.size() : 0;
    }

    public int get(T key) {
        Integer result = values.get(key);
        return result != null ? result : 0;
    }

    public void set(T key, int value) {
        if (value == 0) {
            values.remove(key);
        } else {
            values.put(key, value);
        }
    }

    public Iterator<Map.Entry<T, Integer>> iterator() {
        return values.entrySet().iterator();
    }

    public RandomAccessVector<T> times(int x) {
        if (x == 0) {
            return like();
        }

        RandomAccessVector<T> result = like().assign(this);
        if (x == 1) {
            return result;
        }

        for (Map.Entry<T, Integer> xEntry : result.values.entrySet()) {
            xEntry.setValue(xEntry.getValue() * x);
        }

        return result;
    }

    public RandomAccessVector<T> plus(RandomAccessVector<T> x) {

        RandomAccessVector<T> result = like().assign(this);

        for (Map.Entry<T, Integer> xEntry : x.values.entrySet()) {
            T key = xEntry.getKey();
            result.set(key, this.get(key) + xEntry.getValue());
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;

        RandomAccessVector that = (RandomAccessVector) o;
        if (size() != that.size()) {
            return false;
        }

        Iterator<Map.Entry<T, Integer>> iter = that.iterator();
        while (iter.hasNext()) {
            Map.Entry<T, Integer> entry = iter.next();
            T key = entry.getKey();
            if (get(key) != entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = size();
        Iterator<Map.Entry<T, Integer>> iter = iterator();
        while (iter.hasNext()) {
            Map.Entry<T, Integer> entry = iter.next();
            result += entry.getKey().hashCode() * entry.getValue();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('{');
        Iterator<Map.Entry<T, Integer>> iter = iterator();
        while (iter.hasNext()) {
            Map.Entry<T, Integer> entry = iter.next();
            T key = entry.getKey();
            double value = entry.getValue();
            if (value != 0) {
                result.append(key.toString());
                result.append(':');
                result.append(value);
                result.append(',');
            }
        }
        if (result.length() > 1) {
            result.setCharAt(result.length() - 1, '}');
        } else {
            result.append('}');
        }
        return result.toString();
    }
}
