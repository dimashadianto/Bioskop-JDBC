package com.enigma.repository;

import com.enigma.entitiy.Rating;

import java.util.List;

public interface RatingRepo {
    List<Rating> selectAll();
    Rating getById(Integer id);
}
