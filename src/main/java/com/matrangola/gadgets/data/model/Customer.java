package com.matrangola.gadgets.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date birthday;

    @OneToMany(mappedBy = "owner", cascade = ALL, fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Gadget> gadgets;

    public Customer() {

    }

    public Customer(String first, String last) {
        firstName = first;
        lastName = last;
    }

    public Customer(String first, String last, Date birthday) {
        this(first, last);
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<Gadget> getGadgets() {
        return gadgets;
    }
}
