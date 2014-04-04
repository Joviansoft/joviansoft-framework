/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012 
 * 日    期：12-6-21
 */
package com.joviansoft.framework.core.session;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author bigbao
 * @version 1.0
 */
public class SimpleSession implements Session {

    private Map<String, Object> attributes = new HashMap<String, Object>();

    @Override
    public void setAttribute(String name, Object obj) {
        attributes.put(name, obj);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

}

