package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import static org.mockito.Mockito.*;

class NotNullPredicateBuilderTest {
    @Test
    void shouldCallIsNotNull(){
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Expression expression = mock(Expression.class);
        NotNullPredicateBuilder builder = new NotNullPredicateBuilder(cb, expression);

        builder.build();

        verify(cb).isNotNull(expression);
        verifyNoMoreInteractions(expression);
    }
}
