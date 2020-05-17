package com.github.mikesafonov.specification.builder.starter.base.cars;

import javax.persistence.*;

/**
 *
 * @author MikeSafonov
 */
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


    public CarEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CarModel getModel() {
        return model;
    }

    public int getCostFrom() {
        return costFrom;
    }

    public int getCostTo() {
        return costTo;
    }
}
