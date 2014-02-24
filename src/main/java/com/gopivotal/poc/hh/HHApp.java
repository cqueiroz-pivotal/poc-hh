package com.gopivotal.poc.hh;


import net.sourceforge.argparse4j.ArgumentParsers;

import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cax
 */
class HHApp {

    private final static  Logger LOG = LoggerFactory.getLogger(HHApp.class);


    public static void main(String[] args) throws ArgumentParserException {


        ArgumentParser parser = ArgumentParsers.newArgumentParser("HHApp").description("Hit a DB to test throughput");
        parser.addArgument("-t")
                .dest("nThreads")
                .nargs("?")
                .type(Integer.class)
                .setDefault(10)
                .help("Spawn multiple threads to send data to the DB - Default: 10 threads");

        parser.addArgument("-b")
                .dest("batchSize")
                .nargs("?")
                .type(Integer.class)
                .setDefault(500)
                .help("Define size of the batch operation - Default: 500");

        parser.addArgument("-tr")
                .dest("nTransactions")
                .nargs("?")
                .type(Integer.class)
                .setDefault(1000)
                .help("Define number of operations/transactions with the DB (per thread) - Default: 1000");


        parser.addArgument("-exp")
                .dest("nExperiments")
                .nargs("?")
                .type(Integer.class)
                .setDefault(1)
                .help("Define number of experiments - Default: 1");


        final int nThreads = parser.parseArgs(args).getInt("nThreads");
        final int nTransactions = parser.parseArgs(args).getInt("nTransactions");
        final int batchSize = parser.parseArgs(args).getInt("batchSize");
        final int nExperiments = parser.parseArgs(args).getInt("nExperiments");



        LOG.info("Initialising experiments with the following config:");
        LOG.info("Threads: " + nThreads);
        LOG.info("Transactions: " + nTransactions);
        LOG.info("Batch size: " + batchSize);
        LOG.info("Experiments: " + nExperiments);

        final Stats stats = new Stats(nThreads,batchSize,nTransactions,nExperiments);


        for(int i = 0 ; i < nExperiments; i++){
            HHExp exp = new HHExp(i+1,nThreads,nTransactions,batchSize);
            long time = exp.execute();
            try {
                LOG.info("Sleeping for 5s");
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                LOG.error("error waiting thread",e);
            }
            stats.addMeasurement(i+1,time);
        }


        stats.computeAndSave();

    }
}
