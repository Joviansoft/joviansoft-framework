package com.joviansoft.mpolice.rest;

import com.joviansoft.framework.core.BaseController;
import com.joviansoft.framework.core.annotations.IgnoreSession;
import com.joviansoft.framework.core.annotations.IgnoreSign;
import com.joviansoft.mpolice.response.LogonResponse;
import com.joviansoft.framework.core.session.SessionManager;
import com.joviansoft.framework.core.session.SimpleSession;
import com.joviansoft.framework.core.utils.JoviansoftUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bigbao on 14-2-24.
 */
@Controller
public class UsersController extends BaseController {
    @Autowired
    protected SessionManager sessionManager;

    @RequestMapping(value = "/logon", method = RequestMethod.GET)
    @ResponseBody
    @IgnoreSession
    @IgnoreSign
    public Object logon(HttpServletRequest request) throws Exception {
        // add session
        SimpleSession session = new SimpleSession();

        String sessionId = JoviansoftUtils.getUUID();

        session.setAttribute("ip", request.getRemoteAddr());

        sessionManager.addSession(sessionId, session);

        LogonResponse logonResponse = new LogonResponse();
        logonResponse.setSession(sessionId);
        return logonResponse;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Object logout(HttpServletRequest request) throws Exception {

        // remove session
        String sessionid = request.getParameter("session");
        sessionManager.removeSession(sessionid);
        return null;
    }
}
