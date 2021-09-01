package com.github.mikesafonov.specification.builder.starter.query;

import com.github.mikesafonov.specification.builder.starter.annotations.Distinct;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaQuery;

/**
 * @author Mike Safonov
 */
@RequiredArgsConstructor
public class DistinctQueryProcessor implements QueryProcessor {

    private final Class<?> filterClass;

    @Override
    public void process(CriteriaQuery<?> query) {
        query.distinct(filterClass.isAnnotationPresent(Distinct.class));
    }
}
