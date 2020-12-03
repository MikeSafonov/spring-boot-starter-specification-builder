package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.predicates.PredicateBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Mike Safonov
 */
@Slf4j
@RequiredArgsConstructor
public class SelfBuildSpecification<S> implements Specification<S> {

    private final List<PredicateBuilder> predicateBuilders;
    @Override
    public Predicate toPredicate(Root<S> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate[] predicates = predicateBuilders.stream()
            .map(builder -> builder.build(root, query, cb))
            .toArray(Predicate[]::new);
        return cb.and(predicates);
    }
}
