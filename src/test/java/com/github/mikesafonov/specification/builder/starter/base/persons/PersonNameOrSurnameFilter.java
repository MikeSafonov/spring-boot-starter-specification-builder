package com.github.mikesafonov.specification.builder.starter.base.persons;

import com.github.mikesafonov.specification.builder.starter.annotations.Join;
import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import com.github.mikesafonov.specification.builder.starter.annotations.Names;
import lombok.Data;

/**
 * @author Mike Safonov
 */
@Data
public class PersonNameOrSurnameFilter {

    @Like
    @Join("personInfo")
    @Names({"name", "surname"})
    private String value;

}
