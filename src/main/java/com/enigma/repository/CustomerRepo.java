package com.enigma.repository;

import com.enigma.entitiy.Customer;

import java.util.List;

public interface CustomerRepo {
    List<Customer> selectAll();
    void create(Customer customer);
    void update(Customer customer);
    void delete(Integer id);
    Customer getById(Integer id);
}
