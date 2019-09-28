package com.github.mikesafonov.specification.builder.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MikeSafonov
 */
@Configuration
public class AutoConfiguration {
    @Bean
    public SpecificationBuilder specificationBuilder() {
        return new SpecificationBuilder();
    }
}
