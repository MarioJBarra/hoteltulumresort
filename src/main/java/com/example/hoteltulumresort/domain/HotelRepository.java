package com.example.hoteltulumresort.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelRepository {
    Hotel save(Hotel hotel);

    List<Hotel> findAll();

    List<Hotel> findByCity(String city);

    Optional<Hotel> findById(UUID id);

    boolean existsById(UUID hotelId);

    void deleteById(UUID hotelId);
}

