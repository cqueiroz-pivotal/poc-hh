package com.gopivotal.com.hh;

import com.gopivotal.poc.hh.dao.Payload;
import com.gopivotal.poc.hh.dao.PayloadDAO;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class HHAppTests {


    @Autowired
    PayloadDAO payloadDAO;

    @Test
    public void testGeneratePayloads() {
        Assert.assertEquals(10, Payload.generatePayloads(10).length);

    }

    @Test
    public void testSinglePayloadInsert(){
        Payload payload = Payload.generatePayloads(1)[0];
        Assert.assertNotNull(payload);
        payloadDAO.insert(payload);
    }

    @Test
    public void testBatchPayloadInsert() {
        Payload[] payloads = Payload.generatePayloads(500);
        Assert.assertNotNull(payloads);
        payloadDAO.insertBatch(payloads);
    }


}
