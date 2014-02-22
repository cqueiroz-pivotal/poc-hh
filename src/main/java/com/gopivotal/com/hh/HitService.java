package com.gopivotal.com.hh;

import com.gopivotal.com.hh.dao.Payload;
import com.gopivotal.com.hh.dao.PayloadDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HitService {

    @Autowired
    private PayloadDAO payloadDAO;

    public long hit(int batchSize) {
        long time = 0;
        Payload[] payloads = Payload.generatePayloads(batchSize);
        payloadDAO.insertBatch(payloads);
        return time;
    }
}
