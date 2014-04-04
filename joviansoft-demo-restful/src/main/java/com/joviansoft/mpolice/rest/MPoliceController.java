package com.joviansoft.mpolice.rest;

import com.joviansoft.framework.core.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bigbao on 14-2-19.
 */
@Controller
@RequestMapping("/rest1")
public class MPoliceController extends BaseController{
    @RequestMapping(value = "test", method= RequestMethod.GET)
    @ResponseBody
    public String getString(HttpServletRequest request){
        request.getMethod();
        String id = request.getParameter("id");
        return id;
    }
    @RequestMapping(value = "test", method= RequestMethod.POST)
    @ResponseBody
    public String put(HttpServletRequest request){
        request.getMethod();
        String id = request.getParameter("id");
        return id;
    }


}
