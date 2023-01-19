package com.davcet.konsulta.konsultasql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.davcet.konsulta.konsultaconn.KonsultaConnection;

public final class SqlManager {  
  private final Connection conn;
  private static final String PATTERN_MATCH_UPDATE = 
      "DELETE|INSERT|UPDATE|CREATE|ALTER]";
  
  private static final Pattern pattern = 
      Pattern.compile(SqlManager.PATTERN_MATCH_UPDATE,Pattern.CASE_INSENSITIVE);
  
  //private constructor
  private SqlManager(Builder b) {
    this.conn = b.conn;
  }
  
  /** SqlManager Builder **/
  public final static class Builder {
    private Connection conn;

    public final SqlManager build() {
      return new SqlManager(this);
    }
    
    public final Builder connection(KonsultaConnection conn) {
      this.conn = conn.getConnection();
      return this;
    }

    public static final Builder New() {
      return new Builder();
    }    
  }
  
  //=======================================================
  //public methods
  //=======================================================

  /** Detect the type of sql statement (SELECT; INSERT; DELETE; UPDATE) 
   * and execute it **/
  public final SqlResult execute(String sql) throws SQLException {
    return execute(sql,0);
  }

  public final SqlResult execute(String sql, int restart) throws SQLException {
    switch (detectStatementType(sql)) {
      case EXECUTE_UPDATE:
        return executeUpdate(sql);
      case EXECUTE_SELECT:
        return executeSelect(sql, restart);
        
      default: return null;
    }
  }

  //=======================================================
  //private methods
  //=======================================================
  private enum statementType {
    EXECUTE_UPDATE,
    EXECUTE_SELECT;
  }

  private final statementType detectStatementType(String sql) {
    final Matcher matcher = pattern.matcher(sql);
    final statementType found = (Boolean.TRUE.equals(matcher.find())) ? 
        statementType.EXECUTE_UPDATE : statementType.EXECUTE_SELECT;
    
    return found;
  }
  
  private final SqlResult executeUpdate (String sql) throws SQLException {
    try(final Statement st = this.conn.createStatement()){
      
      final long n = st.executeUpdate(sql);
      final SqlResult res = SqlResult.Builder.New().affectedRows(n).build();
      return res;
      
    }
  }
  
  private final SqlResult executeSelect (String sql, int restart) 
  throws SQLException {
    
    try(final Statement st = this.conn.createStatement()){
      
      final ResultSet rs = st.executeQuery(sql);
      
      final long affected = selectCount(sql);
      final SqlResult res = SqlResult.Builder.New()
          .resultSet(rs)
          .affectedRows(affected)
          .restart(restart)
          .build();
      
      return res;
      
    }
  }
  
  private final long selectCount(String sql) throws SQLException {
    final StringBuilder sb = new StringBuilder();
    sb.append("SELECT COUNT(*) FROM (");
    sb.append(sql);
    sb.append(")");

    try(final Statement st = this.conn.createStatement()){

      ResultSet crs = st.executeQuery(sb.toString());
      crs.next();
      return crs.getLong(1);

    }
  }

}