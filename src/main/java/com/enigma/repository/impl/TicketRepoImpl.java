package com.enigma.repository.impl;

import com.enigma.entitiy.Ticket;
import com.enigma.repository.TicketRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class TicketRepoImpl implements TicketRepo {

    Connection con;

    public TicketRepoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public List<Ticket> selectAll() {
        List<Ticket> data = new ArrayList<>();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM trx_ticket");
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id");
                Integer seatId = result.getInt("seat_id");
                Integer customerId = result.getInt("customer_id");
                data.add(new Ticket(id, seatId, customerId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @Override
    public void create(Ticket ticket) {
        try {
            con.setAutoCommit(false);
            PreparedStatement pr = con.prepareStatement("INSERT INTO trx_ticket (seat_id, customer_id) VALUES (?, ?)");
            pr.setInt(1, ticket.getSeatId());
            pr.setInt(2, ticket.getCustomerId());

            Integer age = getAgeCustomer(ticket.getCustomerId());
            String ratingCode = getRatingCode(ticket.getSeatId());

            if (ratingCode != null) {
                switch (ratingCode) {
                    case "A":
                        System.out.println("Allowed to watch the film");
                        break;
                    case "BO":
                        if (age < 13) {
                            System.out.println("Under 13 years old not allowed to watch the film");
                            throw new SQLException();
                        }
                        break;
                    case "R":
                        if (age < 18) {
                            System.out.println("Under 18 years old not allowed to watch the film");
                            throw new SQLException();
                        }
                        break;
                    case "D":
                        if (age < 21) {
                            System.out.println("Under 21 years old not allowed to watch the film");
                            throw new SQLException();
                        }
                        break;
                }
            }
            pr.executeUpdate();

            PreparedStatement pr1 = con.prepareStatement("UPDATE t_theater SET stock = stock - 1 WHERE id = ?");
            pr1.setInt(1, ticket.getSeatId());
            pr1.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void update(Ticket ticket) {
        Ticket ticUpdate = getById(ticket.getId());
        if (ticUpdate != null) {
            try {
                PreparedStatement pr = con.prepareStatement("UPDATE trx_ticket SET seat_id = ?, customer_id = ? WHERE id = ?");
                pr.setInt(1, ticket.getSeatId());
                pr.setInt(2, ticket.getCustomerId());
                pr.setInt(3, ticket.getId());

                pr.executeUpdate();
                System.out.println("Successfully update ticket");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void delete(Integer ticketId) {
        Ticket ticDelete = getById(ticketId);
        if (ticDelete != null || ticketId > 0) {
            try {
                PreparedStatement pr = con.prepareStatement("DELETE FROM trx_ticket WHERE id = ?");
                pr.setInt(1, ticketId);

                pr.executeUpdate();
                System.out.println("Successfully delete ticket");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Ticket getById(Integer ticketId) {
        try {
            PreparedStatement pr = con.prepareStatement("SELECT * FROM trx_ticket WHERE id = ?");
            pr.setInt(1, ticketId);
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                return new Ticket (
                        result.getInt("id"),
                        result.getInt("seat_id"),
                        result.getInt("customer_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Integer getAgeCustomer(Integer customerId) {
        try {
            PreparedStatement pr1 = con.prepareStatement("SELECT birth_date FROM m_customer WHERE id = ?");
            pr1.setInt(1, customerId);
            ResultSet resultSet = pr1.executeQuery();
            if (resultSet.next()) {
                String birthDate = resultSet.getString("birth_date");
                LocalDate localDate = LocalDate.parse(birthDate);
                return Period.between(localDate, LocalDate.now()).getYears();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String getRatingCode(Integer seatId) {
        try {
            PreparedStatement pr = con.prepareStatement("SELECT r.code\n" +
                    "FROM t_seat s JOIN t_theater t ON s.theater_id = t.id\n" +
                    "JOIN t_film f ON t.film_id = f.id\n" +
                    "JOIN t_rating r ON f.rating_id = r.id WHERE s.id = ?");
            pr.setInt(1, seatId);
            ResultSet resultSet = pr.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("code");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}