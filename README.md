# hive-hbase
The project tests hbase client and HBase UDF in Hive

## Build
To build the project run
```
mvn clean package
```

## HBase preparation
```
$ hbase shell

create 'alex.test','f'
put 'alex.test','k1','f:c1','value1'
put 'alex.test','k2','f:c1','value2'
```
## Test HBase client
Replace ip-10-101-124-13 with correct HBase quorum.
```
./run.sh ip-10-101-124-13 alex.test k1 f c1
```
client should return
```
Value: value1
```

## Hive Preparation
Open hive with MR engine
```
$ hive --hiveconf hive.execution.engine=mr

use default;
create table test (k string);
insert into test values('k1');
insert into test values('k2');
insert into test values('none');
```

## Test HBaseUDF
Open hive with MR engine
```
$ hive --hiveconf hive.execution.engine=mr
```
Run the following lines
(Fix paths to jars if necessary)
```
use default;
add jar /usr/lib/hbase/lib/hbase-common-0.98.17-hadoop2.jar;
add jar /usr/lib/hbase/lib/hbase-client-0.98.17-hadoop2.jar;
add jar /usr/lib/hbase/lib/hbase-protocol-0.98.17-hadoop2.jar;
add jar /usr/lib/hbase/lib/htrace-core-2.04.jar;
add jar /usr/lib/hbase/lib/metrics-core-2.2.0.jar;
add jar /home/hadoop/work/hive-hbase/target/hive-hbase-1.0.jar;

CREATE TEMPORARY FUNCTION hive_hbase_alex AS 'org.x4444.hive.udf.HBaseUDF';

-- test local mode (Replace ip-10-101-124-13 with correct HBase quorum.)

select k, hive_hbase_alex("ip-10-101-124-13", "alex.test", k, "f", "c1") from test;

-- test MR mode (Replace ip-10-101-124-13 with correct HBase quorum.)

select distinct k, hive_hbase_alex("ip-10-101-124-13", "alex.test", k, "f", "c1") from test;
```
Both queries should return
```
k1	value1
k2	value2
none	NULL
```
