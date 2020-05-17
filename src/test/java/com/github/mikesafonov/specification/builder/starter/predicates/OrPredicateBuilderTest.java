package com.github.mikesafonov.specification.builder.starter.predicates;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.function.Function;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Mike Safonov
 */
class OrPredicateBuilderTest {

    @Test
    void shouldCallOrOnExpressionsArray() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Expression[] expressions = new Expression[]{
            mock(Expression.class), mock(Expression.class)
        };
        Function<Expression, PredicateBuilder> function = expression -> new NullPredicateBuilder(cb, expression);
        PredicateBuilder[] expectedBuilders = new PredicateBuilder[]{
            new NullPredicateBuilder(cb, expressions[0]),
            new NullPredicateBuilder(cb, expressions[1])
        };
        Predicate[] expected = new Predicate[]{
            expectedBuilders[0].build(),
            expectedBuilders[1].build()
        };

        OrPredicateBuilder builder = new OrPredicateBuilder(cb, expressions, function);

        builder.build();

        verify(cb).or(expected);
    }

}
