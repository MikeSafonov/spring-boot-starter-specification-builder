package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 * @author MikeSafonov
 */
class LessThanPredicateBuilderTest extends BasePredicateTest {
    private LessThanPredicateBuilder builder;

    @BeforeEach
    void setUpBuilder() {
        builder = new LessThanPredicateBuilder(expressionBuilder, fieldWithValue);
    }

    @Test
    void shouldCallLessThanOnExpression() {
        builder.build(root, criteriaQuery, cb);

        verify(cb).lessThan(expression, (Comparable)fieldWithValue.getValue());
        verifyNoMoreInteractions(expression);
    }
}
