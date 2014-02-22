package com.gopivotal.com.hh.dao;

import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Random;

/**
 * Created by queirc on 2/21/14.
 */
public class Payload {

    private Object[] data;

    private final  Random rand = new Random();

    public Payload(){
        this.data = generateValues();
    }
    private Object[] generateValues() {
        return new Object[]{new Timestamp(System.currentTimeMillis()), Integer.valueOf(rand.nextInt(10000000)), "a1234567891234567890", "b1234567891234567890", "c123456789", "d123456789", "e123456789", Integer.valueOf(Math.abs(rand.nextInt())), Integer.valueOf(Math.abs(rand.nextInt())), Integer.valueOf(Math.abs(rand.nextInt())), Integer.valueOf(Math.abs(rand.nextInt())), Integer.valueOf(Math.abs(rand.nextInt()))};
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
            payloads[i] = new Payload();
        }
        return payloads;
    }
}
