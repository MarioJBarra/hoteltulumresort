package com.example.hoteltulumresort.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaHotelRepository extends JpaRepository<HotelEntity, UUID> {

    List<HotelEntity> findByCity(String city);
}
