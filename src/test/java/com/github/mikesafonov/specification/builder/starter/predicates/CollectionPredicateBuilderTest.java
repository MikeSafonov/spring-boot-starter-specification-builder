package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 * @author MikeSafonov
 */
class CollectionPredicateBuilderTest extends BasePredicateTest {

    private CollectionPredicateBuilder builder;

    @BeforeEach
    void setUpBuilder() {
        builder = new CollectionPredicateBuilder(expressionBuilder, fieldWithValue);
    }

    @Test
    void shouldCallEqualsOnExpression() {

        builder.build(root, criteriaQuery, cb);

        verify(expression).in(fieldWithValue.getValueAsCollection());
        verifyNoMoreInteractions(expression);
    }
}
