package com.davcet.konsulta.konsultasql;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.davcet.konsulta.konsultacommon.exception.ExceptionManager;

public final class SqlResult {
  private final LinkedHashMap<Integer,SqlColumn> mapResult; //key-value map: col id - col object (col values)
  private final ResultSet resultSet;
  private final long affectedRows;
  private final ArrayList<String> columnList;
  private final ArrayList<ArrayList<Object>> rowList;

  private SqlResult(Builder b) {
    this.mapResult = b.mapResult;
    this.resultSet = b.rs;
    this.affectedRows = b.affectedRows;
    this.columnList = b.columnList;
    this.rowList = b.rowList;
  }

  /** SqlResult Builder **/
  final static class Builder {
    private LinkedHashMap<Integer,SqlColumn> mapResult = new LinkedHashMap<>();
    private ResultSet rs;
    private long affectedRows;
    private ArrayList<String> columnList = new ArrayList<>();
    private final ArrayList<ArrayList<Object>> rowList = new ArrayList<>();
    
    final SqlResult build() throws SQLException {
      return mapSqlResult();
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
    
    private SqlResult mapSqlResult() throws SQLException {
      
        if (this.rs != null) {
          
          final ResultSetMetaData meta = this.rs.getMetaData();
          final int columnCount = meta.getColumnCount();
  
          while (rs.next()) {
            
            ArrayList<Object> rowValues = new ArrayList<>();
            
            for (int i = 1; i <= columnCount; i++) {

              if (rs.getRow() == 1) {
                this.columnList.add(meta.getColumnName(i));
              }

              JDBCType type = JDBCType.valueOf(meta.getColumnType(i));
              SqlColumn tempColumn = (rs.getRow() == 1) ? new SqlColumn(meta.getColumnName(i), type) : this.mapResult.get(i);
              Object columnValue = castColumnValue(i, type);
              tempColumn.addValue(columnValue);
              rowValues.add(columnValue);

              //This map will contains, for every column, all it's values
              this.mapResult.put(i, tempColumn);
            }

            this.rowList.add(rowValues);            
          }
        }
        
        return new SqlResult(this);

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
  } //End Builder

  /** Return key-value map: col number id (start from 1) - col object (col values)**/
  public final LinkedHashMap<Integer,SqlColumn> getSqlResultMapped() {
    return this.mapResult;
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
  public final ArrayList<String> getColumnList() {
    return this.columnList;
  }
  
  /** Return column name by column id **/
  public final String getColumnNameById(int id) {
    return this.columnList.get(id);
  }

  /** Return all rows values**/
  public final ArrayList<ArrayList<Object>> getRowList() {
    return this.rowList;
  }
  
  /** Return row values by row id **/
  public final ArrayList<Object> getRowValuesById(int id) {
    return this.rowList.get(id);
  }

}