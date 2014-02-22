package com.gopivotal.poc.hh.dao;

/**
 * Created by queirc on 2/21/14.
 */
public interface PayloadDAO {

    /**
     *
     * @param payload
     */
    public void insert(Payload payload);

    /**
     *
     * @param payloads
     */
    public void insertBatch(Payload[] payloads);


}
