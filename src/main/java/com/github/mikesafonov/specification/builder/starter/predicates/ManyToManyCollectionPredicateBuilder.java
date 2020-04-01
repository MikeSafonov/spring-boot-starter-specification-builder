package com.github.mikesafonov.specification.builder.starter.predicates;

import com.github.mikesafonov.specification.builder.starter.ExpressionBuilder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

/**
 * The predicate is constructed according to the following scheme:
 * for each element of the collection a subquery is created that checks the existence of an
 * entry in the intersection table for the link between the related and target table.
 *
 * @param <T>
 * @author MikeSafonov
 */
@RequiredArgsConstructor
public class ManyToManyCollectionPredicateBuilder<T> implements PredicateBuilder {
    private final Root<T> root;
    private final CriteriaBuilder cb;
    private final CriteriaQuery<?> cq;
    private final Collection fieldValue;
    private final Field field;
    private final String fieldName;
    private final ExpressionBuilder expressionBuilder;

    @Override
    public Predicate build() {
        List<Predicate> predicates = new ArrayList<>();
        for (Object filter : fieldValue) {
            Subquery<T> sq = cq.subquery((Class<T>) root.getJavaType());
            Root<T> project = sq.from((Class<T>) root.getJavaType());

            Expression<T> expr = expressionBuilder.getExpression(project, field, fieldName, cb.equal(project, root));
            sq.select(project).where(cb.equal(expr, filter));
            predicates.add(cb.exists(sq));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
