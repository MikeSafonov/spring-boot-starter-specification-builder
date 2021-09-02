package com.github.mikesafonov.specification.builder.starter.predicates;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mike Safonov
 */
public class PredicateContainer {

    private final List<Predicate> predicates = new ArrayList<>();

    public void add(Predicate predicate) {
        predicates.add(predicate);
    }

    public Predicate[] asArray() {
        return predicates.toArray(new Predicate[0]);
    }

}
