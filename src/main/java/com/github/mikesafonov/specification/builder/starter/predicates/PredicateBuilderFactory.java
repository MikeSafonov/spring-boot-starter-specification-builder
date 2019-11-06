package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.annotations.*;
import lombok.experimental.UtilityClass;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Factory for {@link PredicateBuilder} from {@link CriteriaBuilder}, field and fields value
 *
 * @author MikeSafonov
 */
@UtilityClass
public class PredicateBuilderFactory {

    @org.springframework.lang.NonNull
    public static PredicateBuilder createPredicateBuilder(
            @org.springframework.lang.NonNull Root root,
            @org.springframework.lang.NonNull CriteriaBuilder cb,
            @org.springframework.lang.NonNull Field field,
            @org.springframework.lang.NonNull Object fieldValue,
            @org.springframework.lang.NonNull String fieldName) {
        Expression expression = ExpressionBuilder.getExpression(root, field, fieldName);
        if (field.isAnnotationPresent(IsNull.class)) {
            return new NullPredicateBuilder(cb, expression);
        }
        if (field.isAnnotationPresent(NonNull.class)) {
            return new NotNullPredicateBuilder(cb, expression);
        }
        if (Collection.class.isAssignableFrom(fieldValue.getClass())) {
            Collection collection = (Collection) fieldValue;
            return new CollectionPredicateBuilder(collection, expression);
        }
        if (field.isAnnotationPresent(Like.class)) {
            return new LikePredicateBuilder(cb, field.getAnnotation(Like.class), fieldValue, expression);
        } else if (field.isAnnotationPresent(GreaterThan.class)) {
            return new GreaterThanPredicateBuilder(cb, fieldValue, expression);
        } else if (field.isAnnotationPresent(GreaterThanEqual.class)) {
            return new GreaterThanEqualPredicateBuilder(cb, fieldValue, expression);
        } else if (field.isAnnotationPresent(LessThan.class)) {
            return new LessThanPredicateBuilder(cb, fieldValue, expression);
        } else if (field.isAnnotationPresent(LessThanEqual.class)) {
            return new LessThanEqualPredicateBuilder(cb, fieldValue, expression);
        }
        return new EqualsPredicateBuilder(cb, fieldValue, expression);
    }
}
