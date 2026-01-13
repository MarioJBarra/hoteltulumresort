package com.example.hoteltulumresort.aplication;


import com.example.hoteltulumresort.domain.Address;
import com.example.hoteltulumresort.domain.Hotel;
import com.example.hoteltulumresort.domain.HotelRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateHotelUseCase {

    private final HotelRepository hotelRepository;

    public Hotel execute(String name, int stars, Address address) {
        Hotel hotel = new Hotel(UUID.randomUUID(), name, stars, address);
        return hotelRepository.save(hotel);
    }

}
