package org.x4444.hbase;

import org.apache.hadoop.hbase.client.HTable;
import org.junit.Test;

public class TestHBaseService {

  @Test
  public void testGetHTable() {
    HTable t = HBaseService.getHTable("localhost", "test");
    System.out.println(t);

    // String v1 = HBaseService.readValue(t, "1", "f", "v1");
    // System.out.println(v1);
  }

}
