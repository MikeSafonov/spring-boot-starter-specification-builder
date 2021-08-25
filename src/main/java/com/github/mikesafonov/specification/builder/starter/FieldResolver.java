package com.github.mikesafonov.specification.builder.starter;

import java.util.stream.Stream;

/**
 * @author Mike Safonov
 */
public interface FieldResolver {

    Stream<FieldWithValue> resolve(Object filter);

}
