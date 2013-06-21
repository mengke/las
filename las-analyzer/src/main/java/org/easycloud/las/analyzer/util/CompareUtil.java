package org.easycloud.las.analyzer.util;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-21
 * Time: 上午10:58
 */
public class CompareUtil {

    public static int compare(long a, long b) {
        return (a < b ? -1 : (a == b ? 0 : 1));
    }

    public static int compare(byte a, byte b) {
        return (a < b ? -1 : (a == b ? 0 : 1));
    }

}
