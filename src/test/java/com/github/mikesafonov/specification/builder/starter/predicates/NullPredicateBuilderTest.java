package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

class NullPredicateBuilderTest {
    @Test
    void shouldCallIsNull(){
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Expression expression = mock(Expression.class);
        NullPredicateBuilder builder = new NullPredicateBuilder(cb);

        builder.build(expression);

        verify(cb).isNull(expression);
        verifyNoMoreInteractions(expression);
    }
}
