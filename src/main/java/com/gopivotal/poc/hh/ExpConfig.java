package com.gopivotal.poc.hh;

/**
 * Created by cq on 16/3/14.
 */
public class ExpConfig {

    private final int nThreads;

    private final int nTransactions;

    private final int batchSize;

    private final int expId;

    private final int workloadType;

    public ExpConfig(int expId, int nThreads, int nTransactions, int batchSize, int workloadType){

        this.expId = expId;
        this.nThreads =nThreads;
        this.nTransactions = nTransactions;
        this.batchSize = batchSize;
        this.workloadType = workloadType;
    }

    public int getnThreads() {
        return nThreads;
    }

    public int getnTransactions() {
        return nTransactions;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public int getExpId() {
        return expId;
    }

    public int getWorkloadType() {
        return workloadType;
    }
}
