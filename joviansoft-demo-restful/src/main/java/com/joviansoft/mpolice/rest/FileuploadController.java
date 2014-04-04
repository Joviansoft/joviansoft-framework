package com.joviansoft.mpolice.rest;

import com.joviansoft.framework.core.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by bigbao on 14-3-24.
 */
@Controller
public class FileuploadController extends BaseController {
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadFile(MultipartHttpServletRequest request, HttpServletResponse response){
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        while(itr.hasNext()){
            mpf = request.getFile(itr.next());
            try {
                mpf.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
