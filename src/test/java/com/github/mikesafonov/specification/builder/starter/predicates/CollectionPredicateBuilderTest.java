package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.Expression;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CollectionPredicateBuilderTest {
    @Test
    void shouldCallInOnExpression() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Some string");
        Expression expression = mock(Expression.class);
        CollectionPredicateBuilder builder = new CollectionPredicateBuilder(stringList, expression);


        builder.build();

        verify(expression).in(stringList);
        verifyNoMoreInteractions(expression);
    }
}
