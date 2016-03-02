package org.x4444.hbase;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.client.HTable;

public class Main {

  public static void main(String[] args) {

    System.out.println(args.length);
    if (args.length != 5) {
      System.out.printf("Usage: org.x4444.hbase.Main <quorum> <table> <key> <family> <field>");
      throw new RuntimeException("noargs");
    }

    String quorum = args[0];
    String table = args[1];
    String key = args[2];
    String family = args[3];
    String field = args[4];

    System.out.println("---------------------------------------");
    System.out.println("quorum: " + quorum);
    System.out.println("table: " + table);
    System.out.println("key: " + key);
    System.out.println("family: " + family);
    System.out.println("field: " + field);
    System.out.println("---------------------------------------");

    System.out.printf("Getting HTable...");
    HTable t = HBaseService.getHTable(quorum, table);
    System.out.println("HTable: " + t);

    try {
      System.out.printf("Reading Field Value...");
      String v1 = HBaseService.readValue(t, "1", "f", "v1");
      System.out.println("Value: " + v1);

      System.out.println("---------------------------------------");
      System.out.println("All Done");
      System.out.println("---------------------------------------");
    } finally {
      IOUtils.closeQuietly(t);
    }
  }
}
