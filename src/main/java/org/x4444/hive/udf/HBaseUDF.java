package org.x4444.hive.udf;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.x4444.hbase.HBaseService;

public class HBaseUDF extends UDF {

  HTable t = null;

  public Text evaluate(String quorum, String table, String key, String family, String col) {
    System.out.println("---------------------------------------");
    System.out.println("quorum: " + quorum);
    System.out.println("table: " + table);
    System.out.println("key: " + key);
    System.out.println("family: " + family);
    System.out.println("column: " + col);
    System.out.println("---------------------------------------");

    System.out.printf("Getting HTable...");
    if (t == null) {
      t = HBaseService.getHTable(quorum, table);
    }
    System.out.println("HTable: " + t);

    System.out.printf("Reading Column Value...");
    String v1 = HBaseService.readValue(t, key, family, col);
    System.out.println("Value: " + v1);

    System.out.println("---------------------------------------");
    System.out.println("All Done");
    System.out.println("---------------------------------------");

    return v1 != null ? new Text(v1) : null;
  }

  @Override
  protected void finalize() throws Throwable {
    IOUtils.closeQuietly(t);
  }
}
