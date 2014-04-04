package com.joviansoft.framework.exception;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author barryhong
 * @version 1.0
 */
public class DbException extends BaseException {
    public DbException(String msg) {
        this.setErrorMessage(msg);
    }
	  public DbException(String s, Throwable e) {
		    super(s, e);
		  }
    

}
