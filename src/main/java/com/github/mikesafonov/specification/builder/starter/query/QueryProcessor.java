package com.github.mikesafonov.specification.builder.starter.query;

import javax.persistence.criteria.CriteriaQuery;

/**
 * @author Mike Safonov
 */
public interface QueryProcessor {

    void process(CriteriaQuery<?> query);
}
