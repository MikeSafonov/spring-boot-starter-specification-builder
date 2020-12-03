package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Builder for collection {@code in} predicate
 *
 * @author MikeSafonov
 */
public class CollectionPredicateBuilder extends SimplePredicateBuilder {

    public CollectionPredicateBuilder(ExpressionBuilder expressionBuilder, FieldWithValue field) {
        super(expressionBuilder, field);
    }

    @Override
    public Predicate build(Root<?> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        return getExpression(root).in(field.getValueAsCollection());
    }
}
