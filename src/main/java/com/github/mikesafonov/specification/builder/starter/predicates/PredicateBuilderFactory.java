package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.annotations.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Factory for {@link PredicateBuilder} from {@link CriteriaBuilder}, field and fields value
 *
 * @author MikeSafonov
 */
public class PredicateBuilderFactory {

    private final ExpressionBuilder expressionBuilder = new ExpressionBuilder();

    @org.springframework.lang.NonNull
    public <T> PredicateBuilder createPredicateBuilder(
        @org.springframework.lang.NonNull Root<T> root,
        @org.springframework.lang.NonNull CriteriaBuilder cb,
        @org.springframework.lang.NonNull CriteriaQuery<?> cq,
        @org.springframework.lang.NonNull FieldWithValue field) {

        if (field.isAnnotatedBy(IsNull.class)) {
            return new NullPredicateBuilder(cb, expressionBuilder.getExpression(root, field));
        }
        if (field.isAnnotatedBy(NonNull.class)) {
            return new NotNullPredicateBuilder(cb, expressionBuilder.getExpression(root, field));
        }
        if (field.isValueCollection()) {
            if (field.isAnnotatedBy(ManyToManyCollection.class)) {
                return new ManyToManyCollectionPredicateBuilder<>(root, cb, cq, field, expressionBuilder);
            }
            return new CollectionPredicateBuilder(field.getValueAsCollection(),
                expressionBuilder.getExpression(root, field));
        }
        if (field.isAnnotatedBy(Like.class)) {
            return new LikePredicateBuilder(cb, field.getAnnotation(Like.class), field.getValue(),
                expressionBuilder.getExpression(root, field));
        } else if (field.isAnnotatedBy(GreaterThan.class)) {
            return new GreaterThanPredicateBuilder(cb, field.getValue(), expressionBuilder.getExpression(root, field));
        } else if (field.isAnnotatedBy(GreaterThanEqual.class)) {
            return new GreaterThanEqualPredicateBuilder(cb, field.getValue(), expressionBuilder.getExpression(root, field));
        } else if (field.isAnnotatedBy(LessThan.class)) {
            return new LessThanPredicateBuilder(cb, field.getValue(), expressionBuilder.getExpression(root, field));
        } else if (field.isAnnotatedBy(LessThanEqual.class)) {
            return new LessThanEqualPredicateBuilder(cb, field.getValue(), expressionBuilder.getExpression(root, field));
        } else if (field.isAnnotatedBy(SegmentIntersection.class) && field.isValueSegmentFilter()) {
            return new SegmentIntersectionPredicateBuilder<>(
                root,
                field.getField(),
                field.getValueAsSegmentFilter(),
                field.getAnnotation(SegmentIntersection.class),
                cb,
                expressionBuilder
            );
        }
        return new EqualsPredicateBuilder(cb, field.getValue(), expressionBuilder.getExpression(root, field));
    }
}
