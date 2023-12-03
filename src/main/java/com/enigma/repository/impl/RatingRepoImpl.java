package com.enigma.repository.impl;

import com.enigma.entitiy.Rating;
import com.enigma.repository.RatingRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingRepoImpl implements RatingRepo {

    Connection con;

    public RatingRepoImpl(Connection con) {
        this.con = con;
    }

    public RatingRepoImpl() {
    }

    @Override
    public List<Rating> selectAll() {
        List<Rating> data = new ArrayList<>();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM t_rating");
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id");
                String code = result.getString("code");
                String description = result.getString("description");
                data.add(new Rating(id, code, description));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @Override
    public Rating getById(Integer ratingId) {
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM t_rating WHERE id = ?");
            pr.setInt(1, ratingId);
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                return new Rating (
                        result.getInt("id"),
                        result.getString("code"),
                        result.getString("description"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}