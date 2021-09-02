package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.annotations.SegmentIntersection;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;

@RequiredArgsConstructor
public class SegmentIntersectionPredicateProcessor implements PredicateProcessor {

    @Override
    public <T> void process(PredicateContainer container, FieldWithValue field, Root<T> root,
                            CriteriaBuilder cb, CriteriaQuery<?> cq, ExpressionBuilder expressionBuilder) {
        if (match(field)) {
            SegmentFilter<?> fieldValue = field.getValueAsSegmentFilter();
            SegmentIntersection segmentIntersection = field.getAnnotation(SegmentIntersection.class);
            Field f = field.getField();
            Object from = fieldValue.getFrom();
            Object to = fieldValue.getTo();

            Expression<?> fromField = expressionBuilder.getExpression(root, f, segmentIntersection.fromField());
            Expression<?> toField = expressionBuilder.getExpression(root, f, segmentIntersection.toField());

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
            container.add(predicate);
        }
    }

    private boolean match(FieldWithValue field) {
        return field.isAnnotatedBy(SegmentIntersection.class) && field.isValueSegmentFilter();
    }
}
