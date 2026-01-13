package com.example.hoteltulumresort.domain;

import java.util.UUID;

public record Hotel(UUID id, String name, int stars, Address address) {

    public Hotel withAddress(Address newAddress) {
        return new Hotel(this.id, this.name, this.stars, newAddress);
    }
}
