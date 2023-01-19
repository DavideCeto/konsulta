package com.davcet.konsulta.konsultaconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.davcet.konsulta.konsultacommon.env.EnvVariables;

public final class KonsultaConnection {
	private final String url;
	private final String usr;
	private final String pwd;
	private final Connection conn;
	
	//private constructor
	private KonsultaConnection(Builder b) throws SQLException {
	  this.url = (b.dbName != null && !b.dbName.isEmpty()) ? 
	      EnvVariables.GET.dbUrl(b.dbName) : "";
	  
	  this.usr = (b.dbName != null && !b.dbName.isEmpty()) ? 
	      EnvVariables.GET.dbUser(b.dbName) : "";
	  
	  this.pwd = (b.dbName != null && !b.dbName.isEmpty()) ? 
	      EnvVariables.GET.dbPwd(b.dbName) : "";
	  
	  this.conn = buildConnection(); 
	}

	/** KonsultaConnection Builder **/
	public static class Builder {
    private String dbName;
    
    public final KonsultaConnection build() throws SQLException {
      return new KonsultaConnection(this);
    }

    public final Builder DBMS(String dbName) {
      this.dbName = dbName;
      return this;
    }
    
    public static final Builder New() {
      return new Builder();
    }
	}
	
	//=======================================================
	//public methods
	//=======================================================
	public final Connection getConnection() {
	  return this.conn;
	}

  public final void closeConnection() throws SQLException {
    if (!this.conn.isClosed()) this.conn.close();
  }

  public final void commit() throws SQLException {
    if (!this.conn.isClosed() && !this.conn.getAutoCommit()) this.conn.commit();
  }

  //=======================================================
  //private methods
  //=======================================================
	private final Connection buildConnection() throws SQLException {
	  Connection conn = DriverManager.getConnection(this.url, this.usr, this.pwd);
    conn.setAutoCommit(false);
    return conn;
  }

}
