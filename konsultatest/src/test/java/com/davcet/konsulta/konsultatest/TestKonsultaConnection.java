package com.davcet.konsulta.konsultatest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.davcet.konsulta.konsultacommon.env.EnvVariables;
import com.davcet.konsulta.konsultaconn.KonsultaConnection;

class TestKonsultaConnection {
  
  @Test
  @DisplayName("When you 'build' the KonsultaConnection and you pass an empty "
      + " or null DBMS, you should get an SQLException")
  void testEmptyOrNullConnection() {
    assertThrowsExactly(java.sql.SQLException.class, () -> {
        KonsultaConnection.Builder.New().DBMS("").build();
    });
    
    assertThrowsExactly(java.sql.SQLException.class, () -> {
      KonsultaConnection.Builder.New().DBMS(null).build();
    });
    
  }
  
  @Test
  @DisplayName("When you 'build' the KonsultaConnection and you pass "
      + "a 'bad' DBMS, you should get an SQLException")
  void testBadNameDBMSConnection() {
    assertThrowsExactly(java.sql.SQLException.class, () -> {
        KonsultaConnection.Builder.New().DBMS("BAD!").build();
    });
    
  }
  
  @Test
  @DisplayName("Read all the DBMS names from environmente variables, "
      + "and for each get a valid java sql connection")

  List<KonsultaConnection> testAllConnectionsFromEnvVariables() 
  throws SQLException {
    
    List<String> dbNames = EnvVariables.GET.dbNames();
    
    assertNotNull(dbNames);
    assertFalse(dbNames.isEmpty());

    List<KonsultaConnection> connList = new ArrayList<>();
    
    for (String dbName : dbNames) {
      System.out.println("-----------");
      System.out.println("DBMS NAME:" + dbName);
      KonsultaConnection conn = KonsultaConnection.Builder
          .New().DBMS(dbName).build();
      
      Connection c = conn.getConnection();
      assertNotNull(c);
      System.out.println("Test Connection OK");
      System.out.print("-----------");
      
      connList.add(conn);
    }
    
    return connList;
  }

}