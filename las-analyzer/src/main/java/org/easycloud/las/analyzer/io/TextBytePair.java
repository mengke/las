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
 * Time: 上午11:43
 */
public class TextBytePair implements WritableComparable<TextBytePair> {

    private Text text;
    private byte byteValue;

    public TextBytePair() {
        set(new Text(), (byte) 0);
    }

    public TextBytePair(String text, byte byteValue) {
        set(new Text(text), byteValue);
    }

    public void set(Text text, byte byteValue) {
        this.text = text;
        this.byteValue = byteValue;
    }

    public Text getText() {
        return text;
    }

    public byte getByteValue() {
        return byteValue;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        text.write(out);
        out.writeByte(byteValue);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        text.readFields(in);
        byteValue = in.readByte();
    }

    @Override
    public int compareTo(TextBytePair o) {
        int cmp = text.compareTo(o.text);
        if (cmp != 0) {
            return cmp;
        }
        return CompareUtil.compare(byteValue, o.byteValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextBytePair that = (TextBytePair) o;

        if (byteValue != that.byteValue) return false;
        if (!text.equals(that.text)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + (int) byteValue;
        return result;
    }

    @Override
    public String toString() {
        return text + "," + byteValue;
    }
}
