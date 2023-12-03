package com.enigma.entitiy;

import java.sql.Date;

public class Customer {
    private Integer id;
    private String name;
    private Date birthDate;

    public Customer() {
    }

    public Customer(Integer id, String name, String birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = Date.valueOf(birthDate);
    }

    public Customer(String name, String birthDate) {
        this.name = name;
        this.birthDate = Date.valueOf(birthDate);
    }

    @Override
    public String toString() {
        return "Customer {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", birth_date = '" + birthDate + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
