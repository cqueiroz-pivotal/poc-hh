package com.gopivotal.poc.hh;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by cax on 2/21/14.
 */
class HHExp {

    private final Logger LOG = LoggerFactory.getLogger(HHExp.class);

    private final int nThreads;

    private final int nTransactions;

    private final int batchSize;

    private final int id;

    private final ApplicationContext context;
    /**
     *
     * @param nThreads
     * @param nTransactions
     * @param batchSize
     */
    public HHExp(int id,  int nThreads, int nTransactions, int batchSize){
        this.nThreads = nThreads;
        this.nTransactions = nTransactions;
        this.batchSize = batchSize;
        this.id = id;
        context = new ClassPathXmlApplicationContext("spring-config.xml");

    }

    public long execute(){

        long initTime = System.currentTimeMillis();
        LOG.info("Started experiment: " + id);
        long finalTime = 0;
        try{
            final ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

            for(int i = 0; i < nThreads ; i++){
                executorService.execute(new Runnable() {
                    final HitService hitService = context.getBean(HitService.class);
                    long actualTime = 0;

                    @Override
                    public void run() {

                        for(int i = 0 ; i < nTransactions ; i++){
                            long processingTime = hitService.hit(batchSize);
                            actualTime += processingTime;
                        }
                        hitService.shutdown();

                    }

                });
            }

            executorService.shutdown();
            executorService.awaitTermination(60, TimeUnit.SECONDS);

            finalTime = System.currentTimeMillis();
            long totalTime = finalTime - initTime;
            LOG.info("Finished experiment: " + id);
            LOG.info("TPS: " + (nThreads * nTransactions * batchSize/(totalTime /1000.0f)));

        }catch(Exception e){
            LOG.error("Error processing Experiment: " + id, e);
        }
        return (finalTime - initTime);
    }

}
