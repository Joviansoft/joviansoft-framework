package com.joviansoft.framework.core.interceptor;

import com.joviansoft.framework.core.annotations.IgnoreApikey;
import com.joviansoft.framework.core.annotations.IgnoreSession;
import com.joviansoft.framework.core.annotations.IgnoreSign;
import com.joviansoft.framework.core.exceptions.ServiceErrorCode;
import com.joviansoft.framework.core.exceptions.SystemServiceException;
import com.joviansoft.framework.core.security.SecurityManager;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义拦截器，处理系统参数的检查,session,参数签名
 * Created by bigbao on 14-2-23.
 */
public class CheckRequestInterceptor implements HandlerInterceptor {
    private Boolean sessionCheck;
    private Boolean signCheck;


    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    private SecurityManager securityManager;


    public Boolean getSessionCheck() {
        return sessionCheck;
    }

    public void setSessionCheck(Boolean sessionCheck) {
        this.sessionCheck = sessionCheck;
    }

    public Boolean getSignCheck() {
        return signCheck;
    }

    public void setSignCheck(Boolean signCheck) {
        this.signCheck = signCheck;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HandlerMethod handler = (HandlerMethod) o;
        IgnoreApikey ignoreApikey = handler.getMethodAnnotation(IgnoreApikey.class);

        String str = request.getParameter("appKey");
        String sign = request.getParameter("sign");

        //检查appkey是否有效
        if (null == ignoreApikey) {

            if (!securityManager.checkAppSecret(request)) {
                throw new SystemServiceException(ServiceErrorCode.INVALID_APPKEY);
            }
        }

        //检查参数签名
        IgnoreSign ignoreSign = handler.getMethodAnnotation(IgnoreSign.class);
        if (null == ignoreSign) {
            if (!securityManager.checkSign(request)) {
                throw new SystemServiceException(ServiceErrorCode.INVALID_SIGN);
            }
        }

        //检查session
        IgnoreSession ignoreSession = handler.getMethodAnnotation(IgnoreSession.class);
        if (null == ignoreSession) {

            if (!securityManager.checkSession(request)) {
                throw new SystemServiceException(ServiceErrorCode.INVALID_SESSION);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("服务访问完成");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("视图返回");
    }

}
