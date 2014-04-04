package com.joviansoft.framework.core.session;

import java.io.Serializable;

/**
 * @author bigbao
 */
public interface Session extends Serializable {
    void setAttribute(String name, Object obj);

    Object getAttribute(String name);
}