package com.gopivotal.poc.hh;

import com.gopivotal.poc.hh.dao.JdbcPayloadDAO;
import com.gopivotal.poc.hh.dao.Payload;

import com.gopivotal.poc.hh.dao.PayloadDAO;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Component
@Scope("prototype")
class HitService {

    private final Logger LOG = LoggerFactory.getLogger(HitService.class);


    @Autowired
    private PayloadDAO payloadDAO;

//    @PostConstruct
//    private void setDAO() throws SQLException{
//        this.payloadDAO = new JdbcPayloadDAO(ds);
//    }

    public long hit(int batchSize) {

        long startTime = System.currentTimeMillis();
        Payload[] payloads = Payload.generatePayloads(batchSize);
        payloadDAO.insertBatch(payloads);
        long finalTime = System.currentTimeMillis();
        return (finalTime - startTime);
    }


}
