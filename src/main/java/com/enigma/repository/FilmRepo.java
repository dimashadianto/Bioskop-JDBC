package com.enigma.repository;

import com.enigma.entitiy.Film;

import java.util.List;

public interface FilmRepo {
    List<Film> selectAll();
    void create(Film film);
    void update(Film film);
    void delete(Integer id);
    Film getById(Integer id);
}
