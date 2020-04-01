package com.github.mikesafonov.specification.builder.starter.type;

import lombok.Value;

@Value
public class SegmentFilter<T> {
    private T from;
    private T to;
}
