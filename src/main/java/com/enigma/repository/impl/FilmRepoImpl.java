package com.enigma.repository.impl;

import com.enigma.entitiy.Film;
import com.enigma.repository.FilmRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmRepoImpl implements FilmRepo {

    Connection con;

    public FilmRepoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public List<Film> selectAll() {
        List<Film> data = new ArrayList<>();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM t_film");
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id");
                String title = result.getString("title");
                Integer duration = result.getInt("duration");
                String date = result.getString("show_date");
                Integer price = result.getInt("price");
                Integer ratingId = result.getInt("rating_id");
                data.add(new Film(id, title, duration, date, price, ratingId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @Override
    public void create(Film film) {
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO t_film (title, duration, show_date, price, rating_id) VALUES (?, ?, ?, ?, ?)");
            pr.setString(1, film.getTitle());
            pr.setInt(2, film.getDuration());
            pr.setDate(3, film.getShowDate());
            pr.setInt(4, film.getPrice());
            pr.setInt(5, film.getRatingId());
            pr.executeUpdate();

            System.out.println("Film " + film.getTitle() + " successfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Film film) {
        Film fUpdate = getById(film.getId());
        if (fUpdate != null) {
            try {
                PreparedStatement pr = con.prepareStatement("UPDATE t_film SET title = ?, duration = ?, show_date = ?, price = ?, rating_id = ? WHERE id = ?");
                pr.setString(1, film.getTitle());
                pr.setInt(2, film.getDuration());
                pr.setDate(3, film.getShowDate());
                pr.setInt(4, film.getPrice());
                pr.setInt(5, film.getRatingId());
                pr.setInt(6, film.getId());
                pr.executeUpdate();

                System.out.println("Successfully update film");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void delete(Integer filmId) {
        Film fDelete = getById(filmId);
        if (fDelete != null || filmId > 0) {
            try {
                PreparedStatement pr = con.prepareStatement("DELETE FROM t_film WHERE id = ?");
                pr.setInt(1, filmId);
                pr.executeUpdate();

                System.out.println("Successfully delete film");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Film getById(Integer filmId) {
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM t_film WHERE id = ?");
            pr.setInt(1, filmId);
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                return new Film (
                        result.getInt("id"),
                        result.getString("title"),
                        result.getInt("duration"),
                        result.getString("show_date"),
                        result.getInt("price"),
                        result.getInt("rating_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}