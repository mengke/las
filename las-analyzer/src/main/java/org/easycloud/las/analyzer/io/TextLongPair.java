/*
 * Copyright 2013 Ke Meng (mengke@icloud.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.easycloud.las.analyzer.io;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.easycloud.las.analyzer.util.CompareUtil;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-21
 * Time: 上午10:14
 */
public class TextLongPair implements WritableComparable<TextLongPair> {

    private Text text;
    private long longValue;

    public TextLongPair() {
        set(new Text(), 0);
    }

    public TextLongPair(String text, long longValue) {
        set(new Text(text), longValue);
    }

    public void set(Text text, long longValue) {
        this.text = text;
        this.longValue = longValue;
    }

    public Text getText() {
        return text;
    }

    public long getLongValue() {
        return longValue;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        text.write(dataOutput);
        dataOutput.writeLong(longValue);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        text.readFields(dataInput);
        longValue = dataInput.readLong();
    }

    @Override
    public int compareTo(TextLongPair o) {
        int cmp = text.compareTo(o.text);
        if (cmp != 0) {
            return cmp;
        }
        return CompareUtil.compare(longValue, o.longValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextLongPair that = (TextLongPair) o;

        if (longValue != that.longValue) return false;
        if (!text.equals(that.text)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + (int) (longValue ^ (longValue >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return text + "," + longValue;
    }
}
