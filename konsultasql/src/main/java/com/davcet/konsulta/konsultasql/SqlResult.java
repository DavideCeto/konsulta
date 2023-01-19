package com.davcet.konsulta.konsultasql;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public final class SqlResult {
  //key-value map: col id - col object (col values)
  private final LinkedHashMap<Integer,SqlColumn> columnMap; 
  
  private final ResultSet resultSet;
  private final long affectedRows;
  private final ArrayList<String> columnList;
  private final ArrayList<ArrayList<Object>> rowList;
  private final int restart;
  private final int fetchSize;

  //private constructor
  private SqlResult(Builder b) {
    this.columnMap = b.columnMap;
    this.resultSet = b.rs;
    this.affectedRows = b.affectedRows;
    this.columnList = b.columnList;
    this.rowList = b.rowList;
    this.restart = b.restart;
    this.fetchSize = (b.noFetch) ? -1 : Builder.FETCH_SIZE;
  }

  /** SqlResult Builder - package scope **/
  static final class Builder {
    private final LinkedHashMap<Integer,SqlColumn> columnMap = 
        new LinkedHashMap<>();
    
    private final ArrayList<String> columnList = new ArrayList<>();
    private final ArrayList<ArrayList<Object>> rowList = new ArrayList<>();
    private static final int FETCH_SIZE = 10000;
    private ResultSet rs;
    private long affectedRows;
    private int restart = 0;
    private boolean noFetch = false;
    
    final SqlResult build() throws SQLException {
      return mapSqlResult();
    }

    final Builder noFetchSize() {
      this.noFetch = true;
      return this;
    }
    
    final Builder restart(int n) {
      this.restart = n;
      return this;
    }
    
    final Builder affectedRows(long n) {
      this.affectedRows = n;
      return this;
    }

    final Builder resultSet(ResultSet rs) {
      this.rs = rs;
      return this;
    }

    static final Builder New() {
      return new Builder();
    }
    
    private final SqlResult mapSqlResult() throws SQLException {
        if (this.rs != null) {

          final ResultSetMetaData meta = this.rs.getMetaData();
          if (this.restart > 0) this.rs.absolute(restart);
          int rowCount = 0;
          
          while (rs.next() && (noFetch || rowCount < FETCH_SIZE)) {
            mapResult(meta, rowCount);
            rowCount++;
          }
        }

        return new SqlResult(this);
    }

    private final void mapResult(ResultSetMetaData meta, int rowCount) 
    throws SQLException {
      
      final ArrayList<Object> currentRowValues = new ArrayList<>();
      final int colCount = meta.getColumnCount();

      for (int i = 1; i <= colCount; i++) {
        final SqlColumn currentCol = (rowCount == 0) ? 
            initializeColumn(meta, i) : this.columnMap.get(i);
        
        final Object colValue = castColumnValue(i, currentCol.getSqlType());

        currentCol.addValue(colValue);
        currentRowValues.add(colValue);
        this.columnMap.put(i, currentCol);
      }

      this.rowList.add(currentRowValues);      
    }

    private final SqlColumn initializeColumn(ResultSetMetaData meta, int i) 
    throws SQLException {

        this.columnList.add(meta.getColumnName(i));
        JDBCType type = JDBCType.valueOf(meta.getColumnType(i));
        return new SqlColumn(meta.getColumnName(i), type);
    }

    private final Object castColumnValue(int index, JDBCType type) {
      try {
        
        switch (type) {
          case ARRAY:  return this.rs.getArray(index);
          case BIGINT: return this.rs.getLong(index);
          case BINARY: return this.rs.getBinaryStream(index);
          case BIT: return this.rs.getInt(index);
          case BLOB: return this.rs.getBlob(index);
          case BOOLEAN: return this.rs.getBoolean(index);
          case CHAR: return this.rs.getString(index);
          case CLOB: return this.rs.getClob(index);
          case DATALINK: return this.rs.getURL(index);
          case DATE: return this.rs.getDate(index);
          case DECIMAL: return this.rs.getBigDecimal(index);
          case DISTINCT: return this.rs.getBinaryStream(index);
          case DOUBLE: return this.rs.getDouble(index);
          case FLOAT: return this.rs.getFloat(index);
          case INTEGER: return this.rs.getInt(index);
          case JAVA_OBJECT: return this.rs.getObject(index);
          case LONGNVARCHAR: return this.rs.getNString(index);
          case LONGVARBINARY: return this.rs.getBinaryStream(index);
          case LONGVARCHAR: return this.rs.getString(index);
          case NCHAR: return this.rs.getNString(index);
          case NCLOB: return this.rs.getNClob(index);
          case NULL: return null;
          case NUMERIC: return this.rs.getBigDecimal(index);
          case NVARCHAR: return this.rs.getNString(index);
          case OTHER:return this.rs.getBinaryStream(index);
          case REAL: return this.rs.getFloat(index);
          case REF: return this.rs.getRef(index);
          case REF_CURSOR: return this.rs.getCursorName();
          case ROWID: return this.rs.getRowId(index);
          case SMALLINT: return this.rs.getShort(index);
          case SQLXML: return this.rs.getSQLXML(index);
          case STRUCT: return this.rs.getArray(index);
          case TIME: return this.rs.getTime(index);
          case TIME_WITH_TIMEZONE: return this.rs.getTime(index);
          case TIMESTAMP: return this.rs.getTimestamp(index);
          case TIMESTAMP_WITH_TIMEZONE: return this.rs.getTimestamp(index);
          case TINYINT: return this.rs.getShort(index);
          case VARBINARY: return this.rs.getBinaryStream(index);
          case VARCHAR: return this.rs.getString(index);

          default: return this.rs.getBinaryStream(index);
        }
        
      }
      catch (SQLException ex) {
        return null;
      }
    }
  } //end Builder

  //=====================================================
  //public methods
  //=====================================================

  /** Return key-value map: 
   * col number id (start from 1) - col object (col values)**/
  public final LinkedHashMap<Integer,SqlColumn> getResultMappedByColumns() {
    return this.columnMap;
  }

  /** Return all the ResultSet (only for select statement) **/
  public final ResultSet getResultSet() {
    return this.resultSet;
  }
  
  /** Return affected rows **/
  public final long getAffectedRows() {
    return this.affectedRows;
  }

  /** Return columns names **/
  public final ArrayList<String> getColNames() {
    return this.columnList;
  }
  
  /** Return column name by column id **/
  public final String getColNameById(int id) {
    return this.columnList.get(id);
  }

  /** Return all rows values**/
  public final ArrayList<ArrayList<Object>> getValuesForEachRow() {
    return this.rowList;
  }
  
  /** Return row values by row id **/
  public final ArrayList<Object> getRowValuesById(int id) {
    return this.rowList.get(id);
  }

  /** Return last restart **/
  public final int getRestartPoint() {
    return this.restart;
  }

  /** Return fetch size or -1 if no fetch size **/
  public final int getFetchSize() {
    return this.fetchSize;
  }

}