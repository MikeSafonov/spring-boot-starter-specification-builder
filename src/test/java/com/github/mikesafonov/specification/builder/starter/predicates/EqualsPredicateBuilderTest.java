package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author MikeSafonov
 */
class EqualsPredicateBuilderTest extends BasePredicateTest {

    private EqualsPredicateBuilder builder;

    @BeforeEach
    void setUpBuilder() {
        builder = new EqualsPredicateBuilder(expressionBuilder, fieldWithValue);
    }

    @Test
    void shouldCallEqualsOnExpression() {

        builder.build(root, criteriaQuery, cb);

        verify(cb).equal(expression, fieldWithValue.getValue());
        verifyNoMoreInteractions(expression);
    }
}
