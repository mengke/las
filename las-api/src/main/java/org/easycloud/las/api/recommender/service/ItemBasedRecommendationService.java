package org.easycloud.las.api.recommender.service;

import org.apache.thrift.TException;
import org.easycloud.las.api.recommender.thrift.ItemBasedRecommendation;
import org.easycloud.las.api.recommender.thrift.Recommendation;
import org.easycloud.las.api.recommender.thrift.UserRecommendations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-31
 * Time: 下午2:57
 */
@Service
public class ItemBasedRecommendationService implements ItemBasedRecommendation.Iface {

    private static Logger LOGGER = LoggerFactory.getLogger(ItemBasedRecommendationService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<UserRecommendations> listItemBasedRecommendations(int start, int limit) throws TException {
        Query query = new Query().skip(start).limit(limit);
        return mongoTemplate.find(query, UserRecommendations.class, "item_recommendations.out");
    }

    @Override
    public List<Recommendation> findItemBasedRecommendations(String userCode) throws TException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("UserCode: " + userCode);
        }

        UserRecommendations userRecommendations = mongoTemplate.findById(userCode,
                UserRecommendations.class, "item_recommendations.out");
        if (userRecommendations != null) {
            List<Recommendation> recommendations = userRecommendations.getRecommendations();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Item-based recommendation size: " + recommendations.size());
            }
            return recommendations;
        } else {
            return new ArrayList<Recommendation>(0);
        }
    }
}
