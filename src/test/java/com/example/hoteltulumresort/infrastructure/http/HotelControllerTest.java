package com.example.hoteltulumresort.infrastructure.http;

import com.example.hoteltulumresort.aplication.*;
import com.example.hoteltulumresort.domain.Address;
import com.example.hoteltulumresort.domain.Hotel;
import com.example.hoteltulumresort.domain.exceptions.HotelNotFoundException;
import com.example.hoteltulumresort.infrastructure.secutity.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
@Import(SecurityConfig.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateHotelUseCase createHotelUseCase;

    @MockitoBean
    private ListHotelsUseCase listHotelsUseCase;

    @MockitoBean
    private FindHotelsByCityUseCase findHotelsByCityUseCase;

    @MockitoBean
    private UpdateHotelAddressUseCase updateHotelAddressUseCase;

    @MockitoBean
    private DeleteHotelUseCase deleteHotelUseCase;

    @Test
    @DisplayName("POST /hotels - Should create hotel")
    @WithMockUser
    void createHotel_shouldReturn201() throws Exception {
        Hotel created = new Hotel(
                UUID.randomUUID(),
                "Hotel Tulum",
                5,
                new Address("Calle 1", "Tulum", "México", "77760")
        );

        when(createHotelUseCase.execute(any(), anyInt(), any())).thenReturn(created);

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Hotel Tulum",
                                    "stars": 5,
                                    "address": {
                                        "street": "Calle 1",
                                        "city": "Tulum",
                                        "country": "México",
                                        "postalCode": "77760"
                                    }
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Hotel Tulum"));
    }

    @Test
    @DisplayName("POST /hotels - Should return 400 when name is blank")
    @WithMockUser
    void createHotel_shouldReturn400_whenNameBlank() throws Exception {
        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "",
                                    "stars": 5,
                                    "address": {
                                        "street": "Calle 1",
                                        "city": "Tulum",
                                        "country": "México",
                                        "postalCode": "77760"
                                    }
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /hotels - Should return 401 when not authenticated")
    void createHotel_shouldReturn401() throws Exception {
        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /hotels - Should return list")
    @WithMockUser
    void listHotels_shouldReturnList() throws Exception {
        when(listHotelsUseCase.execute()).thenReturn(List.of(
                new Hotel(UUID.randomUUID(), "Hotel A", 4, new Address("St", "City", "Country", "12345"))
        ));

        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("GET /hotels/search - Should return hotels by city")
    @WithMockUser
    void findByCity_shouldReturnHotels() throws Exception {
        when(findHotelsByCityUseCase.execute("Tulum")).thenReturn(List.of(
                new Hotel(UUID.randomUUID(), "Hotel Tulum", 4, new Address("St", "Tulum", "México", "77760"))
        ));

        mockMvc.perform(get("/hotels/search").param("city", "Tulum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address.city").value("Tulum"));
    }

    @Test
    @DisplayName("PATCH /hotels/{id}/address - Should update address")
    @WithMockUser
    void updateAddress_shouldReturn200() throws Exception {
        UUID id = UUID.randomUUID();
        Hotel updated = new Hotel(id, "Hotel", 4, new Address("New St", "New City", "Country", "12345"));

        when(updateHotelAddressUseCase.execute(eq(id), any())).thenReturn(updated);

        mockMvc.perform(patch("/hotels/{id}/address", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "street": "New St",
                                    "city": "New City",
                                    "country": "Country",
                                    "postalCode": "12345"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address.street").value("New St"));
    }

    @Test
    @DisplayName("PATCH /hotels/{id}/address - Should return 404")
    @WithMockUser
    void updateAddress_shouldReturn404() throws Exception {
        UUID id = UUID.randomUUID();
        when(updateHotelAddressUseCase.execute(eq(id), any())).thenThrow(new HotelNotFoundException(id));

        mockMvc.perform(patch("/hotels/{id}/address", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "street": "St",
                                    "city": "City",
                                    "country": "Country",
                                    "postalCode": "12345"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /hotels/{id} - Should delete when admin")
    @WithMockUser(roles = "ADMIN")
    void deleteHotel_shouldReturn204() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/hotels/{id}", id))
                .andExpect(status().isNoContent());

        verify(deleteHotelUseCase).execute(id);
    }
    @Test
    @DisplayName("DELETE /hotels/{id} - Should return 403 when not admin")
    @WithMockUser(username = "user", roles = "USER")
    void deleteHotel_shouldReturn403() throws Exception {
        mockMvc.perform(delete("/hotels/{id}", UUID.randomUUID()))
                .andExpect(status().isForbidden());
    }
}