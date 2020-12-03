package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 * @author MikeSafonov
 */
class LessThanEqualPredicateBuilderTest extends BasePredicateTest {
    private LessThanEqualPredicateBuilder builder;

    @BeforeEach
    void setUpBuilder() {
        builder = new LessThanEqualPredicateBuilder(expressionBuilder, fieldWithValue);
    }

    @Test
    void shouldCallLessThanEqualOnExpression() {
        builder.build(root, criteriaQuery, cb);

        verify(cb).lessThanOrEqualTo(expression, (Comparable)fieldWithValue.getValue());
        verifyNoMoreInteractions(expression);
    }
}
