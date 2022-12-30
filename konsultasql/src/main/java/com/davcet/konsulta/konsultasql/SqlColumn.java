package com.davcet.konsulta.konsultasql;

import java.sql.JDBCType;
import java.util.LinkedList;

public final class SqlColumn {
  private final String name;  
  private final LinkedList<Object> values;
  private final JDBCType sqlType;

  SqlColumn(String name, JDBCType sqlType) {
    this.name = name;
    this.sqlType = sqlType;
    this.values = new LinkedList<Object>();
  }

  final void addValue(Object val){
    this.values.add(val);
  }

  public final String getColumnName() {
    return this.name;
  }
  
  public final LinkedList<Object> getColumnValues() {
    return this.values;
  }

  public final JDBCType getColumnSqlType() {
    return this.sqlType;
  }

}
