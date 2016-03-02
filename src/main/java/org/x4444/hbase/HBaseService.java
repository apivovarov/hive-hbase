package org.x4444.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseService {

  static String readValue(HTable t1, String rowKey, String family, String field) {
    try {
      Get get = new Get(Bytes.toBytes(rowKey));
      long t = System.currentTimeMillis();
      Result res = t1.get(get);
      t = System.currentTimeMillis() - t;
      byte[] famB = Bytes.toBytes(family);
      byte[] fieldB = Bytes.toBytes(field);
      String fieldV = Bytes.toString(res.getValue(famB, fieldB));
      System.out.println("fieldV: " + fieldV);
      return fieldV;
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  static HTable getHTable(String master, String tableName) {
    try {
      Configuration conf = getConf(master);
      HTable t1 = new HTable(conf, Bytes.toBytes(tableName));
      return t1;
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  static Configuration getConf(String master) {
    Configuration conf = HBaseConfiguration.create();
    conf.set("hbase.zookeeper.quorum", master);
    conf.set("fs.default.name", "hdfs://" + master + ":8020");
    conf.set("hbase.client.retries.number", "1");
    conf.set("zookeeper.recovery.retry", "1");
    conf.set("hbase.rpc.timeout", "10000");

    return conf;
  }
}
