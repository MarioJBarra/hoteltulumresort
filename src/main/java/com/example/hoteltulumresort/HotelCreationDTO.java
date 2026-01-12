package com.example.hoteltulumresort;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Generated;

@Generated
public final class HotelCreationDTO {
    @NotBlank(message = "The name is required.")
    private String name;
    @Max(value = 5, message = "The max number of start are 5")
    @Positive
    private int stars;

    @NotBlank(message = "The street is required.")
    private String street;
    @NotBlank(message = "The city is required.")
    private String city;
    @NotBlank(message = "The country is required.")
    private String country;
    @NotBlank(message = "The postal code is required.")
    private String postalCode;



}
