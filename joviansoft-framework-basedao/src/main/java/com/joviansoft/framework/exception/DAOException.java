package com.joviansoft.framework.exception;

/**
 * 数据访问层异常类
 * <p>Title: Prodb</p>
 * <p>Description: 常住人口管理信息系统（二代证）省级库</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: GNT(ZJHZ)</p>
 * @author kgb_hz@126.com,kgb@primetech.com.cn
 * @version 1.0
 */
public class DAOException extends DbException {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	   *
	   * @param s
	 * @param e
	   */
	  public DAOException(String s, Throwable e) {
		    super(s, e);
		  }



	public DAOException(String msg) {
		super(msg);
//		this.setErrorMessage(msg);
		// TODO Auto-generated constructor stub
	}



	/**
	   *
	   * @return
	   */
	  public String getMessage() {
	    return "<数据访问层DAO>" + super.getMessage() + "！";
	  }
	}	
