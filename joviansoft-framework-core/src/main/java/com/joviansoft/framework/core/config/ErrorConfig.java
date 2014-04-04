/**
 */
package com.joviansoft.framework.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * <pre>
 *   错误码配置文件
 * </pre>
 *
 * @author bigbao
 */

public class ErrorConfig {

    private static final String ERROR_PROPERTIES = "errors.properties";
    protected static final Logger logger = LoggerFactory.getLogger(ErrorConfig.class);

    private static Properties properties;


    public static String getErrorMessage(String errorCode) {
        if (properties == null) {
            try {
                DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
                Resource resource = resourceLoader.getResource(ERROR_PROPERTIES);
                properties = PropertiesLoaderUtils.loadProperties(resource);
            } catch (IOException e) {
                throw new RuntimeException("找不到error.properties属性文件", e);
            }
        }
        String errorMessage = properties.getProperty(errorCode);

        if (errorMessage == null) {
            logger.error("不存在应用键为{0}的错误码,错误码配置文件。", errorCode);
        }
        try {
            errorMessage = new String(errorMessage.getBytes("ISO-8859-1"),"gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return errorMessage;
    }
}

