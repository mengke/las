package org.easycloud.las.api.housevisit.service;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompositeProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.easycloud.las.api.housevisit.thrift.HouseVisit;
import org.easycloud.las.api.housevisit.thrift.HvRecord;
import org.easycloud.las.api.housevisit.thrift.UvsRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-10
 * Time: 下午5:06
 */
@ContextConfiguration(locations = {"classpath*:/spring/las-api.xml"})
public class HouseVisitServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HouseVisitService houseVisitService;

    @Test
    public void testGetUserVisitRecords() throws Exception {
        List<UvsRecord> records = houseVisitService.getUserVisitRecords(0, 15);
        assertEquals(records.size(), 15);
    }

    @Test
    public void testGetUserVisitRecord()throws Exception {
        List<HvRecord> records = houseVisitService.getUserVisitRecord("0005918715913596cb4c9c9e5dc13dd6", (byte) 2);
        for (HvRecord record : records) {
            assertEquals(record.getHouseType(), (byte) 2);
        }
    }

    @Test
    public void testThrift() throws Exception {
        TTransport transport = new TSocket("localhost", 8081);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        TProtocol compositeProtocol = new TCompositeProtocol(protocol, "house_visit");
        HouseVisit.Client client = new HouseVisit.Client(compositeProtocol);

        List<HvRecord> records = client.getUserVisitRecord("0005918715913596cb4c9c9e5dc13dd6", (byte) 1);
        for (HvRecord record : records) {
            assertEquals(record.getHouseType(), (byte) 1);
        }

        transport.close();
    }
}
