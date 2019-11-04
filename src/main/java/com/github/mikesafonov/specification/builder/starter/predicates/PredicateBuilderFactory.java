package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.annotations.IsNull;
import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import com.github.mikesafonov.specification.builder.starter.annotations.NonNull;
import lombok.experimental.UtilityClass;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Factory for {@link PredicateBuilder} from {@link CriteriaBuilder}, field and fields value
 *
 * @author MikeSafonov
 */
@UtilityClass
public class PredicateBuilderFactory {

    @org.springframework.lang.NonNull
    public static PredicateBuilder createPredicateBuilder(@org.springframework.lang.NonNull CriteriaBuilder cb,
                                                          @org.springframework.lang.NonNull Field field,
                                                          @org.springframework.lang.NonNull Object fieldValue) {
        if (Collection.class.isAssignableFrom(fieldValue.getClass())) {
            Collection collection = (Collection) fieldValue;
            return new CollectionPredicateBuilder(collection);
        }
        if (field.isAnnotationPresent(Like.class)) {
            return new LikePredicateBuilder(cb, field.getAnnotation(Like.class), fieldValue);
        } else if (field.isAnnotationPresent(NonNull.class)) {
            return new NotNullPredicateBuilder(cb);
        } else if (field.isAnnotationPresent(IsNull.class)) {
            return new NullPredicateBuilder(cb);
        }
        return new EqualsPredicateBuilder(cb, fieldValue);
    }
}
