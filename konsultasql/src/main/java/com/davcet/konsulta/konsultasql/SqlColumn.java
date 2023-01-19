package com.davcet.konsulta.konsultasql;

import java.sql.JDBCType;
import java.util.LinkedList;

public final class SqlColumn {
  private final String name;  
  private final LinkedList<Object> values;
  private final JDBCType sqlType;

  //package scope constructor
  SqlColumn(String name, JDBCType sqlType) {
    this.name = name;
    this.sqlType = sqlType;
    this.values = new LinkedList<Object>();
  }

  //=======================================================
  //public methods
  //=======================================================
  public final String getColumnName() {
    return this.name;
  }
  
  public final LinkedList<Object> getColumnValues() {
    return this.values;
  }

  public final JDBCType getSqlType() {
    return this.sqlType;
  }

  //=======================================================
  //package scope methods
  //=======================================================
  final void addValue(Object val){
    this.values.add(val);
  }

}
