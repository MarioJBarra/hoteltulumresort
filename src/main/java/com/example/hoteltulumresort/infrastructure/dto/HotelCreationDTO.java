package com.example.hoteltulumresort.infrastructure.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelCreationDTO {
    @NotBlank(message = "The name is required.")
    private String name;
    @Max(value = 5, message = "The max number of start are 5")
    @Positive
    private int stars;

    @Valid
    @NotNull(message = "The address is required")
    private AddressDTO address;
}
