package com.joviansoft.framework.core.security;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bigbao on 14-2-25.
 */
public interface SecurityManager {
    /**
     * 检查session
     * @param request
     * @return
     */
    boolean checkSession(HttpServletRequest request);

    /**
     * 检查签名
     * @param request
     * @return
     */
    boolean checkSign(HttpServletRequest request);

    /**
     * 检查App Key
     * @param request
     * @return
     */
    boolean checkAppSecret(HttpServletRequest request);

    /**
     * 检查是否超时
     * @param request
     * @return
     */
    boolean checkTimeOut(HttpServletRequest request);
}
