package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

/**
 * @author MikeSafonov
 */
class ExpressionBuilder {

    /**
     * @param root          entity root
     * @param field         field for expression
     * @param attributeName attribute name
     * @param <E>           entity type
     * @return return attribute expression from root or joined attribute expression via {@link Join}
     */
    static <E> Expression getExpression(Root<E> root, Field field, String attributeName) {
        if (field.isAnnotationPresent(Join.class)) {
            Join[] joins = field.getAnnotationsByType(Join.class);
            javax.persistence.criteria.Join<Object, Object> join = root.join(joins[0].value(), joins[0].type());
            for (int i = 1; i < joins.length; i++) {
                join = join.join(joins[i].value(), joins[i].type());
            }
            return join.get(attributeName);
        } else {
            return root.get(attributeName);
        }
    }

}