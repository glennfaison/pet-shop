package com.faison.models;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    @Column(name = "postOfficeBox", nullable = false)
    private String postOfficeBox;
    private String street;
    private String city;
    private String state;
    @Column(name = "country", nullable = false, unique = true)
    private String country;

    public String getId() {
        return id;
    }

    public Address setId(String id) {
        this.id = id;
        return this;
    }

    public String getPostOfficeBox() {
        return postOfficeBox;
    }

    public Address setPostOfficeBox(String postOfficeBox) {
        this.postOfficeBox = postOfficeBox;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public Address setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }
}
