package org.anas.debriefingspringboot.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "fields")
public class Field {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private double area;

    @ManyToOne(optional = false)
    private Farm farm;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
