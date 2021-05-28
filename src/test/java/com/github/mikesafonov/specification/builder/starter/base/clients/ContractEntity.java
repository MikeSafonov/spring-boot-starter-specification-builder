package com.github.mikesafonov.specification.builder.starter.base.clients;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Mike Safonov
 */
@Data
@Entity
@Table(name = "contracts")
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String number;

    @Column(name = "id_client")
    private Long idClient;
}
