package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author MikeSafonov
 */
class GreaterThanEqualPredicateBuilderTest extends BasePredicateTest {

    private GreaterThanEqualPredicateBuilder builder;

    @BeforeEach
    void setUpBuilder() {
        builder = new GreaterThanEqualPredicateBuilder(expressionBuilder, fieldWithValue);
    }

    @Test
    void shouldCallGreaterThanEqualOnExpression() {
         builder.build(root, criteriaQuery, cb);

        verify(cb).greaterThanOrEqualTo(expression, (Comparable)fieldWithValue.getValue());
        verifyNoMoreInteractions(expression);
    }
}
