package com.joviansoft.framework.core.security;

import com.joviansoft.framework.core.config.SystemParameterNames;
import com.joviansoft.framework.core.session.Session;
import com.joviansoft.framework.core.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bigbao on 14-2-25.
 */
@Component
public class SecurityManagerImpl implements SecurityManager {

    private boolean signCheck;
    private boolean sessionCheck;
    private boolean appKeyCheck;

    @Autowired
    private AppSecretManager appSecretManager;
    @Autowired
    private SessionManager sessionManager;

    /**
     * 检查session
     *
     * @param request
     * @return session是否有效
     */
    @Override
    public boolean checkSession(HttpServletRequest request) {
        //不检查session，全局的session检查参数
        if (!sessionCheck)
            return true;

        boolean validSession = true;

        String sessionId = request.getParameter(SystemParameterNames.sessionId);

        /*客户端没有传sessionid*/
        if (sessionId == null || sessionId.isEmpty()) {
            validSession = false;
        }else{
              /*取得sessionDd所对应的session 在服务端的记录,如果找不到，则说明客户端传入的session无效 */
            Session session = sessionManager.getSession(sessionId);

            if (session == null) {
                validSession = false;
            } else {
                String ip = request.getRemoteAddr();
                String savedIp = (String) session.getAttribute("ip");
                if(!savedIp.equals(ip)){
                    validSession = false;
                }
            }
        }
      return validSession;
    }

    /**
     * 检查签名
     *
     * @param request
     * @return
     */
    @Override
    public boolean checkSign(HttpServletRequest request) {
        if (!signCheck)
            return true;

        return false;
    }

    /**
     * 检查App Key
     *
     * @param request
     * @return
     */
    @Override
    public boolean checkAppSecret(HttpServletRequest request) {
        if (!appKeyCheck) {
            return true;
        }
        String appKey = request.getParameter(SystemParameterNames.appKey);
        return appSecretManager.isValidAppKey(appKey);
    }

    /**
     * 检查是否超时
     *
     * @param request
     * @return
     */
    @Override
    public boolean checkTimeOut(HttpServletRequest request) {
        return true;
    }

    public boolean isSignCheck() {
        return signCheck;
    }

    public void setSignCheck(boolean signCheck) {
        this.signCheck = signCheck;
    }

    public boolean isSessionCheck() {
        return sessionCheck;
    }

    public void setSessionCheck(boolean sessionCheck) {
        this.sessionCheck = sessionCheck;
    }

    public boolean isAppKeyCheck() {
        return appKeyCheck;
    }

    public void setAppKeyCheck(boolean appKeyCheck) {
        this.appKeyCheck = appKeyCheck;
    }

}
