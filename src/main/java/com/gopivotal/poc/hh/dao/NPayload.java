package com.gopivotal.poc.hh.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

/**
 * Created by cq on 13/3/14.
 */
public class NPayload {

    private final static Logger LOG = LoggerFactory.getLogger(NPayload.class);

    private final Object[] data;

    private final static Random random = new Random(System.currentTimeMillis());


    private NPayload() {

        this.data = generateValues();
    }

    /**
     * lot_id varchar(20)
     * wafer_id varchar(50)
     * slot no numeric
     * setupseq varchar(255)
     * processid varchar20
     * process varchar20
     * partid varchar20
     * ppid varchar64
     * reticleid varchar255
     * waferno num(2,0)
     * lottype char(20)
     * equip_index numeric(9,0)
     * is_ktcancel char1
     * is_abnormal char1
     * txsequence numeric
     * unit_index num(9,0)
     * erd_param_index num(9,0)
     * act date timestamp
     * recipe_id varchar(255)
     * chamer_step_id varchar(255)
     * sensor_valn numeric
     * upp_spec numeric
     * low_spec numeric
     *
     * @return
     */
    private Object[] generateValues() {

        String key = UUID.randomUUID().toString();
        return new Object[]{randomString(20), randomString(50), randomString(20), Math.abs(random.nextInt()),
                randomString(255), randomString(20), randomString(20), randomString(64),
                randomString(255), Math.abs(random.nextInt()),
                randomString(20), Math.abs(random.nextInt()),randomString(1),randomString(1), Math.abs(random.nextInt()),
                Math.abs(random.nextInt()), Math.abs(random.nextInt()), new Timestamp(System.currentTimeMillis()),
        randomString(255),randomString(255), Math.abs(random.nextInt()), Math.abs(random.nextInt()), Math.abs(random.nextInt())};

    }

    private String randomString(int size) {
        return new BigInteger(5000, random).toString(32).substring(0, size);
    }

    public Object[] data() {
        return this.data;
    }

    /**
     * Generates payloads
     *
     * @param howMany
     * @return
     */
    public static NPayload[] generatePayloads(int howMany) {

        Assert.isTrue(howMany > 0);
        NPayload[] payloads = new NPayload[howMany];
        for (int i = 0; i < howMany; i++) {
            payloads[i] = new NPayload();
        }
        return payloads;
    }
}
