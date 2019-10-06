package com.github.mikesafonov.specification.builder.starter.predicates;

import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Builder for {@code isNull} predicate
 *
 * @author MikeSafonov
 */
@RequiredArgsConstructor
public class NullPredicateBuilder implements PredicateBuilder {
    private final CriteriaBuilder cb;

    @Override
    public Predicate build(Expression expression) {
        return cb.isNull(expression);
    }
}
