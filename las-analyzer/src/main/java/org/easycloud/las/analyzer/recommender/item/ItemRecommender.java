package org.easycloud.las.analyzer.recommender.item;

import org.easycloud.las.analyzer.recommender.ByValueRecommendedItemComparator;
import org.easycloud.las.analyzer.recommender.RecommendedItem;
import org.easycloud.las.analyzer.recommender.VectorWritable;
import org.easycloud.las.analyzer.util.RandomAccessVector;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-31
 * Time: 下午1:40
 */
public class ItemRecommender {

    static List<RecommendedItem> considerRecommendation(Iterator<VectorWritable> values, int maxRecommendations) {
        RandomAccessVector<String> recommendationVector = null;
        while (values.hasNext()) {
            VectorWritable vectorWritable = values.next();
            recommendationVector = recommendationVector == null ? vectorWritable
                    .get() : recommendationVector.plus(vectorWritable.get());
        }

        Queue<RecommendedItem> topItems = new PriorityQueue<RecommendedItem>(
                maxRecommendations + 1,
                Collections.reverseOrder(ByValueRecommendedItemComparator
                        .getInstance()));


        Iterator<Map.Entry<String, Double>> recommendationVectorIterator = recommendationVector.iterator();
        while (recommendationVectorIterator.hasNext()) {
            Map.Entry<String, Double> entry = recommendationVectorIterator.next();
            String itemId = entry.getKey();
            double value = entry.getValue();
            if (topItems.size() < maxRecommendations) {
                topItems.add(new RecommendedItem(itemId, value));
            } else if (value > topItems.peek().getPrefValue()) {
                topItems.add(new RecommendedItem(itemId, value));
                topItems.poll();
            }
        }


        List<RecommendedItem> recommendations = new ArrayList<RecommendedItem>(
                topItems.size());
        recommendations.addAll(topItems);
        Collections.sort(recommendations,
                ByValueRecommendedItemComparator.getInstance());
        return recommendations;
    }
}
