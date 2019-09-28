package com.github.mikesafonov.specification.builder.starter.annotations;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies a field for joining using join type {@link #type()}
 *
 * @author MikeSafonov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
    String value();

    JoinType type() default JoinType.INNER;
}