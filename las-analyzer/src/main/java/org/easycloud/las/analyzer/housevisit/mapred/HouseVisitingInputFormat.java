package org.easycloud.las.analyzer.housevisit.mapred;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.easycloud.las.analyzer.housevisit.HouseVisitArrayWritable;
import org.easycloud.las.analyzer.housevisit.HouseVisitRecord;
import org.easycloud.las.analyzer.io.TextBytePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-3
 * Time: 上午10:35
 */
public class HouseVisitingInputFormat extends FileInputFormat<TextBytePair, HouseVisitArrayWritable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseVisitingInputFormat.class);

    @Override
    public RecordReader<TextBytePair, HouseVisitArrayWritable>
    getRecordReader(InputSplit inputSplit,
                    JobConf conf, Reporter reporter) throws IOException {
        reporter.setStatus(inputSplit.toString());
        return new HouseVisitingRecordReader(conf, (FileSplit) inputSplit);
    }


    private class HouseVisitingRecordReader implements RecordReader<TextBytePair, HouseVisitArrayWritable> {

        private LineRecordReader lineReader;
        private LongWritable lineKey;
        private Text lineValue;

        public HouseVisitingRecordReader(JobConf conf, FileSplit inputSplit) throws IOException {
            lineReader = new LineRecordReader(conf, inputSplit);
            lineKey = lineReader.createKey();
            lineValue = lineReader.createValue();
        }

        @Override
        public boolean next(TextBytePair key, HouseVisitArrayWritable value) throws IOException {
            // get the next line
            if (!lineReader.next(lineKey, lineValue)) {
                return false;
            }

            // parse the lineValue which is in the format:
            // key1st,key2nd\t[h1|t1|v1, h2|t2|v2, h3|t3|v3]
            String[] pieces = lineValue.toString().split("\t");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("HouseVisitingInputFormat processing the record :" + lineValue);
            }
            if (pieces.length != 2) {
                throw new IOException("Invalid record received : " + lineValue);
            }

            // try to parse the key from lineValue
            String keyPiece = pieces[0].trim();
            String[] keyPieces = keyPiece.split(",");
            if (keyPieces.length != 2) {
                throw new IOException("Invalid record received : " + lineValue);
            }
            String keyFirstPart = keyPieces[0].trim();

            byte keySecondPart;
            try {
                keySecondPart = Byte.parseByte(keyPieces[1].trim());
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("HouseVisitingInputFormat parse the byte value :" + keySecondPart);
                }
            } catch (NumberFormatException nfe) {
                throw new IOException("Error parsing byte value in record : " + keyPieces[1].trim());
            }
            key.set(new Text(keyFirstPart), keySecondPart);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("HouseVisitingInputFormat : parsed the key is " + key);
            }
            // try to parse array value from lineValue
            String valuePiece = pieces[1].trim();
            if (!valuePiece.startsWith("[") || !valuePiece.endsWith("]")) {
                throw new IOException("Invalid record value received : " + valuePiece);
            }

            valuePiece = valuePiece.substring(1, valuePiece.length() - 1);
            String[] valueArray = valuePiece.split(", ");
            List<HouseVisitRecord> houseVisitRecordList = new ArrayList<HouseVisitRecord>();
            for (String strValue : valueArray) {
                String[] houseVisitPieces = strValue.split("\\|");
                if (houseVisitPieces.length != 3) {
                    throw new IOException("Invalid the value entry received : " + strValue + ", the pieces num is : " + houseVisitPieces.length);
                }
                String houseCode = houseVisitPieces[0].trim();
                byte houseType;
                long visitDttm;
                try {
                    houseType = Byte.parseByte(houseVisitPieces[1].trim());
                    visitDttm = Long.parseLong(houseVisitPieces[2].trim());
                } catch (NumberFormatException nfe) {
                    throw new IOException("Error parsing the value entry in record : " + strValue);
                }
                houseVisitRecordList.add(new HouseVisitRecord(houseCode, houseType, visitDttm));
            }
            HouseVisitRecord[] houseVisitRecords = houseVisitRecordList.toArray(new HouseVisitRecord[houseVisitRecordList.size()]);
            value.set(houseVisitRecords);
            return true;
        }

        @Override
        public TextBytePair createKey() {
            return new TextBytePair();
        }

        @Override
        public HouseVisitArrayWritable createValue() {
            return new HouseVisitArrayWritable();
        }

        @Override
        public long getPos() throws IOException {
            return lineReader.getPos();
        }

        @Override
        public void close() throws IOException {
            lineReader.close();
        }

        @Override
        public float getProgress() throws IOException {
            return lineReader.getProgress();
        }
    }
}
