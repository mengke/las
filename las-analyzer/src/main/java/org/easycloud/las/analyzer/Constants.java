package org.easycloud.las.analyzer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-31
 */
public interface Constants {

    static final String LOG_DELIMITER = "\\s{3}";

    static final int LOG_ENTRIES_SIZE = 8;

    static final String LOG_PLACEHOLDER = "$";

    static final byte USER_TYPE_ANONYMOUS = 1;
    static final byte USER_TYPE_LOGIN = 2;

    static final byte HOUSE_TYPE_SELL = 1;
    static final byte HOUSE_TYPE_RENT = 2;

    static final DateFormat LOG_VISIT_DTTM_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static final int LOG_SKIPPED = 1;
    static final int LOG_PARSED = 2;
    static final int LOG_FORMAT_ERROR = 3;
}
