package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 * @author MikeSafonov
 */
class GreaterThanPredicateBuilderTest extends BasePredicateTest {

    private GreaterThanPredicateBuilder builder;

    @BeforeEach
    void setUpBuilder() {
        builder = new GreaterThanPredicateBuilder(expressionBuilder, fieldWithValue);
    }

    @Test
    void shouldCallGreaterThanOnExpression(){
        builder.build(root, criteriaQuery, cb);

        verify(cb).greaterThan(expression, (Comparable) fieldWithValue.getValue());
        verifyNoMoreInteractions(expression);
    }
}
