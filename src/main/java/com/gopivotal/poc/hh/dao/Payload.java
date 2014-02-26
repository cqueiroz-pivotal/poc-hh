package com.gopivotal.poc.hh.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;



/**
 * Created by queirc on 2/21/14.
 */
public class Payload {

    private final static Logger LOG = LoggerFactory.getLogger(Payload.class);

    private final Object[] data;

    private final static Random random = new Random(System.currentTimeMillis());




    private Payload(int i) {

        this.data = generateValues(i);
    }

    private Object[] generateValues(int i) {

            String key = UUID.randomUUID().toString();
            return new Object[]{new Timestamp(System.currentTimeMillis()), key, "a1234567891234567890", "b1234567891234567890",
                    "c123456789", "d123456789", "e123456789", Math.abs(random.nextInt()),
                    Math.abs(random.nextInt()), Math.abs(random.nextInt()),
                    Math.abs(random.nextInt()), Math.abs(random.nextInt())};


    }

    public Object[] data(){
        return this.data;
    }

    /**
     * Generates payloads
     * @param howMany
     * @return
     */
    public static Payload[] generatePayloads(int howMany){

        Assert.isTrue(howMany>0);
        Payload[] payloads = new Payload[howMany];
        for(int i = 0 ; i < howMany; i ++){
            payloads[i] = new Payload(i+1);
        }
        return payloads;
    }
}
