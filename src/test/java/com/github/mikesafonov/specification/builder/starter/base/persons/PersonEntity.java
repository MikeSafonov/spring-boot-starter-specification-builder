package com.github.mikesafonov.specification.builder.starter.base.persons;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Mike Safonov
 */
@Data
@Entity
@Table(name = "persons")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "person")
    private PersonInfoEntity personInfo;
}
