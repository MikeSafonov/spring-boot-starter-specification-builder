package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Builder for {@code isNotNull} predicate
 *
 * @author MikeSafonov
 */
public class NotNullPredicateBuilder extends SimplePredicateBuilder {

    public NotNullPredicateBuilder(ExpressionBuilder expressionBuilder, FieldWithValue field) {
        super(expressionBuilder, field);
    }

    @Override
    public Predicate build(Root<?> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        return cb.isNotNull(getExpression(root));
    }
}
