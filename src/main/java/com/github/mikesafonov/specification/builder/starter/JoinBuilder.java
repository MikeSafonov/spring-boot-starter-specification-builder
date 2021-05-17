package com.github.mikesafonov.specification.builder.starter;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Mike Safonov
 */
public class JoinBuilder {

    @NonNull
    public <E> javax.persistence.criteria.Join<?, ?> buildAllJoins(@NonNull Join[] joins,
                                                                   @NonNull Root<E> root,
                                                                   @NonNull Predicate... restrictions) {
        javax.persistence.criteria.Join<?, ?> join = root.join(joins[0].value(), joins[0].type()).on(restrictions);
        for (int i = 1; i < joins.length; i++) {
            join = join.join(joins[i].value(), joins[i].type());
        }
        return join;
    }

    @NonNull
    public <E> javax.persistence.criteria.Join<?, ?> buildRootJoin(@NonNull Join join,
                                                                   @NonNull Root<E> root,
                                                                   @NonNull Predicate... restrictions) {
        return root.join(join.value(), join.type()).on(restrictions);
    }

    @NonNull
    public <E> javax.persistence.criteria.Join<?, ?> buildJoin(@NonNull Join join,
                                                               @NonNull javax.persistence.criteria.Join<?, ?> rootJoin) {
        return rootJoin.join(join.value(), join.type());
    }

}
