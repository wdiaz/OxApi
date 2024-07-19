package com.commerce.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOneEqualsOne() {
        assertEquals(1, 1, "1 should be equal to 1");
    }

    @Test
    public void testAddItemToCart() throws Exception {
        String requestPayload = "{"
                + "\"uuid\": \"36e06121-79a9-4ac1-a86d-9fe661bac028\","
                + "\"productId\": \"76\","
                + "\"quantity\": \"4\""
                + "}";

        String expectedResponse = "{"
                + "\"status\": \"created\","
                + "\"uuid\": \"36e06121-79a9-4ac1-a86d-9fe661bac028\""
                + "}";

        mockMvc.perform(post("/api/carts/add-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
                .andDo(print());
    }
}