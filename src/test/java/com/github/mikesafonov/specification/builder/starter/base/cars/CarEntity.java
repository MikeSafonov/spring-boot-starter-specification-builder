package com.github.mikesafonov.specification.builder.starter.base.cars;

import lombok.Data;

import javax.persistence.*;

/**
 *
 * @author MikeSafonov
 */
@Data
@Entity
@Table(name = "cars")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number")
    private String number;

    @Column(name = "cost_from")
    private int costFrom;

    @Column(name = "cost_to")
    private int costTo;

    @ManyToOne
    @JoinColumn(name = "id_model")
    private CarModel model;
}
