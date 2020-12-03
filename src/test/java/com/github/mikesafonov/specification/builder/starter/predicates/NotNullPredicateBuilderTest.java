package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 * @author MikeSafonov
 */
class NotNullPredicateBuilderTest extends BasePredicateTest {
    private NotNullPredicateBuilder builder;

    @BeforeEach
    void setUpBuilder() {
        builder = new NotNullPredicateBuilder(expressionBuilder, fieldWithValue);
    }


    @Test
    void shouldCallIsNotNull(){
        builder.build(root, criteriaQuery, cb);

        verify(cb).isNotNull(expression);
        verifyNoMoreInteractions(expression);
    }
}
