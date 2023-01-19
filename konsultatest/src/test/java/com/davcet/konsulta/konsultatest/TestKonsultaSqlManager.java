package com.davcet.konsulta.konsultatest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.davcet.konsulta.konsultaconn.KonsultaConnection;
import com.davcet.konsulta.konsultasql.SqlColumn;
import com.davcet.konsulta.konsultasql.SqlManager;
import com.davcet.konsulta.konsultasql.SqlResult;

@DisplayName("Test SqlManager/SqlColumn/SqlResult classes")
class TestKonsultaSqlManager {

  private static KonsultaConnection buildH2TestConnection() 
  throws SQLException {
    return KonsultaConnection.Builder.New().DBMS("TEST_H2").build();
  }

  private static SqlManager buildH2TestSqlManager(KonsultaConnection conn) 
  throws SQLException {
    
    return SqlManager.Builder.New()
        .connection(conn).build();
  }

  @DisplayName("Test H2")
  @Nested
  class testH2 {
    
    final KonsultaConnection conn;
    final SqlManager sqlManager; 

    testH2() throws SQLException{
      conn = TestKonsultaSqlManager.buildH2TestConnection();
      sqlManager = TestKonsultaSqlManager.buildH2TestSqlManager(conn);      
    }

    @Test
    @DisplayName("Create/Insert/Select/Delete from a H2 table called TEST")
    void testCreateInsertSelectDeleteH2() throws SQLException {
      String sql = "CREATE TABLE TEST (Id int, Name varchar)";
      SqlResult res = sqlManager.execute(sql);
      
      sql = "INSERT INTO TEST VALUES(1,'David')";
      res = sqlManager.execute(sql);
      assertTrue(res.getAffectedRows() == 1);
        
      sql = "INSERT INTO TEST VALUES(2, 'Nadia')";
      res = sqlManager.execute(sql);
      assertTrue(res.getAffectedRows() == 1);
      
      sql = "SELECT * FROM TEST";
      res = sqlManager.execute(sql);
      assertTrue(res.getAffectedRows() == 2);

      ArrayList<String> cl = res.getColNames();
      
      StringBuilder test = new StringBuilder();
      test.append("COLUMNS:").append("\n");
      cl.forEach(el -> {test.append(el).append("\n");});
        
      ArrayList<ArrayList<Object>> r = res.getValuesForEachRow();
      test.append("ROWS:").append("\n");
      r.forEach(elem ->{elem.forEach(el -> {test.append(el).append("\n");});});
        
      LinkedHashMap l = res.getResultMappedByColumns();
      l.forEach((k,v) -> {
        SqlColumn s = (SqlColumn) v;
        test.append(s.getSqlType()).append("\n");
        LinkedList<Object> values = s.getColumnValues();
        values.forEach(el -> {test.append(el).append("\n");});
      });
      
      sql = "DELETE FROM TEST";
      res = sqlManager.execute(sql);
      assertTrue(res.getAffectedRows() == 2);
    }

    @Test
    @DisplayName("Close H2 connection")
    void closeConnection() throws SQLException {
      conn.closeConnection();
    }

  } //nested H2 Test Class

}