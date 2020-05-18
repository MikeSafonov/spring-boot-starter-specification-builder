package com.github.mikesafonov.specification.builder.starter.base.persons;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Mike Safonov
 */
@Data
@Entity
@Table(name = "person_infos")
public class PersonInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToOne
    @JoinColumn(name="id_person")
    private PersonEntity person;

}
