package com.joviansoft.framework.core.annotations;

/**
 * Created by bigbao on 14-3-12.
 */

import java.lang.annotation.*;
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreApikey {
}
