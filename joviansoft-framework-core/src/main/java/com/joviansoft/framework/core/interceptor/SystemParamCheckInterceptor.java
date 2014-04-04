package com.joviansoft.framework.core.interceptor;

import com.joviansoft.framework.core.security.AppSecretManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bigbao on 14-2-24.
 */
public class SystemParamCheckInterceptor implements HandlerInterceptor {
    //    @Autowired
    //    AppSecretManager appSecretManager;

    public AppSecretManager getAppSecretManager() {
        return appSecretManager;
    }

    public void setAppSecretManager(AppSecretManager appSecretManager) {
        this.appSecretManager = appSecretManager;
    }

    private AppSecretManager appSecretManager;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
