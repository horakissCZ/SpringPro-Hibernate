package com.springpro.repository;

import com.springpro.entity.Singer;

import java.util.List;

public interface SingerRepository {

    List<Singer> findAll();
    List<Singer> findAllWithAlbum();
    Singer findById(Long id);
    Singer save(Singer contact);
    void delete(Singer contact);
}
