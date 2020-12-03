package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.annotations.Like;

import javax.persistence.criteria.*;

/**
 * Builder for {@code like} predicate
 *
 * @author MikeSafonov
 */
public class LikePredicateBuilder extends SimplePredicateBuilder {

    private static final String PERCENT = "%";

    private final Like like;

    public LikePredicateBuilder(ExpressionBuilder expressionBuilder, FieldWithValue field, Like like) {
        super(expressionBuilder, field);
        this.like = like;
    }

    @Override
    public Predicate build(Root<?> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        String searchValue = getSearchValue();
        Expression<String> expr = getExpression(root).as(String.class);
        if (!like.caseSensitive()) {
            expr = cb.upper(expr);
            searchValue = searchValue.toUpperCase();
        }
        return cb.like(expr, searchValue);
    }

    private String getSearchValue() {
        switch (like.direction()) {
            case LEFT:
                return PERCENT + field.getValue();
            case RIGHT:
                return field.getValue() + PERCENT;
            default:
                return PERCENT + field.getValue() + PERCENT;
        }
    }
}
