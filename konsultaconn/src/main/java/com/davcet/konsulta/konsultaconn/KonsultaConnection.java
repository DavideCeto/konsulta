package com.davcet.konsulta.konsultaconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.davcet.konsulta.konsultacommon.env.EnvVariables;
import com.davcet.konsulta.konsultacommon.exception.ExceptionManager;

public final class KonsultaConnection {
	private final String url;
	private final String user;
	private final String pwd;
	private final Connection conn;
	
	private KonsultaConnection(Builder b) throws SQLException {
	  this.url = getDbProperty("KONSULTA_" + b.dbName + "_URL");
	  this.user = getDbProperty("KONSULTA_" + b.dbName + "_USER");
	  this.pwd = getDbProperty("KONSULTA_" + b.dbName + "_PWD");
	  this.conn = buildConnection(); 
	}

	/** KnonsultaConnection Builder **/
	public static class Builder {
    private String dbName;
    
    public KonsultaConnection build() throws SQLException {
      return new KonsultaConnection(this);
    }

    public Builder DBMS(String dbName) {
      this.dbName = dbName;
      return this;
    }
    
    public static final Builder New() {
      return new Builder();
    }
	}
	 
	public Connection getConnection() {
	  return this.conn;
	}

  public final void closeConnection() throws SQLException {
      if (!this.conn.isClosed()) this.conn.close();
  }

	private final Connection buildConnection() throws SQLException {
      
      Connection conn = DriverManager.getConnection(this.url, this.user, this.pwd);
      conn.setAutoCommit(false);
      return conn;
      
    
  }

  private String getDbProperty(String dbProp) {
    try {
      return EnvVariables.valueOf(dbProp).value();
    }
    catch (NullPointerException ex) {
      return null;
    }
  }

}
