package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import com.github.mikesafonov.specification.builder.starter.FieldWithValue;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Mike Safonov
 */
public interface PredicateProcessor {

    default int order() {
        return Integer.MIN_VALUE;
    }

    <T> void process(PredicateContainer container, FieldWithValue field,
                     @org.springframework.lang.NonNull Root<T> root,
                     @org.springframework.lang.NonNull CriteriaBuilder cb,
                     @org.springframework.lang.NonNull CriteriaQuery<?> cq,
                     @org.springframework.lang.NonNull ExpressionBuilder expressionBuilder);


}
