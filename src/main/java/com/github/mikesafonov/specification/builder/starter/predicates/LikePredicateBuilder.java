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
    private final Object fieldValue;

    public LikePredicateBuilder(CriteriaBuilder cb, Like like, Object fieldValue, Expression expression) {
        super(expression);
        this.cb = cb;
        this.like = like;
        this.fieldValue = fieldValue;
    }


    @Override
    public Predicate build() {
        String searchValue = getSearchValue();
        Expression<String> expr = expression.as(String.class);
        if (!like.caseSensitive()) {
            expr = cb.upper(expr);
            searchValue = searchValue.toUpperCase();
        }
        return cb.like(expr, searchValue);
    }

    private String getSearchValue() {
        switch (like.direction()) {
            case LEFT:
                return PERCENT + fieldValue;
            case RIGHT:
                return fieldValue + PERCENT;
            case NONE:
                return fieldValue.toString();
            default:
                return PERCENT + fieldValue + PERCENT;
        }
    }
}
