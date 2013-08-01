package org.easycloud.las.api.services.impl;

import org.apache.thrift.TException;
import org.easycloud.las.api.services.thrift.*;
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
 * Date: 13-8-1
 * Time: 下午1:57
 */
@Service
public class LasServiceImpl implements LasService.Iface {

    private static Logger LOGGER = LoggerFactory.getLogger(LasServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<UserVisitRecord> listUserVisitRecords(int start, int limit) throws TException {
        Query query = new Query().skip(start).limit(limit);
        return mongoTemplate.find(query, UserVisitRecord.class, "house_visit.out");
    }

    @Override
    public List<VisitRecord> findVisitRecords(String userCode, byte houseType) throws TException {
        UserVisitRecord userRecord = mongoTemplate.findById(userCode, UserVisitRecord.class, "house_visit.out");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("UserCode: " + userCode);
            LOGGER.debug("HouseType: " + houseType);

        }
        if (userRecord != null) {
            List<VisitRecord> houseVisits = userRecord.getVisitRecords();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Houses size: " + houseVisits.size());
            }
            List<VisitRecord> results = new ArrayList<VisitRecord>();
            if (houseType > 0) {
                for (VisitRecord hvRecord : houseVisits) {
                    if (hvRecord.getHouseType() == houseType) {
                        results.add(hvRecord);
                    }
                }
            } else {
                return houseVisits;
            }
            return results;
        } else {
            return new ArrayList<VisitRecord>();
        }
    }

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
