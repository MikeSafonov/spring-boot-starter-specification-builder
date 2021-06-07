package com.github.mikesafonov.specification.builder.starter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies what field must be testing whether the expression satisfies the given pattern.
 *
 * @author MikeSafonov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Like {
    /**
     * @return Like direction
     */
    DIRECTION direction() default DIRECTION.AROUND;

    /**
     * @return is like must be case sensitive
     */
    boolean caseSensitive() default false;

    enum DIRECTION {
        LEFT, RIGHT, AROUND, NONE
    }
}