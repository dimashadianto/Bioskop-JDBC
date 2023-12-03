package com.enigma.repository;

import com.enigma.entitiy.Theater;

import java.util.List;

public interface TheaterRepo {
    List<Theater> selectAll();
    void create(Theater theater);
    void update(Theater theater);
    void delete(Integer id);
    Theater getById(Integer id);
}