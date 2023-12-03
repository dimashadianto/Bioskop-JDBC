package com.enigma.repository.impl;

import com.enigma.entitiy.Seat;
import com.enigma.repository.SeatRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatRepoImpl implements SeatRepo {

    Connection con;

    public SeatRepoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public List<Seat> selectAll() {
        List<Seat> data = new ArrayList<>();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM t_seat");
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id");
                String seatNumber = result.getString("seat_number");
                Integer theaterIdd = result.getInt("theater_id");
                data.add(new Seat(id, seatNumber, theaterIdd));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @Override
    public void create(Seat seat) {
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO t_seat (seat_number, theater_id) VALUES (?, ?)");
            PreparedStatement pr1 = con.prepareStatement("UPDATE t_theater SET stock = stock + 1 WHERE id = ?");
            pr.setString(1, seat.getSeatNumber());
            pr.setInt(2, seat.getTheaterId());
            pr1.setInt(1, seat.getTheaterId());

            con.setAutoCommit(false);
            pr.executeUpdate();
            pr1.executeUpdate();
            System.out.println("Seat " + seat.getSeatNumber() + " successfully created");
            con.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void update(Seat seat) {
        Seat sUpdate = getById(seat.getId());
        if (sUpdate != null) {
            try {
                PreparedStatement pr = con.prepareStatement("UPDATE t_seat SET seat_number = ?, theater_id = ? WHERE id = ?");
                pr.setString(1, seat.getSeatNumber());
                pr.setInt(2, seat.getTheaterId());
                pr.setInt(3, seat.getId());
                pr.executeUpdate();

                System.out.println("Successfully update seat");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void delete(Integer seatId) {
        Seat sDelete = getById(seatId);
        if (sDelete != null || seatId > 0) {
            try {
                PreparedStatement pr = con.prepareStatement("DELETE FROM t_seat WHERE id = ?");
                PreparedStatement pr1 = con.prepareStatement("UPDATE t_theater SET stock = stock - 1 WHERE id = ?");
                pr.setInt(1, seatId);
                if (sDelete != null) {
                    pr1.setInt(1, sDelete.getTheaterId());
                }

                con.setAutoCommit(false);
                pr.executeUpdate();
                System.out.println("Successfully delete seat");
                pr1.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public Seat getById(Integer seatId) {
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM t_seat WHERE id = ?");
            pr.setInt(1, seatId);
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                return new Seat (
                        result.getInt("id"),
                        result.getString("seat_number"),
                        result.getInt("theater_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}