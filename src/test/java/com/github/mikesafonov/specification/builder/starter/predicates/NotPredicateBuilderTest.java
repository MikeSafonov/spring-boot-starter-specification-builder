package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class NotPredicateBuilderTest {

    private CriteriaBuilder cb;
    private PredicateBuilder anotherBuilder;

    private NotPredicateBuilder notPredicateBuilder;

    @BeforeEach
    void setUp() {
        cb = mock(CriteriaBuilder.class);
        anotherBuilder = mock(PredicateBuilder.class);

        notPredicateBuilder = new NotPredicateBuilder(cb, anotherBuilder);
    }

    @Test
    void shouldCallNotOnAnotherBuilder() {
        notPredicateBuilder.build();

        verify(cb).not(anotherBuilder.build());
    }
}