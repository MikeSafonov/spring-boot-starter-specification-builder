package com.github.mikesafonov.specification.builder.starter.predicates;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Collection;

/**
 * Builder for collection {@code in} predicate
 *
 * @author MikeSafonov
 */
public class CollectionPredicateBuilder extends SimplePredicateBuilder {
    private final Collection fieldValue;

    public CollectionPredicateBuilder(Collection fieldValue, Expression expression) {
        super(expression);
        this.fieldValue = fieldValue;
    }

    @Override
    public Predicate build() {
        return expression.in(fieldValue);
    }
}
