package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.annotations.*;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Factory for {@link PredicateBuilder} from {@link CriteriaBuilder}, field and fields value
 *
 * @author MikeSafonov
 */
public class PredicateBuilderFactory<T> {
    private final ExpressionBuilder expressionBuilder = new ExpressionBuilder();

    @org.springframework.lang.NonNull
    public PredicateBuilder createPredicateBuilder(
            @org.springframework.lang.NonNull Root<T> root,
            @org.springframework.lang.NonNull CriteriaBuilder cb,
            @org.springframework.lang.NonNull CriteriaQuery<?> cq,
            @org.springframework.lang.NonNull Field field,
            @org.springframework.lang.NonNull Object fieldValue,
            @org.springframework.lang.NonNull String fieldName) {
        if (field.isAnnotationPresent(IsNull.class)) {
            return new NullPredicateBuilder(cb, expressionBuilder.getExpression(root, field, fieldName));
        }
        if (field.isAnnotationPresent(NonNull.class)) {
            return new NotNullPredicateBuilder(cb, expressionBuilder.getExpression(root, field, fieldName));
        }
        if (Collection.class.isAssignableFrom(fieldValue.getClass())) {
            Collection collection = (Collection) fieldValue;
            if (field.isAnnotationPresent(ManyToManyCollection.class)) {
                return new ManyToManyCollectionPredicateBuilder(root, cb, cq, collection, field, fieldName, expressionBuilder);
            }
            return new CollectionPredicateBuilder(collection, expressionBuilder.getExpression(root, field, fieldName));
        }
        if (field.isAnnotationPresent(Like.class)) {
            return new LikePredicateBuilder(cb, field.getAnnotation(Like.class), fieldValue, expressionBuilder.getExpression(root, field, fieldName));
        } else if (field.isAnnotationPresent(GreaterThan.class)) {
            return new GreaterThanPredicateBuilder(cb, fieldValue, expressionBuilder.getExpression(root, field, fieldName));
        } else if (field.isAnnotationPresent(GreaterThanEqual.class)) {
            return new GreaterThanEqualPredicateBuilder(cb, fieldValue, expressionBuilder.getExpression(root, field, fieldName));
        } else if (field.isAnnotationPresent(LessThan.class)) {
            return new LessThanPredicateBuilder(cb, fieldValue, expressionBuilder.getExpression(root, field, fieldName));
        } else if (field.isAnnotationPresent(LessThanEqual.class)) {
            return new LessThanEqualPredicateBuilder(cb, fieldValue, expressionBuilder.getExpression(root, field, fieldName));
        } else if (field.isAnnotationPresent(SegmentIntersection.class) &&
            SegmentFilter.class.isAssignableFrom(fieldValue.getClass())
        ) {
            return new SegmentIntersectionPredicateBuilder<>(
                root,
                field,
                (SegmentFilter<?>) fieldValue,
                field.getAnnotation(SegmentIntersection.class),
                cb,
                expressionBuilder
            );
        }
        return new EqualsPredicateBuilder(cb, fieldValue, expressionBuilder.getExpression(root, field, fieldName));
    }
}
