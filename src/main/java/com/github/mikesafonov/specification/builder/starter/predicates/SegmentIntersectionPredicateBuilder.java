package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.annotations.SegmentIntersection;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class SegmentIntersectionPredicateBuilder<T> implements PredicateBuilder {

    private final FieldWithValue field;
    private final ExpressionBuilder expressionBuilder;

    @Override
    public Predicate build(Root<?> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        SegmentFilter<T> fieldValue = field.getValueAsSegmentFilter();
        SegmentIntersection segmentIntersection = field.getAnnotation(SegmentIntersection.class);
        T from = fieldValue.getFrom();
        T to = fieldValue.getTo();

        Predicate predicate = cb.and();

        if (from == null && to != null) {
            predicate = new LessThanEqualPredicateBuilder(expressionBuilder, new FieldWithValue(field.getField(), to, segmentIntersection.fromField())).build(root, q, cb);
        } else if (from != null && to == null) {
            predicate = cb.or(
                expressionBuilder.getExpression(root, field.getField(), segmentIntersection.toField()).isNull(),
                new GreaterThanEqualPredicateBuilder(expressionBuilder, new FieldWithValue(field.getField(), from, segmentIntersection.toField())).build(root, q, cb)
            );
        } else if (from != null) {
            predicate =
                cb.or(
                    cb.and(
                        new LessThanEqualPredicateBuilder(expressionBuilder, new FieldWithValue(field.getField(), from, segmentIntersection.fromField())).build(root, q, cb),
                        cb.or(
                            expressionBuilder.getExpression(root, field.getField(), segmentIntersection.toField()).isNull(),
                            new GreaterThanEqualPredicateBuilder(expressionBuilder, new FieldWithValue(field.getField(), from, segmentIntersection.toField())).build(root, q, cb)
                        )
                    ),
                    cb.and(
                        new GreaterThanEqualPredicateBuilder(expressionBuilder, new FieldWithValue(field.getField(), from, segmentIntersection.fromField())).build(root, q, cb),
                        new LessThanEqualPredicateBuilder(expressionBuilder, new FieldWithValue(field.getField(), to, segmentIntersection.fromField())).build(root, q, cb)
                    )
                );
        }

        return predicate;
    }
}
