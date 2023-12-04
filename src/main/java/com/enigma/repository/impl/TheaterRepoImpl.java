package com.enigma.repository.impl;

import com.enigma.entitiy.Theater;
import com.enigma.repository.TheaterRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheaterRepoImpl implements TheaterRepo {

    Connection con;

    public TheaterRepoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public List<Theater> selectAll() {
        List<Theater> data = new ArrayList<>();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM t_theater");
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id");
                String theaterNumber = result.getString("theater_number");
                Integer stock = result.getInt("stock");
                Integer filmId = result.getInt("film_id");
                data.add(new Theater(id, theaterNumber, stock, filmId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @Override
    public void create(Theater theater) {
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO t_theater (theater_number, stock, film_id) VALUES (?, ?, ?)");
            pr.setString(1, theater.getTheaterNumber());
            pr.setInt(2, theater.getStock());
            pr.setInt(3, theater.getFilmId());
            pr.executeUpdate();

            System.out.println("Theater " + theater.getTheaterNumber() + "successfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Theater theater) {
        Theater tUpdate = getById(theater.getId());
        if (tUpdate != null) {
            try {
                PreparedStatement pr = con.prepareStatement("UPDATE t_theater SET theater_number = ?, stock = ?, film_id = ? WHERE id = ?");
                pr.setString(1, theater.getTheaterNumber());
                pr.setInt(2, theater.getStock());
                pr.setInt(3, theater.getFilmId());
                pr.setInt(4, theater.getId());
                pr.executeUpdate();

                System.out.println("Successfully update theater");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void delete(Integer id) {
        Theater tDelete = getById(id);
        if (tDelete != null || id > 0) {
            try {
                if (checkExistTheaterId(id)) {
                    System.out.println("Cannot delete theater because has ticket transactions");
                } else {
                    con.setAutoCommit(false);
                    deleteSeatByTheaterId(id);
                    PreparedStatement pr = con.prepareStatement("DELETE FROM t_theater WHERE id = ?");
                    pr.setInt(1, id);
                    pr.executeUpdate();
                    con.commit();
                    System.out.println("Successfully delete theater");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else System.out.println("Theater id not exist");
    }

    private boolean checkExistTheaterId(Integer theaterId) {
        try {
            PreparedStatement pr = con.prepareStatement("SELECT COUNT(s.theater_id) AS ticket_count FROM trx_ticket tt JOIN t_seat s ON s.id = tt.seat_id WHERE s.theater_id = ?");
            pr.setInt(1, theaterId);
            ResultSet resultSet = pr.executeQuery();
            if (resultSet.next()) {
                int ticketCount = resultSet.getInt("ticket_count");
                return ticketCount > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void deleteSeatByTheaterId(Integer theaterId) {
        try {
            PreparedStatement pr = con.prepareStatement("DELETE from t_seat where theater_id = ?");
            pr.setInt(1, theaterId);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Theater getById(Integer id) {
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM t_theater WHERE id = ?");
            pr.setInt(1, id);
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                return new Theater (
                        result.getInt("id"),
                        result.getString("theater_number"),
                        result.getInt("stock"),
                        result.getInt("film_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}