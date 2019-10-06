package com.github.mikesafonov.specification.builder.starter.annotations;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.*;

/**
 * Specifies a field for joining using join type {@link #type()}
 *
 * @author MikeSafonov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Joins.class)
public @interface Join {
    String value();

    JoinType type() default JoinType.INNER;
}