package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.annotations.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Collection;

import static com.github.mikesafonov.specification.builder.starter.ExpressionBuilder.getExpression;

/**
 * Factory for {@link PredicateBuilder} from {@link CriteriaBuilder}, field and fields value
 *
 * @author MikeSafonov
 */
public class PredicateBuilderFactory<T> {

    @org.springframework.lang.NonNull
    public PredicateBuilder createPredicateBuilder(
            @org.springframework.lang.NonNull Root<T> root,
            @org.springframework.lang.NonNull CriteriaBuilder cb,
            @org.springframework.lang.NonNull CriteriaQuery<?> cq,
            @org.springframework.lang.NonNull Field field,
            @org.springframework.lang.NonNull Object fieldValue,
            @org.springframework.lang.NonNull String fieldName) {
        if (field.isAnnotationPresent(IsNull.class)) {
            return new NullPredicateBuilder(cb, getExpression(root, field, fieldName));
        }
        if (field.isAnnotationPresent(NonNull.class)) {
            return new NotNullPredicateBuilder(cb, getExpression(root, field, fieldName));
        }
        if (Collection.class.isAssignableFrom(fieldValue.getClass())) {
            Collection collection = (Collection) fieldValue;
            if (field.isAnnotationPresent(ManyToManyCollection.class)) {
                return new ManyToManyCollectionPredicateBuilder(root, cb, cq, collection, field, fieldName);
            }
            return new CollectionPredicateBuilder(collection, getExpression(root, field, fieldName));
        }
        if (field.isAnnotationPresent(Like.class)) {
            return new LikePredicateBuilder(cb, field.getAnnotation(Like.class), fieldValue, getExpression(root, field, fieldName));
        } else if (field.isAnnotationPresent(GreaterThan.class)) {
            return new GreaterThanPredicateBuilder(cb, fieldValue, getExpression(root, field, fieldName));
        } else if (field.isAnnotationPresent(GreaterThanEqual.class)) {
            return new GreaterThanEqualPredicateBuilder(cb, fieldValue, getExpression(root, field, fieldName));
        } else if (field.isAnnotationPresent(LessThan.class)) {
            return new LessThanPredicateBuilder(cb, fieldValue, getExpression(root, field, fieldName));
        } else if (field.isAnnotationPresent(LessThanEqual.class)) {
            return new LessThanEqualPredicateBuilder(cb, fieldValue, getExpression(root, field, fieldName));
        }
        return new EqualsPredicateBuilder(cb, fieldValue, getExpression(root, field, fieldName));
    }
}
