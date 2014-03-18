package com.gopivotal.poc.hh;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
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

    private PrintWriter pw;

    /**
     *
     * @param expConfig
     * @param context
     */
    public HHExp(ExpConfig expConfig, ApplicationContext context){
        this.nThreads = expConfig.getnThreads();
        this.nTransactions = expConfig.getnTransactions();
        this.batchSize = expConfig.getBatchSize();
        this.id = expConfig.getExpId();
        this.context = context;

        try {
            pw = new PrintWriter(Thread.currentThread().getName() + "_latency" + this.id + ".csv");
            pw.println("Threads: " + nThreads);
            pw.println("Transactions: " + nTransactions);
            pw.println("Batch size: " + batchSize);
            pw.flush();
        } catch (FileNotFoundException e) {
            LOG.error("error: file not found: latency.csv", e);
            pw = null;
        }

    }

    /**
     * Runs the experiment.
     * @return total time elapsed to run experiment.
     */
    public long execute(){

        long initTime = System.currentTimeMillis();
        LOG.info("Started experiment: " + id);
        long finalTime = 0;
        try{
            final ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
            final CountDownLatch cdl = new CountDownLatch(nThreads);
            for(int i = 0; i < nThreads ; i++){
                executorService.execute(new Runnable() {
                    final HitService hitService = context.getBean(HitService.class);
                    long actualTime = 0;

                    @Override
                    public void run() {

                        cdl.countDown();

                        try {
                            cdl.await();
                            LOG.debug("Thread " + Thread.currentThread().getName() + " started");
                        } catch (InterruptedException e) {
                            LOG.error("error: ",e);
                        }
                        for(int i = 0 ; i < nTransactions ; i++){
                            long processingTime = hitService.hit(batchSize);
                            actualTime += processingTime;
                            if(pw!=null) {
                                pw.println(processingTime);
                                pw.flush();
                            }
                        }
                        LOG.info("Thread TPS: " + (nTransactions * batchSize/(actualTime /1000.0f)));
                    }

                });
            }

            executorService.shutdown();
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);


            finalTime = System.currentTimeMillis();
            long totalTime = finalTime - initTime;
            LOG.debug("Time to finish Exp: " + totalTime);
            LOG.info("Finished experiment: " + id);
            LOG.info("TPS: " + (nThreads * nTransactions * batchSize/(totalTime /1000.0f)));


        }catch(Exception e){
            LOG.error("Error processing Experiment: " + id, e);
        }finally{
            if(pw!=null)
                pw.close();
        }
        return (finalTime - initTime);
    }

}
