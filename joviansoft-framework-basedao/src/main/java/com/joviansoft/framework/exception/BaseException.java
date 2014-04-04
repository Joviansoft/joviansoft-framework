/**
 * @author barryhong   create date 20080516
 * 这个类扩展自RuntimeException目的是抛出这个异常时不用声明，
 * 重写了getMessage方法目的是在有异常发生时Dorado的alert格式化窗口里的异常信息
 * */
package com.joviansoft.framework.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
    public BaseException() {
        this.setErrorMessage("未知错误！");
    }

    public BaseException(String msg) {
        this.setErrorMessage(msg);
    }

    public String getMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("当前错误发生于：");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sb.append(sd.format(new Date()));
        sb.append("\n");
        sb.append("\n");
        sb.append("错误内容如下：");
//        if(this.errorMessage.length()> 1000){
//        	sb.append(this.errorMessage.substring(0, 1000));
//        }else{
        	sb.append(this.errorMessage);
//        }
        return sb.toString();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    //日志处理
    protected Log _log = LogFactory.getLog(this.getClass());
    //每层异常的错误代码


    /**
     * 所有其他构造函数的基础实现类
     * @param s
     * @param e
     */
    public BaseException(String s, Throwable e) {
      //super(s, e); //Since JDK.1.4
      super(s);
      if (e == null) { //设置异常信息字符串
    	  setErrorMessage(s);
      }
      else {
        this._rootCause = e;
        //setErrmsg(s.concat("；").concat(e.getMessage()));
        //setErrmsg(s.concat("；").concat(e.toString()));
        ///////////////////////////////////////////////////////////////////
        //组合自定义异常和异常的原始错误描述抛出，返回给前台客户端
        ///////////////////////////////////////////////////////////////////
        /*Since JDK1.4
               StackTraceElement[] trace = e.getStackTrace();
               StringBuffer strBuf = new StringBuffer();
               strBuf.append(s).append(";").append(e.toString()).append("\r\n");
               for (int i = 0; i < trace.length; i++) {
          strBuf.append("\tat " + trace[i] + "\r\n");
               }
               setErrmsg(strBuf.toString());
         */
        StringWriter sw = new StringWriter(512);
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        setErrorMessage(s.concat(sw.toString()));
      }
      _log.warn(getMessage());
    }

    private int errcode;
    private Throwable _rootCause;

    public int getErrcode() {
      return errcode;
    }

    public void setErrcode(int errcode) {
      this.errcode = errcode;
    }


    /**
     *
     * @return
     */
    public Throwable getRootCause() {
      return _rootCause;
    }

    //////////////////////////////////////////////////////////////////////////////


}
