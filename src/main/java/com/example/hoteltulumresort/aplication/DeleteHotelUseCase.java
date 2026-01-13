package com.example.hoteltulumresort.aplication;

import com.example.hoteltulumresort.domain.HotelRepository;
import com.example.hoteltulumresort.domain.exceptions.HotelNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteHotelUseCase {

    private final HotelRepository hotelRepository;

    public void execute(UUID hotelId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new HotelNotFoundException(hotelId);
        }
        hotelRepository.deleteById(hotelId);
    }
}
