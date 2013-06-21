package org.easycloud.las.analyzer.housevisit;

import org.apache.hadoop.io.Text;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.easycloud.las.analyzer.Constants.*;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-6-17
 */
public class HouseVisitParser {

	public static final Pattern PATTERN_HOUSE_VISIT = Pattern.compile("http://beijing\\.homelink\\.com\\.cn/(\\w+?)/(\\w{12}).shtml.*");
	private static final String RENT_HOUSE = "zufang";
	private static final String SELL_HOUSE = "ershoufang";

	private HouseVisitEntry houseVisitEntry;

	private int parseStatus;

	public void parse(String record) {
		parseStatus = 0;
		String[] recordEntries = record.split(LOG_DELIMITER);
		assert recordEntries.length == LOG_ENTRIES_SIZE;
		String cookieId = recordEntries[2];
		String userCode = recordEntries[5];
		String visitUrl = recordEntries[4];
		String visitTimeStr = recordEntries[1];

		Matcher houseVisitMatcher = PATTERN_HOUSE_VISIT.matcher(visitUrl);
		if (houseVisitMatcher.matches()) {
			houseVisitEntry = new HouseVisitEntry();
			String houseServiceType = houseVisitMatcher.group(1);
			String houseCode = houseVisitMatcher.group(2);
			byte houseType;
			if (RENT_HOUSE.equals(houseServiceType)) {
				houseType = HOUSE_TYPE_RENT;
			} else if (SELL_HOUSE.equals(houseServiceType)) {
				houseType = HOUSE_TYPE_SELL;
			} else {
				// skip
				parseStatus = LOG_SKIPPED;
				return;
			}
			Date visitTime;
			try {
				visitTime = LOG_VISIT_DTTM_FORMAT.parse(visitTimeStr);
				long visitDttm = visitTime.getTime();
				String userId;
				byte userType;
				if (!LOG_PLACEHOLDER.equals(userCode)) {
					userId = userCode;
					userType = USER_TYPE_LOGIN;
				} else {
					userId = cookieId;
					userType = USER_TYPE_ANONYMOUS;
				}

				houseVisitEntry = new HouseVisitEntry(userId, userType, visitDttm, houseCode, houseType);
				parseStatus = LOG_PARSED;
			} catch (ParseException e) {
				parseStatus = LOG_FORMAT_ERROR;
			}

		} else {
			parseStatus = LOG_SKIPPED;
		}
	}

	public void parse(Text record) {
		parse(record.toString());
	}

	public HouseVisitEntry getHouseVisitEntry() {
		return houseVisitEntry;
	}

	public int getParseStatus() {
		return parseStatus;
	}
}
