package com.davcet.konsulta.konsultatest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.davcet.konsulta.konsultacommon.exception.ExceptionManager;
import com.davcet.konsulta.konsultaconn.KonsultaConnection;
import com.davcet.konsulta.konsultasql.SqlColumn;
import com.davcet.konsulta.konsultasql.SqlManager;
import com.davcet.konsulta.konsultasql.SqlResult;

final public class KonsultaTest {
  private KonsultaTest() {}
  
  public final static String testSqlManagerWithH2InMemoryDb() {
    
    try {

      KonsultaConnection conn = KonsultaConnection.Builder.New().DBMS("TEST_H2").build();
    
      StringBuilder test = new StringBuilder();
    
      
      SqlManager sqlManager = SqlManager.Builder.New().connection(conn).build();
      
      String sql = "CREATE TABLE TEST (Id int, Name varchar)";
      SqlResult res = sqlManager.execute(sql);
      test.append(sql + " " + res.getAffectedRows()).append("\n");
      
      sql = "INSERT INTO TEST VALUES(1,'David')";
      res = sqlManager.execute(sql);
      test.append(sql + " " + res.getAffectedRows()).append("\n");
      
      sql = "INSERT INTO TEST VALUES(2, 'Nadia')";
      res = sqlManager.execute(sql);
      test.append(sql + " " + res.getAffectedRows()).append("\n");
      
      sql = "SELECT * FROM TEST";
      res = sqlManager.execute(sql);
      test.append(sql + " " + res.getAffectedRows()).append("\n");

      ArrayList<String> cl = res.getColumnList();
      test.append("COLUMNS:").append("\n");
      cl.forEach(el -> {test.append(el);});
      
      ArrayList<ArrayList<Object>> r = res.getRowList();
      test.append("ROWS:").append("\n");
      r.forEach(elem ->{elem.forEach(el -> {test.append(el).append("\n");});});
      
      LinkedHashMap l = res.getSqlResultMapped();
      l.forEach((k,v) -> {
        SqlColumn s = (SqlColumn) v;
        test.append(s.getColumnSqlType()).append("\n");
        LinkedList<Object> values = s.getColumnValues();
        values.forEach(el -> {test.append(el).append("\n");});
      });
      
      sql = "DELETE FROM TEST";
      res = sqlManager.execute(sql);
      test.append(sql + " " + res.getAffectedRows()).append("\n");
      
      conn.closeConnection();
      test.append("OK").append("\n");
      return test.toString();
      
    } catch (Exception ex) {    
      return ExceptionManager.getStackTrace(ex);
    }
  }
  
  /*public static void main(String... args) {
    KonsultaTest ktest = new KonsultaTest();
    boolean test = ktest.testSqlManagerWithH2InMemoryDb();
    String sTest = (Boolean.valueOf(test).equals(Boolean.TRUE)) ? "OK" : "KO";
    System.out.println("TEST:" + sTest);
  }*/

}
