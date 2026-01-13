package com.example.hoteltulumresort.aplication;

import com.example.hoteltulumresort.domain.Address;
import com.example.hoteltulumresort.domain.Hotel;
import com.example.hoteltulumresort.domain.HotelRepository;
import com.example.hoteltulumresort.domain.exceptions.HotelNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateHotelAddressUseCase {

    private final HotelRepository hotelRepository;

    public Hotel execute(UUID hotelId, Address newAddress) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(hotelId));

        Hotel updatedHotel = hotel.withAddress(newAddress);
        return hotelRepository.save(updatedHotel);
    }
}
