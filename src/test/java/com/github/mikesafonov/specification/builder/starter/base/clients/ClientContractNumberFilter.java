package com.github.mikesafonov.specification.builder.starter.base.clients;

import com.github.mikesafonov.specification.builder.starter.annotations.Distinct;
import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

/**
 * @author Mike Safonov
 */
@Data
@Distinct
public class ClientContractNumberFilter {

    @Like
    @Join("contracts")
    @Name("number")
    private String contract;
}
