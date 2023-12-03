package com.enigma.repository.impl;

import com.enigma.entitiy.Customer;
import com.enigma.repository.CustomerRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoImpl implements CustomerRepo {

    Connection con;

    public CustomerRepoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public List<Customer> selectAll() {
        List<Customer> data = new ArrayList<>();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM m_customer");
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                String date = result.getString("birth_date");
                data.add(new Customer(id, name, date));
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @Override
    public void create(Customer customer) {
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO m_customer (name, birth_date) VALUES (?, ?)");
            pr.setString(1, customer.getName());
            pr.setDate(2, customer.getBirthDate());
            pr.executeUpdate();

            System.out.println("Customer " + customer.getName() + " successfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Customer customer) {
        Customer cUpdate = getById(customer.getId());
        if (cUpdate != null) {
            try {
                PreparedStatement pr = con.prepareStatement("UPDATE m_customer SET name = ?, birth_date = ? WHERE id = ?");
                pr.setString(1, customer.getName());
                pr.setDate(2, customer.getBirthDate());
                pr.setInt(3, customer.getId());
                pr.executeUpdate();

                System.out.println("Successfully update customer");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void delete(Integer customerId) {
        Customer cDelete = getById(customerId);
        if (cDelete != null || customerId > 0) {
            try {
                PreparedStatement pr = con.prepareStatement("DELETE FROM m_customer WHERE id = ?");
                pr.setInt(1, customerId);
                pr.executeUpdate();

                System.out.println("Successfully delete customer");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Customer getById(Integer customerId) {
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM m_customer WHERE id = ?");
            pr.setInt(1, customerId);
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                return new Customer (
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("birth_date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}