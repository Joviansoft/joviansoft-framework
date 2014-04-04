package com.joviansoft.framework.core.annotations;

import java.lang.annotation.*;

/**
 * Created by bigbao on 14-2-25.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreSession {

}

