package com.joviansoft.framework.dao;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.joviansoft.framework.exception.DAOException;
import com.joviansoft.framework.query.IResultSetListMap;

public interface IJdbcDao {
	
	/**
	   *new paging
	   * @param strSQL
	   * @param page
	   * @param pagesize
	   * @return
	   */
	  IResultSetListMap getOnePageListInNewMethod(String strSQL, int page, int pagesize) throws
	      DAOException;

	  /**
	   *
	   * @param strSQL
	   * @param offset
	   * @param pagesize
	   * @return
	   */
	  IResultSetListMap getOnePageList(String strSQL, int offset, int pagesize) throws
	      DAOException;

	  /**
	   *
	   * @param strSQL
	   * @return
	   */
	  IResultSetListMap getAllList(String strSQL) throws DAOException;

	  /**
	   *
	   * @param strSQL
	   * @return
	   */
	  int getTotalRecordCount(String strSQL) throws DAOException;

	  /**
	   * 执行更新语句
	   * @param strSql
	   * @throws <{DAOException}>
	   */
	  void execute(final String strSql) throws DAOException;

	  /**
	   * 执行简单的存储过程，没有参数，没有返回结果
	   * @param strSqlCall
	   */
	  void executeSpCall(String strSqlCall) throws DAOException;

	  /**
	   *
	   * @param sql
	   * @param action
	   * @return
	   */
	  public Object execute(final String sql, PreparedStatementCallback action);

	  /**
	   *
	   * @param sql
	   * @param action
	   * @return
	   */
	  public Object execute(final String sql, CallableStatementCallback action);
	}
