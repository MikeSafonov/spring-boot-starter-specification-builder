package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.FieldWithValue;
import com.github.mikesafonov.specification.builder.starter.base.cars.CarFilter;
import com.github.mikesafonov.specification.builder.starter.type.SegmentFilter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author MikeSafonov
 */
class PredicateBuilderFactoryTest {

    @Test
    @SneakyThrows
    void shouldReturnCollectionPredicateBuilder() {
        String fieldName = "collection";
        Field field = CarFilter.class.getDeclaredField(fieldName);
        Object value = new ArrayList<>();
        PredicateBuilder builder = new PredicateBuilderFactory()
            .createPredicateBuilder(new FieldWithValue(field, value));

        assertThat(builder).isInstanceOf(CollectionPredicateBuilder.class);
    }

    @Test
    void shouldReturnManyToManyCollectionPredicateBuilder() {
        assertThat(createBuilderForField("manyToManyCollection", new ArrayList<>()))
            .isInstanceOf(ManyToManyCollectionPredicateBuilder.class);
    }

    @Test
    void shouldReturnLikePredicateBuilder() {
        assertThat(createBuilderForField("likeValue")).isInstanceOf(LikePredicateBuilder.class);
    }

    @Test
    void shouldReturnNotNullPredicateBuilder() {
        assertThat(createBuilderForField("nonNullValue")).isInstanceOf(NotNullPredicateBuilder.class);
    }

    @Test
    void shouldReturnNullPredicateBuilder() {
        assertThat(createBuilderForField("nullValue")).isInstanceOf(NullPredicateBuilder.class);
    }

    @Test
    void shouldReturnEqualsPredicateBuilder() {
        assertThat(createBuilderForField("id")).isInstanceOf(EqualsPredicateBuilder.class);
    }

    @Test
    void shouldReturnGreaterThanEqualsPredicateBuilder() {
        assertThat(createBuilderForField("idGreaterThanEqual")).isInstanceOf(GreaterThanEqualPredicateBuilder.class);
    }

    @Test
    void shouldReturnGreaterThanPredicateBuilder() {
        assertThat(createBuilderForField("idGreaterThan")).isInstanceOf(GreaterThanPredicateBuilder.class);
    }

    @Test
    void shouldReturnLessThanEqualsPredicateBuilder() {
        assertThat(createBuilderForField("idLessThanEqual")).isInstanceOf(LessThanEqualPredicateBuilder.class);
    }

    @Test
    void shouldReturnSegmentIntersectionPredicateBuilder() {
        assertThat(createBuilderForField("costFilter", new SegmentFilter<>(1, 5)))
            .isInstanceOf(SegmentIntersectionPredicateBuilder.class);
    }

    @Test
    void shouldCreateAndPredicateBuilder() {
        assertThat(createBuilderForField("namesNonNullAnd")).isInstanceOf(AndPredicateBuilder.class);
    }

    @Test
    void shouldCreateOrPredicateBuilder() {
        assertThat(createBuilderForField("namesNonNull")).isInstanceOf(OrPredicateBuilder.class);
    }

    @Test
    void shouldCreateNotPredicateBuilder() {
        assertThat(createBuilderForField("notNullValue")).isInstanceOf(NotPredicateBuilder.class);
    }

    private PredicateBuilder createBuilderForField(String fieldName) {
        return createBuilderForField(fieldName, "");
    }

    @SneakyThrows
    private PredicateBuilder createBuilderForField(String fieldName, Object value) {
        Field field = CarFilter.class.getDeclaredField(fieldName);
        return new PredicateBuilderFactory().createPredicateBuilder(new FieldWithValue(field, value));
    }
}
