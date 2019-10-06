package com.github.mikesafonov.specification.builder.starter.predicates;

import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Collection;

/**
 * Builder for collection {@code in} predicate
 *
 * @author MikeSafonov
 */
@RequiredArgsConstructor
public class CollectionPredicateBuilder implements PredicateBuilder {
    private final Collection fieldValue;

    @Override
    public Predicate build(Expression expression) {
        Object[] objects = fieldValue.toArray(new Object[0]);
        return expression.in(objects);
    }
}
