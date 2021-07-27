package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.annotations.SegmentIntersection;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

@RequiredArgsConstructor
public class SegmentIntersectionPredicateBuilder<T> implements PredicateBuilder {
    private final Root<?> root;
    private final Field field;
    private final SegmentFilter<T> fieldValue;
    private final SegmentIntersection segmentIntersection;
    private final CriteriaBuilder cb;
    private final ExpressionBuilder expressionBuilder;

    @Override
    public Predicate build() {
        T from = fieldValue.getFrom();
        T to = fieldValue.getTo();

        Expression<?> fromField = expressionBuilder.getExpression(root, field, segmentIntersection.fromField());
        Expression<?> toField = expressionBuilder.getExpression(root, field, segmentIntersection.toField());

        Predicate predicate = cb.and();

        if (from == null && to != null) {
            predicate = new LessThanEqualPredicateBuilder(cb, cb.literal(to), fromField).build();
        } else if (from != null && to == null) {
            predicate = cb.or(
                toField.isNull(),
                new GreaterThanEqualPredicateBuilder(cb, cb.literal(from), toField).build()
            );
        } else if (from != null) {
            predicate =
                cb.or(
                    cb.and(
                        new LessThanEqualPredicateBuilder(cb, cb.literal(from), fromField).build(),
                        cb.or(
                            toField.isNull(),
                            new GreaterThanEqualPredicateBuilder(cb, cb.literal(from), toField).build()
                        )
                    ),
                    cb.and(
                        new GreaterThanEqualPredicateBuilder(cb, cb.literal(from), fromField).build(),
                        new LessThanEqualPredicateBuilder(cb, cb.literal(to), fromField).build()
                    )
                );
        }

        return predicate;
    }
}
