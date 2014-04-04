package com.joviansoft.framework.core.fileupload;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bigbao on 14-3-24.
 */
public interface FileUpload {
    boolean uploadFile(MultipartHttpServletRequest request, HttpServletResponse response);
    byte[] download(String filePath);
}
