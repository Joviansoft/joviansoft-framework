package com.joviansoft.framework.dao.jdbc;

import com.joviansoft.framework.common.utils.DbUtils;
import com.joviansoft.framework.dao.IJdbcDao;
import com.joviansoft.framework.exception.DAOException;
import com.joviansoft.framework.query.FieldDef;
import com.joviansoft.framework.query.IResultSetListMap;
import com.joviansoft.framework.query.MetaData;
import com.joviansoft.framework.query.ResultSetListMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JdbcDao extends JdbcDaoSupport implements IJdbcDao {

    protected static Log _log = LogFactory.getLog(JdbcDao.class);


    /**
     * 构造返回分页的SQl语句
     * 只适用于Oracle
     *
     * @param sql
     * @return
     */
    protected String getLimitString(String sql) {
        return getOracleLimitString(sql);
    }

    /**
     * @param sql
     * @return
     */
    protected String getOracleLimitString(String sql) {
        StringBuffer pagingSelect = new StringBuffer(255);
        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        pagingSelect.append(sql);
        pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
        return pagingSelect.toString();
    }

    /**
     * @param sql
     * @return
     */
    protected String getSqlServerLimitString(String sql) {
        return sql;
    }

    /**
     * @param strSQL
     * @param iOffset
     * @param iPagesize
     * @return
     */
    public IResultSetListMap getOnePageList(final String strSQL,
                                            final int iOffset,
                                            final int iPagesize) throws
            DAOException {

        //调用PreparedStatetement Select Sql语句的执行类
        class ExecutePagePreparedStatementCallback
                implements PreparedStatementCallback {

            /**
             *
             * @param ps
             * @return
             * @throws SQLException
             * @throws DataAccessException
             */
            public Object doInPreparedStatement(PreparedStatement ps) throws
                    SQLException,
                    DataAccessException {
                //调用执行得到返回值
                return executeReturnExtractor(ps);
            }

            /**
             *
             * @param ps
             * @return
             * @throws SQLException
             */
            private IResultSetListMap executeReturnExtractor(PreparedStatement ps) throws
                    SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    //=====================================================================
                    //执行表达式语句
                    //=====================================================================
                    ps.setInt(1, iOffset + iPagesize);
                    ps.setInt(2, iOffset);
                    _log.debug("iOffset + iPagesize:" + (iOffset + iPagesize));
                    _log.debug("iOffset:" + (iOffset));
                    rs = ps.executeQuery();
                    //=====================================================================
                    //组织返回结果集
                    //=====================================================================
                    return processRs(rs);
                } finally {
                    DbUtils.close(rs);
                }
            }

        }

        /**
         * 调用执行类，返回执行的结果集
         */
        try {
            _log.info("JdbcDao:" + strSQL);
            return (IResultSetListMap) this.getJdbcTemplate().execute(getLimitString(
                    strSQL),
                    new ExecutePagePreparedStatementCallback());
        } catch (Exception ex) {
            throw new DAOException("分页查询数据发生异常", ex);
        }

    }


    ///////////////////////////////////////////////////////////////////
    //Oracle数据库的类型定义
    public abstract class OracleTypes {
        // Fields
        public static final int BIT = -7;
        public static final int TINYINT = -6;
        public static final int SMALLINT = 5;
        public static final int INTEGER = 4;
        public static final int BIGINT = -5;
        public static final int FLOAT = 6;
        public static final int REAL = 7;
        public static final int DOUBLE = 8;
        public static final int NUMERIC = 2;
        public static final int DECIMAL = 3;
        public static final int CHAR = 1;
        public static final int VARCHAR = 12;
        public static final int LONGVARCHAR = -1;
        public static final int DATE = 91;
        public static final int TIME = 92;
        public static final int TIMESTAMP = 93;
        public static final int TIMESTAMPNS = -100;
        public static final int TIMESTAMPTZ = -101;
        public static final int TIMESTAMPLTZ = -102;
        public static final int BINARY = -2;
        public static final int VARBINARY = -3;
        public static final int LONGVARBINARY = -4;
        public static final int ROWID = -8;
        public static final int CURSOR = -10;
        public static final int BLOB = 2004;
        public static final int CLOB = 2005;
        public static final int BFILE = -13;
        public static final int STRUCT = 2002;
        public static final int ARRAY = 2003;
        public static final int REF = 2006;
        public static final int OPAQUE = 2007;
        public static final int JAVA_STRUCT = 2008;
        public static final int JAVA_OBJECT = 2000;
        public static final int PLSQL_INDEX_TABLE = -14;
        public static final int NULL = 0;
        public static final int NUMBER = 2;
        public static final int RAW = -2;
        public static final int OTHER = 1111;
        public static final int FIXED_CHAR = 999;

        // Constructors
        public OracleTypes() {
        }
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    private IResultSetListMap processRs(ResultSet rs) throws SQLException {
        //返回为空
        if (rs == null) {
            return null;
        }
        //=====================================================================
        //组织返回结果集
        //=====================================================================
        IResultSetListMap rslm = new ResultSetListMap();
        //=====================================================================
        //循环处理记录集数据
        //=====================================================================
        ResultSetMetaData rsmd = rs.getMetaData();
        int iColCount = rsmd.getColumnCount();
        //转换查询数据元数据定义
        MetaData metadata = new MetaData();
        for (int n = 1; n <= iColCount; n++) {
            FieldDef fd = new FieldDef();
            fd.setFieldName(rsmd.getColumnName(n));
            fd.setFieldType(rsmd.getColumnType(n));
            fd.setFieldTypeName(rsmd.getColumnTypeName(n));
            fd.setFieldWidth(rsmd.getColumnDisplaySize(n));
            fd.setDisplayName(rsmd.getColumnLabel(n));
            metadata.addFieldDef(fd);
        }
        rslm.setMetaData(metadata);
        //转换查询数据结果集
        while (rs.next()) {
            Map mapOneRecord = new HashMap();
            for (int i = 1; i <= iColCount; i++) {
                String strFieldName = rsmd.getColumnName(i).trim().toUpperCase(); //键值（字段名称）小写
                Object objFieldValue = null;
                switch (rsmd.getColumnType(i)) {
                    //数据库中Blob或LongRaw数据，二进制要进行Base64编码
                    case OracleTypes.BLOB:

                        //取出此BLOB对象 数据库照片字段改为Blob,20070420 by hx
//	            if (rs.getClass().getName().toString().equals( //weblogic的连接池
//	                "weblogic.jdbc.wrapper.ResultSet_oracle_jdbc_driver_OracleResultSetImpl")) {
//	              //_log.info("RS TYPE:" + rs.getClass().getName().toString());
//	              weblogic.jdbc.wrapper.Blob blob = (weblogic.jdbc.wrapper.Blob) rs.
//	                  getBlob(strFieldName);
//	              oracle.sql.BLOB oblob = (oracle.sql.BLOB) blob.getVendorObj();
//	              //直接转换为byte
//	              InputStream output = oblob.getBinaryStream();
//	              try {
//	                //System.out.println(output.toString());
//	                objFieldValue = getBytes(output);
//	              }
//	              catch (Exception ex) {
//	                objFieldValue = null;
//	              }
//	            }

                        if (rs.getClass().getName().toString().equals( //webSphere的连接池
                                "com.ibm.ws.rsadapter.jdbc.WSJdbcResultSet")) {
                            oracle.sql.BLOB oblob = (oracle.sql.BLOB) rs.getBlob(strFieldName);
                            //直接转换为byte
                            InputStream output = oblob.getBinaryStream();
                            try {
                                //System.out.println(output.toString());
                                objFieldValue = getBytes(output);
                            } catch (Exception ex) {
                                objFieldValue = null;
                            }
                        }


                        if (rs.getClass().getName().toString().equals("org.apache.commons.dbcp.DelegatingResultSet") //jdbc的连接池
                                || rs.getClass().getName().toString().equals("com.mchange.v2.c3p0.impl.NewProxyResultSet") //c3po的连接
                                ) {
                            java.sql.Blob oblob = (java.sql.Blob) rs.getBlob(strFieldName);
                            //直接转换为byte
                            InputStream output = oblob.getBinaryStream();
                            try {
                                //System.out.println(output.toString());
                                objFieldValue = getBytes(output);
                            } catch (Exception ex) {
                                objFieldValue = null;
                            }
                        }


                        break;
                    case OracleTypes.BINARY:
                    case OracleTypes.VARBINARY:
                    case OracleTypes.LONGVARBINARY:
                    case OracleTypes.CLOB:
                    case OracleTypes.ARRAY:
                        objFieldValue = rs.getBytes(i);
                        break;
//	          case OracleTypes.NUMBER:
//	        	  objFieldValue = rs.getLong(i);
//	        	  break;
                    default:
                        String strRs = rs.getString(i);
                        objFieldValue = strRs == null ? "" : strRs; //所有字段对应的值，取字符串值
                        break;
                }
                mapOneRecord.put(strFieldName, objFieldValue);
            }
            rslm.addOneRow(mapOneRecord);
        }
        return rslm;

    }

    /**
     * @param strSQL
     * @return
     */
    public int getTotalRecordCount(String strSQL) throws DAOException {
        try {

            return this.getJdbcTemplate().queryForObject(strSQL, Integer.class);
            // return this.getJdbcTemplate().queryForInt(strSQL);
        } catch (DataAccessException ex) {
            throw new DAOException("查询所有记录数发生异常", ex);
        }
    }
    /**
     * @param strSQL
     * @return
     */
    public IResultSetListMap getAllList(String strSQL) throws DAOException {

        //调用PreparedStatetement Select Sql语句的执行类
        class ExecuteAllPreparedStatementCallback
                implements PreparedStatementCallback {

            /**
             *
             * @param ps
             * @return
             * @throws SQLException
             * @throws DataAccessException
             */
            public Object doInPreparedStatement(PreparedStatement ps) throws
                    SQLException,
                    DataAccessException {
                //调用执行得到返回值
                return executeReturnExtractor(ps);
            }

            /**
             *
             * @param ps
             * @return
             * @throws SQLException
             */
            private IResultSetListMap executeReturnExtractor(PreparedStatement ps) throws
                    SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    //=====================================================================
                    //执行表达式语句
                    //=====================================================================
                    rs = ps.executeQuery();
                    //=====================================================================
                    //组织返回结果集
                    //=====================================================================
                    return processRs(rs);
                } finally {
                    DbUtils.close(rs);
                }
            }

        }

        /**
         * 调用执行类，返回执行的结果集
         */
        try {
            _log.info("JdbcDao:" + strSQL);

            return (IResultSetListMap) this.getJdbcTemplate().execute(strSQL,
                    new ExecuteAllPreparedStatementCallback());
        } catch (Exception ex) {
            throw new DAOException("查询所有数据列表发生异常", ex);
        }
    }

    /**
     * @param strSql
     * @throws DAOException
     */
    public void execute(String strSql) throws DAOException {
        try {
            this.getJdbcTemplate().execute(strSql);
        } catch (DataAccessException ex) {
            throw new DAOException("执行数据库更新操作时发生异常，", ex);
        }
    }

    /**
     * @param strSqlCall
     */
    public void executeSpCall(String strSqlCall) throws DAOException {

        //执行存储过程调用的类
        //传入参数Null
        //传输参数Null
        class SimpleCallableStatementCallback
                implements CallableStatementCallback {
            public Object doInCallableStatement(CallableStatement cs) throws
                    SQLException, DataAccessException {
                cs.execute();
                return null;
            }
        }

        try {
            this.getJdbcTemplate().execute(strSqlCall,
                    new SimpleCallableStatementCallback());
        } catch (Exception ex) {
            //System.out.println(ex.toString());
            throw new DAOException("执行数据库存储过程发生异常，", ex);
        }

    }

    /**
     * @param sql
     * @param action
     * @return
     */
    public Object execute(final String sql, PreparedStatementCallback action) {
        return this.getJdbcTemplate().execute(sql, action);
    }

    /**
     * @param sql
     * @param action
     * @return
     */
    public Object execute(final String sql, CallableStatementCallback action) {
        return this.getJdbcTemplate().execute(sql, action);
    }

    /**
     * InputStream转换为byte
     *
     * @param is
     * @return
     */
    public static byte[] getBytes(InputStream is) throws Exception {
        byte[] data = null;

        Collection chunks = new ArrayList();
        byte[] buffer = new byte[1024 * 1000];
        int read = -1;
        int size = 0;

        while ((read = is.read(buffer)) != -1) {
            if (read > 0) {
                byte[] chunk = new byte[read];
                System.arraycopy(buffer, 0, chunk, 0, read);
                chunks.add(chunk);
                size += chunk.length;
            }
        }

        if (size > 0) {
            ByteArrayOutputStream bos = null;
            try {
                bos = new ByteArrayOutputStream(size);
                for (Iterator itr = chunks.iterator(); itr.hasNext(); ) {
                    byte[] chunk = (byte[]) itr.next();
                    bos.write(chunk);
                }
                data = bos.toByteArray();
            } finally {
                if (bos != null) {
                    bos.close();
                }
            }
        }
        return data;
    }

    public IResultSetListMap getOnePageListInNewMethod(String strSQL,
                                                       final int page, final int pagesize) throws DAOException {
        /**@todo Implement this com.hzjc.query.dao.IQueryDao method*/

        //调用PreparedStatetement Select Sql语句的执行类
        class ExecutePagePreparedStatementCallback
                implements PreparedStatementCallback {

            /**
             *
             * @param ps
             * @return
             * @throws SQLException
             * @throws DataAccessException
             */
            public Object doInPreparedStatement(PreparedStatement ps) throws
                    SQLException,
                    DataAccessException {
                //调用执行得到返回值
                return executeReturnExtractor(ps);
            }

            /**
             *
             * @param ps
             * @return
             * @throws SQLException
             */
            private IResultSetListMap executeReturnExtractor(PreparedStatement ps) throws
                    SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    //=====================================================================
                    //执行表达式语句
                    //=====================================================================
                    ps.setInt(2, (page - 1) * pagesize);
                    ps.setInt(1, page * pagesize);
//	        	ps.setMaxRows(pagesize);
                    _log.debug("startPage:" + page);
                    _log.debug("pageSize:" + pagesize);
                    rs = ps.executeQuery();
                    //=====================================================================
                    //组织返回结果集
                    //=====================================================================
//	          rs.absolute(startPage*pagesize);
                    return processRs(rs);
                } finally {
                    DbUtils.close(rs);
                }
            }

        }

        /**
         * 调用执行类，返回执行的结果集
         */
        try {
            _log.info("JdbcDao:" + strSQL);
            _log.info("LimitSql:" + getLimitString(
                    strSQL));
            String ss = getLimitString(strSQL);
            System.out.println(ss);

            return (IResultSetListMap) this.getJdbcTemplate().execute(getLimitString(
                    strSQL),
                    new ExecutePagePreparedStatementCallback());
        } catch (Exception ex) {
            throw new DAOException("分页查询数据发生异常", ex);
        }

    }

//	  /**  
//	  * JDBC 分页查询  
//	  * @param sql       SQL 查询语句  
//	  * @param firstSize 起始页  
//	  * @param maxSize   返回数据条数  
//	  * @return ResultSet  
//	  * @throws SQLException  
//	  */  
//	  public ResultSet queryPageAbsolute(String sql,   
//	              int firstSize,int maxSize) throws SQLException {   
//	         PreparedStatement pre = this.getConn().prepareStatement(sql);   
//	         pre.setMaxRows(maxSize);   
//	          ResultSet rs = pre.executeQuery();   
//	         rs.absolute(firstSize * maxSize);   
//	          return rs;   
//	   }   
}
