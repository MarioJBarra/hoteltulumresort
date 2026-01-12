package com.example.hoteltulumresort;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class HotelController {

    @GetMapping("/hotels")
    public List<Hotel> getHotels() {
        return Collections.emptyList();
    }

    @PostMapping("/hotels")
    public Hotel createHotel(@RequestBody HotelCreationDTO  hotelToCreate) {
        return null;
    }

}
