poc-hh
======

simple java app for test throughput of GemFireXD/SQLFire.

to compile:
mvn package

to run:
java -jar target/HHApp.jar -t <number of threads> -tr <number of transactions> -b <batch size> -exp <number of experiments>
