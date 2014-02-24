package com.gopivotal.poc.hh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by queirc on 2/21/14.
 */
class Stats {

    private final Logger LOG = LoggerFactory.getLogger(Stats.class);

    private final HashMap<Integer,Long> executionTimes = new HashMap<Integer, Long>();

    private final int nThreads;

    private final int nTransactions;

    private final int batchSize;
    private final int nExperiments;

    private final SimpleDateFormat sdf;

    private final Date currentDate;

    public Stats(int nThreads, int batchSize, int nTransactions, int nExperiments){
        this.nThreads = nThreads;
        this.nTransactions = nTransactions;
        this.batchSize = batchSize;
        this.nExperiments = nExperiments;
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        currentDate = new Date();
    }

    public void addMeasurement(int id, long executionTime){
        executionTimes.put(id,executionTime);
    }

    public void computeAndSave() {
        String fileName = sdf.format(currentDate);
        PrintWriter pwc = null;
        try {
            pwc = new PrintWriter("POC" + fileName + ".csv");
            pwc.println("Threads: " + nThreads);
            pwc.println("Transactions: " + nTransactions);
            pwc.println("Batch size: " + batchSize);
            pwc.println("Experiments: " + nExperiments);
            String HEADER = "Experiment #, # Rows, TPS, TPS per thread,Total Time";
            pwc.println(HEADER);
            pwc.flush();
            int nRows = nTransactions * nThreads * batchSize;

            for(int key : executionTimes.keySet()){
                StringBuilder sb = new StringBuilder();
                sb.append(key);
                sb.append(",");
                sb.append(nRows);
                sb.append(",");
                double tps = nRows/ (executionTimes.get(key) / 1000.0f);
                double tpst = (nTransactions * batchSize)/ (executionTimes.get(key) / 1000.0f);
                sb.append(tps);
                sb.append(",");
                sb.append(tpst);
                sb.append(",");
                double totalTime = (System.currentTimeMillis() - currentDate.getTime())/1000.0f;
                sb.append(totalTime);
                pwc.println(sb.toString());
            }


        } catch (FileNotFoundException e) {

            LOG.error("Error opening file",e);

        }finally{
            if(pwc!=null){
                pwc.close();
            }
        }
    }


}
