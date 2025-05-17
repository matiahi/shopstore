package com.mystore.shopstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_order") // order is reserved word :((
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String address;

    @Column(length = 1000)
    private String note;

    // Constructors
    public Order() {
    }

    public Order(String fullName, String email, String address, String note) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.note = note;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
