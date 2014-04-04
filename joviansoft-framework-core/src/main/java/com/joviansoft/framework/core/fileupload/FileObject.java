package com.joviansoft.framework.core.fileupload;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bigbao on 14-3-24.
 */
public abstract  class FileObject {
    private MultipartFile mpf;


    public MultipartFile getMpf() {
        return mpf;
    }

    public void setMpf(MultipartFile mpf) {
        this.mpf = mpf;
    }


}
