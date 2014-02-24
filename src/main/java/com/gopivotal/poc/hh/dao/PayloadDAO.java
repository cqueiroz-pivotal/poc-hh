package com.gopivotal.poc.hh.dao;

/**
 * Created by queirc on 2/21/14.
 */
public interface PayloadDAO {

    /**
     * Close connection.
     */
    public void closeConnection();

// --Commented out by Inspection START (2/25/14, 7:25 AM):
//    /**
//     *
//     * @param payload
//     */
//    public void insert(Payload payload);
// --Commented out by Inspection STOP (2/25/14, 7:25 AM)

    /**
     *
     * @param payloads
     */
    public void insertBatch(Payload[] payloads);


}
