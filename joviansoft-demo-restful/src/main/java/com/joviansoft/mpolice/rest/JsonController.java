package com.joviansoft.mpolice.rest;

import com.joviansoft.framework.core.BaseController;
import com.joviansoft.gps.domain.Message;
import com.joviansoft.gps.service.GpsService;
import com.joviansoft.framework.core.exceptions.BusinessErrorCode;
import com.joviansoft.framework.core.exceptions.BusinessException;
import com.joviansoft.framework.core.response.CodeOnlyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class JsonController extends BaseController {

    @Autowired
    GpsService gpsService;

    @RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object hello() {
        return new Object() {
            public String foo = "Hello world!";
        };
    }

    @RequestMapping(value = "/hi", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object hi(HttpServletRequest request,@Valid Message bean, BindingResult result) {
        //校验参数
       // this.handleError(beanResult);
        this.handleError(result);


        //处理核心业务
        //返回数据
        return bean;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object test(HttpServletRequest request, @Valid Message bean, BindingResult result) {

        String id = request.getParameter("id");
        String message = request.getParameter("message");

        System.out.println("Message=" + message);

        //校验参数
        this.handleError(result);
        try {
            bean.setMessage(URLDecoder.decode(bean.getMessage(), "UTF-8"));
            System.out.println(URLDecoder.decode(bean.getMessage(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //处理核心业务
        //返回数据

//        try {
//            bean.setMessage(URLDecoder.decode( bean.getMessage(), "GBK"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        return bean;
    }


    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object testPost(HttpServletRequest request, @Valid Message bean, BindingResult result) {

        String id = request.getParameter("id");
        String message = request.getParameter("message");
        //校验参数
        this.handleError(result);


        //处理核心业务
        //返回数据
        return bean;
    }

    @RequestMapping(value="/gps", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object gpsTest(){


        return gpsService.searchGpsData();
    }

    @RequestMapping(value="/gps", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object gpsPostTest(){


        return gpsService.searchGpsData();
    }
    @RequestMapping(value="gps/add", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object gpsPost() throws Exception{
        CodeOnlyResponse response = null;
        try {
            response = new CodeOnlyResponse(0);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.VALUE_REQUIRED);
        }
        return response;
    }
}
