package com.enigma.repository;

import com.enigma.entitiy.Seat;

import java.util.List;

public interface SeatRepo {
    List<Seat> selectAll();
    void create(Seat seat);
    void update(Seat seat);
    void delete(Integer id);
    Seat getById(Integer id);
}