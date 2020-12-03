package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class NotPredicateBuilderTest {

    private CriteriaBuilder cb;
    private Root root;
    private CriteriaQuery criteriaQuery;
    private PredicateBuilder anotherBuilder;

    private NotPredicateBuilder notPredicateBuilder;

    @BeforeEach
    void setUp() {
        cb = mock(CriteriaBuilder.class);
        root = mock(Root.class);
        criteriaQuery = mock(CriteriaQuery.class);
        anotherBuilder = mock(PredicateBuilder.class);

        notPredicateBuilder = new NotPredicateBuilder(anotherBuilder);
    }

    @Test
    void shouldCallNotOnAnotherBuilder() {
        notPredicateBuilder.build(root, criteriaQuery, cb);

        verify(cb).not(anotherBuilder.build(root, criteriaQuery, cb));
    }
}
