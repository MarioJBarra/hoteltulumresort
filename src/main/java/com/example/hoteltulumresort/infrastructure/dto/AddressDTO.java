package com.example.hoteltulumresort.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotBlank(message = "The street is required")
    private String street;

    @NotBlank(message = "The city is required")
    private String city;

    @NotBlank(message = "The country is required")
    private String country;

    @NotBlank(message = "The postal code is required")
    private String postalCode;
}
