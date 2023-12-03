package com.enigma.repository;

import com.enigma.entitiy.Seat;
import com.enigma.entitiy.Ticket;

import java.util.List;

public interface TicketRepo {
    List<Ticket> selectAll();
    void create(Ticket ticket);
    void update(Ticket ticket);
    void delete(Integer id);
    Ticket getById(Integer id);
}
