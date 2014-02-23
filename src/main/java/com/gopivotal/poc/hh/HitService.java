package com.gopivotal.poc.hh;

import com.gopivotal.poc.hh.dao.Payload;
import com.gopivotal.poc.hh.dao.PayloadDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class HitService {

    @Autowired
    private PayloadDAO payloadDAO;

    public long hit(int batchSize) {
        long startTime = System.currentTimeMillis();
        Payload[] payloads = Payload.generatePayloads(batchSize);
        payloadDAO.insertBatch(payloads);
        long finalTime = System.currentTimeMillis();
        return (finalTime - startTime);
    }
}
