package org.easycloud.las.services.housevisit.service;

import org.apache.thrift.TException;
import org.easycloud.las.services.housevisit.thrift.HouseVisit;
import org.easycloud.las.services.housevisit.thrift.HvRecord;
import org.easycloud.las.services.housevisit.thrift.UvsRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-10
 * Time: 下午1:49
 */
@Service
public class HouseVisitService implements HouseVisit.Iface {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<UvsRecord> getUserVisitRecords(int start, int limit) throws TException {
        Query query = new Query().skip(start).limit(limit);
        return mongoTemplate.find(query, UvsRecord.class, "house_visit.out");
    }

    @Override
    public List<HvRecord> getUserVisitRecord(String userCode, byte houseType) throws TException {
        UvsRecord userRecord = mongoTemplate.findById(userCode, UvsRecord.class, "house_visit.out");
        List<HvRecord> houseVisits = userRecord.getHvRecords();
        List<HvRecord> results = new ArrayList<HvRecord>();
        if (houseType > 0) {
            for (HvRecord hvRecord : houseVisits) {
                if (hvRecord.getHouseType() == houseType) {
                    results.add(hvRecord);
                }
            }
        }
        return results;
    }

}
