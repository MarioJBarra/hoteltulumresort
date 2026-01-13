package com.example.hoteltulumresort.infrastructure.http;

import com.example.hoteltulumresort.infrastructure.dto.AddressDTO;
import com.example.hoteltulumresort.infrastructure.dto.HotelCreationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class HotelControllerIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    private String basicAuth(String user, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((user + ":" + password).getBytes());
    }

    @Test
    @DisplayName("GET /hotels - Should return 401 without auth")
    void shouldReturn401WithoutAuth() {
        restTestClient.get()
                .uri("/hotels")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("GET /hotels - Should return 200 with auth")
    void shouldGetHotels() {
        restTestClient.get()
                .uri("/hotels")
                .header("Authorization", basicAuth("user", "user123"))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("POST /hotels - Should create hotel")
    void shouldCreateHotel() {
        restTestClient.post()
                .uri("/hotels")
                .header("Authorization", basicAuth("user", "user123"))
                .body(validHotel())
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @DisplayName("POST /hotels - Should return 400 when name is null")
    void shouldReturn400WhenNameIsNull() {
        HotelCreationDTO dto = validHotel();
        dto.setName(null);

        String responseBody = restTestClient.post()
                .uri("/hotels")
                .header("Authorization", basicAuth("user", "user123"))
                .body(dto)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).contains("The name is required");
    }

    @Test
    @DisplayName("DELETE /hotels/{id} - Should return 403 for non-admin")
    void shouldReturn403ForNonAdmin() {
        restTestClient.delete()
                .uri("/hotels/550e8400-e29b-41d4-a716-446655440000")
                .header("Authorization", basicAuth("user", "user123"))
                .exchange()
                .expectStatus()
                .isForbidden();
    }

    @Test
    @DisplayName("DELETE /hotels/{id} - Should return 404 for admin when not found")
    void shouldReturn404WhenHotelNotFound() {
        restTestClient.delete()
                .uri("/hotels/550e8400-e29b-41d4-a716-446655440000")
                .header("Authorization", basicAuth("admin", "admin123"))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    private static HotelCreationDTO validHotel() {
        return new HotelCreationDTO(
                "Hotel Tulum Resort",
                5,
                new AddressDTO("Calle 1", "Tulum", "México", "77760")
        );
    }
}
