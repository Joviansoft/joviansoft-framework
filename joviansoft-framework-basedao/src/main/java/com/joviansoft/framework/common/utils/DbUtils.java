package com.joviansoft.framework.common.utils;

import java.sql.*;
import java.io.*;

/**
 *
 * <p>Title: Prodb</p>
 * <p>Description: 常住人口管理信息系统（二代证）省级库</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: GNT(ZJHZ)</p>
 * @author kgb_hz@126.com,kgb@primetech.com.cn
 * @version 1.0
 */
public class DbUtils {
  private DbUtils() {
  }

////////////////////////////////////////////////////////////////////////////
//Add By KGB 2005-04-08
////////////////////////////////////////////////////////////////////////////
  /**
   * Add By KGB 将Blob文件转换为byte[]字节流
   * @param blob
   * @return
   */
  public static byte[] parseBlobToBytes(Blob blob) {
    byte[] abyte0 = null;
    if (blob == null) {
      return abyte0;
    }
    try {
      InputStream inputstream = blob.getBinaryStream();
      if (null != inputstream) {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(
            1024);
        int j;
        while ( (j = inputstream.read()) != -1) {
          bytearrayoutputstream.write(j);
        }
        inputstream.close();
        abyte0 = bytearrayoutputstream.toByteArray();
        bytearrayoutputstream.close();
      }
    }
    catch (Exception ex) {
    }
    return abyte0;
  }

  /**
   *
   * @param m_ResultSet
   * @param i
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static byte[] getImageToBytes(ResultSet m_ResultSet, int i) throws
      SQLException, IOException {
    //return m_ResultSet.getBytes(i);
    byte abyte0[] = null;
    try {
      InputStream inputstream = m_ResultSet.getBinaryStream(i);
      if (null != inputstream) {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(
            1000);
        int j;
        while ( (j = inputstream.read()) != -1) {
          bytearrayoutputstream.write(j);
        }
        inputstream.close();
        abyte0 = bytearrayoutputstream.toByteArray();
        bytearrayoutputstream.close();
      }
      else {
        abyte0 = new byte[0];
      }
    }
    catch (IOException ex) {
      throw new IOException(ex.getMessage());
    }
    catch (SQLException ex) {
      throw new SQLException(ex.getMessage());
    }
    return abyte0;
  }

  /**
   *
   * @param rs
   * @return
   */
  public static String genSqlClauseByPK(ResultSet rs) {
    if (rs == null) {
      return "";
    }
    StringBuffer whereSb = new StringBuffer();
    try {
      ResultSetMetaData rsmd = rs.getMetaData();
      int iColumns = rsmd.getColumnCount();
      if (iColumns >= 1) {
        whereSb.append(" (");
      }
      for (int i = 1; i <= iColumns; i++) {
        if (i == 1) {
          whereSb.append(rsmd.getColumnName(i) + "=");
        }
        else {
          whereSb.append(" and " + rsmd.getColumnName(i) + "=");
        }
        switch (rsmd.getColumnType(i)) {
          case Types.CHAR:
          case Types.VARCHAR:
//            whereSb.append("'" + StringUtils.formatTrim(rs.getString(i)) + "' ");
            whereSb.append("'" + rs.getString(i) + "' ");
            break;
          case Types.INTEGER:
          case Types.BIGINT:
          case Types.TINYINT:
          case Types.FLOAT:
          case Types.DOUBLE:
            whereSb.append(String.valueOf(rs.getInt(i)) + " ");
            break;
          case Types.DATE:
            break;
          default:
            break;
        }
      }
      if (iColumns >= 1) {
        whereSb.append(") ");
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    return whereSb.toString();
  }

  /**
   * after get Records Close Connection for clear memory Resource
   * @param conn
   */
  public static void close(Connection conn) {
    if (conn != null) {
      try {
        conn.close();
      }
      catch (SQLException ex) {
        ex.printStackTrace();
      }
      conn = null;
    }
  }

  /**
   * after ExcuteQuery free the PreparedStatement  memory Resource
   * @param ps
   */
  public static void close(Statement ps) {
    if (ps != null) {
      try {
        ps.close();
      }
      catch (SQLException ex) {
        ex.printStackTrace();
      }
      ps = null;
    }
  }

  /**
   * after ExcuteQuery free ResultSet  memory resource
   * @param rs
   */
  public static void close(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      }
      catch (SQLException ex) {
        ex.printStackTrace();
      }
      rs = null;
    }
  }

  /**
   * if the Databse Excute throw exception rollback all Operations
   * @param conn
   */
  public static void rollback(Connection conn) {
    if (conn != null) {
      try {
        conn.rollback();
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
      conn = null;
    }
  }

  /**
   * in the Transation commit by programmer
   * @param conn
   */
  public static void commit(Connection conn) {
    if (conn != null) {
      try {
        conn.commit();
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * if Excute a few Operations: setAutoCommit(false) in the Transation and Commit by programer
   * else if setAutoCommit(True) then commit by Jdbc itself
   * @param conn
   * @param isAutoCommit
   */

  public static void setAutoCommit(Connection conn, boolean isAutoCommit) {
    if (conn != null) {
      try {
        conn.setAutoCommit(isAutoCommit);
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

}