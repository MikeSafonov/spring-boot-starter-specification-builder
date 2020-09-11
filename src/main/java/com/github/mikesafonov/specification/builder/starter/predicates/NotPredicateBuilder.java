package com.github.mikesafonov.specification.builder.starter.predicates;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class NotPredicateBuilder implements PredicateBuilder {
    private final CriteriaBuilder cb;
    private final PredicateBuilder predicateBuilder;

    public NotPredicateBuilder(CriteriaBuilder cb, PredicateBuilder predicateBuilder) {
        this.cb = cb;
        this.predicateBuilder = predicateBuilder;
    }

    @Override
    public Predicate build() {
        return cb.not(predicateBuilder.build());
    }
}
