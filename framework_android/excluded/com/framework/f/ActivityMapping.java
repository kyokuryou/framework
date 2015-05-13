package org.smarty.core.f;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by kyokuryou on 15-3-12.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityMapping {
    public String name();
}
