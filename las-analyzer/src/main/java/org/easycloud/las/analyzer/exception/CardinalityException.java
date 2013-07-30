package org.easycloud.las.analyzer.exception;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-29
 * Time: 下午1:29
 */
public class CardinalityException extends RuntimeException {

    public CardinalityException(int expected, int cardinality) {
        super("Required cardinality " + expected + " but got " + cardinality);
    }
}
