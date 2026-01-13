package com.example.hoteltulumresort.infrastructure.http;

import com.example.hoteltulumresort.aplication.*;
import com.example.hoteltulumresort.domain.Address;
import com.example.hoteltulumresort.domain.Hotel;
import com.example.hoteltulumresort.infrastructure.dto.HotelCreationDTO;
import com.example.hoteltulumresort.infrastructure.dto.HotelResponseDTO;
import com.example.hoteltulumresort.infrastructure.dto.UpdateAddressDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final CreateHotelUseCase createHotelUseCase;
    private final ListHotelsUseCase listHotelsUseCase;
    private final FindHotelsByCityUseCase findHotelsByCityUseCase;
    private final UpdateHotelAddressUseCase updateHotelAddressUseCase;
    private final DeleteHotelUseCase deleteHotelUseCase;

    @PostMapping("/hotels")
    public ResponseEntity<HotelResponseDTO> createHotel(@Valid @RequestBody HotelCreationDTO dto) {
        Address address = new Address(
                dto.getAddress().getStreet(),
                dto.getAddress().getCity(),
                dto.getAddress().getCountry(),
                dto.getAddress().getPostalCode()
        );
        Hotel created = createHotelUseCase.execute(dto.getName(), dto.getStars(), address);
        return ResponseEntity.status(HttpStatus.CREATED).body(HotelResponseDTO.fromDomain(created));
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelResponseDTO>> listHotels() {
        List<HotelResponseDTO> hotels = listHotelsUseCase.execute().stream()
                .map(HotelResponseDTO::fromDomain)
                .toList();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/hotels/search")
    public ResponseEntity<List<HotelResponseDTO>> findByCity(@RequestParam String city) {
        List<HotelResponseDTO> hotels = findHotelsByCityUseCase.execute(city).stream()
                .map(HotelResponseDTO::fromDomain)
                .toList();
        return ResponseEntity.ok(hotels);
    }

    @PatchMapping("/hotels/{id}/address")
    public ResponseEntity<HotelResponseDTO> updateAddress(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAddressDTO dto) {
        Address newAddress = new Address(
                dto.getStreet(),
                dto.getCity(),
                dto.getCountry(),
                dto.getPostalCode()
        );
        Hotel updated = updateHotelAddressUseCase.execute(id, newAddress);
        return ResponseEntity.ok(HotelResponseDTO.fromDomain(updated));
    }

    @DeleteMapping("/hotels/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHotel(@PathVariable UUID id) {
        deleteHotelUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
