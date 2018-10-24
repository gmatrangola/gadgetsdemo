package com.matrangola.gadgets.data.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "gadget")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Gadget {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column(name = "is_on")
    private boolean on;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    @JsonIdentityReference(alwaysAsId = true)
    private Customer owner;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="color_id", nullable = true)
    private Color color;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
