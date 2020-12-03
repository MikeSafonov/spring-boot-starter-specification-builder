package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 *
 * @author MikeSafonov
 */
class NullPredicateBuilderTest extends BasePredicateTest {
    private NullPredicateBuilder builder;

    @BeforeEach
    void setUpBuilder() {
        builder = new NullPredicateBuilder(expressionBuilder, fieldWithValue);
    }


    @Test
    void shouldCallIsNull(){
        builder.build(root, criteriaQuery, cb);

        verify(cb).isNull(expression);
        verifyNoMoreInteractions(expression);
    }
}
