package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.predicates.PredicateBuilderFactory;
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

    private final List<FieldWithValue> fieldsForPredicate;

    @Override
    public Predicate toPredicate(Root<S> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        PredicateBuilderFactory factory = new PredicateBuilderFactory();
        Predicate[] predicates = fieldsForPredicate.stream()
            .map(field -> factory.createPredicateBuilder(root, cb, query, field).build())
            .toArray(Predicate[]::new);
        return cb.and(predicates);
    }
}
