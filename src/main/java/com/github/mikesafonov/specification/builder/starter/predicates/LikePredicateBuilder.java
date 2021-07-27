package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.annotations.Like;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Builder for {@code like} predicate
 *
 * @author MikeSafonov
 */
public class LikePredicateBuilder extends SimplePredicateBuilder {

    private static final String PERCENT = "%";

    private final CriteriaBuilder cb;
    private final Like like;
    private final Expression<String> valueExpression;

    public LikePredicateBuilder(CriteriaBuilder cb, Like like, Expression<?> valueExpression, Expression expression) {
        super(expression);
        this.cb = cb;
        this.like = like;
        this.valueExpression = valueExpression.as(String.class);
    }


    @Override
    public Predicate build() {
        Expression<String> searchExpression = getSearchValue();
        Expression<String> expr = expression.as(String.class);
        if (!like.caseSensitive()) {
            expr = cb.upper(expr);
            searchExpression = cb.upper(searchExpression);
        }
        return cb.like(expr, searchExpression);
    }

    private Expression<String> getSearchValue() {
        switch (like.direction()) {
            case LEFT:
                return cb.concat(PERCENT, valueExpression);
            case RIGHT:
                return cb.concat(valueExpression, PERCENT);
            case NONE:
                return valueExpression;
            default:
                return cb.concat(cb.concat(PERCENT, valueExpression), PERCENT);
        }
    }
}
