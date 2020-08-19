package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import com.github.mikesafonov.specification.builder.starter.annotations.Joins;
import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

/**
 * @author MikeSafonov
 */
@UtilityClass
public class ExpressionBuilder {

    /**
     * @param root          entity root
     * @param field         field for expression
     * @param attributeName attribute name
     * @param restrictions  on restrictions
     * @param <E>           entity type
     * @return return attribute expression from root or joined attribute expression via {@link Join}
     */
    @NonNull
    public static <E> Expression getExpression(@NonNull Root<E> root, @NonNull Field field,
                                               @NonNull String attributeName, @NonNull Predicate... restrictions) {
        if (field.isAnnotationPresent(Join.class) || field.isAnnotationPresent(Joins.class)) {
            Join[] joins = field.getAnnotationsByType(Join.class);
            javax.persistence.criteria.Join<Object, Object> join = root.join(joins[0].value(), joins[0].type()).on(restrictions);
            for (int i = 1; i < joins.length; i++) {
                join = join.join(joins[i].value(), joins[i].type());
            }
            return join.get(attributeName);
        } else {
            return root.get(attributeName);
        }
    }

}
