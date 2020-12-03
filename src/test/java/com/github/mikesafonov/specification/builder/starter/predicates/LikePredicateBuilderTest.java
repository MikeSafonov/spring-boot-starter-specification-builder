package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sun.reflect.annotation.AnnotationParser;

import javax.persistence.criteria.Expression;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

/**
 * @author MikeSafonov
 */
class LikePredicateBuilderTest extends BasePredicateTest {

    private static Stream<Arguments> directionArguments() {
        return Stream.of(
            Arguments.of(Like.DIRECTION.AROUND, "%some value%"),
            Arguments.of(Like.DIRECTION.RIGHT, "some value%"),
            Arguments.of(Like.DIRECTION.LEFT, "%some value")
        );
    }

    @ParameterizedTest
    @MethodSource("directionArguments")
    void shouldCallLikeCaseSensitive(Like.DIRECTION direction, String expectedValue) {
        Like like = getLike(direction, true);
        LikePredicateBuilder builder = new LikePredicateBuilder(expressionBuilder, fieldWithValue, like);

        when(expression.as(String.class)).thenReturn(expression);

        builder.build(root, criteriaQuery, cb);

        verify(cb).like(expression, expectedValue);
    }

    @ParameterizedTest
    @MethodSource("directionArguments")
    void shouldCallLikeUpper(String value, Like.DIRECTION direction, String expectedValue) {
        Expression upperExpression = mock(Expression.class);

        Like like = getLike(direction, false);
        LikePredicateBuilder builder = new LikePredicateBuilder(expressionBuilder, fieldWithValue, like);

        when(expression.as(String.class)).thenReturn(expression);
        when(cb.upper(expression)).thenReturn(upperExpression);

        builder.build(root, criteriaQuery, cb);

        verify(cb).like(upperExpression, expectedValue.toUpperCase());
    }

    private Like getLike(Like.DIRECTION direction, boolean caseSensitive) {
        Map<String, Object> annotationParameters = new HashMap<>();
        annotationParameters.put("direction", direction);
        annotationParameters.put("caseSensitive", caseSensitive);

        return (Like) AnnotationParser.annotationForMap(Like.class, annotationParameters);
    }

}
