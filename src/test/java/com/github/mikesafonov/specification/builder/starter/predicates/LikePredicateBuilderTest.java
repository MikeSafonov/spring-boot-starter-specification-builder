package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sun.reflect.annotation.AnnotationParser;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

/**
 *
 * @author MikeSafonov
 */
class LikePredicateBuilderTest {

    private static Stream<Arguments> directionArguments() {
        return Stream.of(
                Arguments.of("some value", Like.DIRECTION.AROUND, "%some value%"),
                Arguments.of("some value", Like.DIRECTION.RIGHT, "some value%"),
                Arguments.of("some value", Like.DIRECTION.LEFT, "%some value")
        );
    }

    @ParameterizedTest
    @MethodSource("directionArguments")
    void shouldCallLikeCaseSensitive(String value, Like.DIRECTION direction, String expectedValue) {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Expression expression = mock(Expression.class);
        Like like = getLike(direction, true);
        LikePredicateBuilder builder = new LikePredicateBuilder(cb, like, value, expression);

        when(expression.as(String.class)).thenReturn(expression);

        builder.build();

        verify(cb).like(expression, expectedValue);
    }

    @ParameterizedTest
    @MethodSource("directionArguments")
    void shouldCallLikeUpper(String value, Like.DIRECTION direction, String expectedValue) {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Expression expression = mock(Expression.class);
        Expression upperExpression = mock(Expression.class);

        Like like = getLike(direction, false);
        LikePredicateBuilder builder = new LikePredicateBuilder(cb, like, value, expression);

        when(expression.as(String.class)).thenReturn(expression);
        when(cb.upper(expression)).thenReturn(upperExpression);

        builder.build();

        verify(cb).like(upperExpression, expectedValue.toUpperCase());
    }

    private Like getLike(Like.DIRECTION direction, boolean caseSensitive) {
        Map<String, Object> annotationParameters = new HashMap<>();
        annotationParameters.put("direction", direction);
        annotationParameters.put("caseSensitive", caseSensitive);

        return (Like) AnnotationParser.annotationForMap(Like.class, annotationParameters);
    }

}
