package org.easycloud.las.analyzer.recommender;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 上午10:30
 */
public class ByValueRecommendedItemComparator implements Comparator<RecommendedItem>, Serializable {

    private static final Comparator<RecommendedItem> INSTANCE = new ByValueRecommendedItemComparator();

    public static Comparator<RecommendedItem> getInstance() {
        return INSTANCE;
    }

    @Override
    public int compare(RecommendedItem o1, RecommendedItem o2) {
        int value1 = o1.getPrefValue();
        int value2 = o2.getPrefValue();
        return value1 > value2 ? -1 : value1 < value2 ? 1 : 0;
    }
}
