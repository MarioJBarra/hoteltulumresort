package com.example.hoteltulumresort.infrastructure.dto;

import com.example.hoteltulumresort.domain.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDTO {

    private UUID id;
    private String name;
    private int stars;
    private AddressDTO address;

    public static HotelResponseDTO fromDomain(Hotel hotel) {
        return new HotelResponseDTO(
                hotel.id(),
                hotel.name(),
                hotel.stars(),
                new AddressDTO(
                        hotel.address().street(),
                        hotel.address().city(),
                        hotel.address().country(),
                        hotel.address().postalCode()
                )
        );
    }
}
