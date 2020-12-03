package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author MikeSafonov
 */
public class GreaterThanEqualPredicateBuilder extends SimplePredicateBuilder {

    public GreaterThanEqualPredicateBuilder(ExpressionBuilder expressionBuilder, FieldWithValue field) {
        super(expressionBuilder, field);
    }

    @Override
    public Predicate build(Root<?> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(getExpression(root), (Comparable) field.getValue());
    }
}
