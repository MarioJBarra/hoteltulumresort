package com.example.hoteltulumresort.infrastructure.jpa;

import com.example.hoteltulumresort.domain.Address;
import com.example.hoteltulumresort.domain.Hotel;
import com.example.hoteltulumresort.domain.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaHotelRepositoryAdapter implements HotelRepository {

    private final JpaHotelRepository jpaRepository;

    @Override
    public Hotel save(Hotel hotel) {
        HotelEntity saved = jpaRepository.save(toEntity(hotel));
        return toDomain(saved);
    }

    @Override
    public List<Hotel> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Hotel> findByCity(String city) {
        return jpaRepository.findByCity(city).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Hotel> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }


    private HotelEntity toEntity(Hotel hotel) {
        return new HotelEntity(
                hotel.id(),
                hotel.name(),
                hotel.stars(),
                hotel.address().street(),
                hotel.address().city(),
                hotel.address().country(),
                hotel.address().postalCode()
        );
    }

    private Hotel toDomain(HotelEntity entity) {
        return new Hotel(
                entity.getId(),
                entity.getName(),
                entity.getStars(),
                new Address(
                        entity.getStreet(),
                        entity.getCity(),
                        entity.getCountry(),
                        entity.getPostalCode()
                )
        );
    }
}
