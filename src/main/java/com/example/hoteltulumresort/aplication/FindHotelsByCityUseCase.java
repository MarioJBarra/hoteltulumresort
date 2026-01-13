package com.example.hoteltulumresort.aplication;

import com.example.hoteltulumresort.domain.Hotel;
import com.example.hoteltulumresort.domain.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindHotelsByCityUseCase {

    private final HotelRepository hotelRepository;

    public List<Hotel> execute(String city) {
        return hotelRepository.findByCity(city);
    }
}
