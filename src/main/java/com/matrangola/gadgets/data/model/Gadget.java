package com.matrangola.gadgets.data.model;

import javax.persistence.*;

@Entity
@Table(name = "gadget")
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
    private Customer owner;

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
}
