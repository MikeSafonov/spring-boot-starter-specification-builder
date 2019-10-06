package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

class ExpressionBuilder {

    <S> Expression getExpression(Root<S> root, Field field, String fieldName) {
        if (field.isAnnotationPresent(Join.class)) {
            Join[] joins = field.getAnnotationsByType(Join.class);
            javax.persistence.criteria.Join<Object, Object> join = root.join(joins[0].value(), joins[0].type());
            for (int i = 1; i < joins.length; i++) {
                join = join.join(joins[i].value(), joins[i].type());
            }
            return join.get(fieldName);
        } else {
            return root.get(fieldName);
        }
    }

}
