package com.github.mikesafonov.specification.builder.starter.predicates;

import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

@RequiredArgsConstructor
public class GreaterThanEqualPredicateBuilder implements PredicateBuilder {
    private final CriteriaBuilder cb;
    private final Object fieldValue;
    private final Expression expression;

    @Override
    public Predicate build() {
        return cb.greaterThanOrEqualTo(expression, (Comparable) fieldValue);
    }
}
