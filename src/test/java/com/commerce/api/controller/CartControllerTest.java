package com.commerce.api.controller;

import com.commerce.api.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    private final String cartUuid = "36e06121-79a9-4ac1-a86d-9fe661bac028";
    private final String productId = "76";

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @BeforeEach
    public void setup() throws Exception {
        // Add the product to the cart before running delete operation
        String addItemJson = "{ \"uuid\": \"" + cartUuid + "\", \"productId\": \"" + productId + "\", \"quantity\": \"1\" }";
        mockMvc.perform(post("/api/carts/add-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addItemJson))
                .andExpect(status().isOk());
    }

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

    @Test
    public void testDeleteCartItem() throws Exception {
        mockMvc.perform(delete("/api/carts/36e06121-79a9-4ac1-a86d-9fe661bac028/items/76"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * Placeholder code
     *
     * @throws Exception
     */
    @Test
    public void testDeleteItemFromCart() throws Exception {
        assertEquals(1, 1, "test delete item from cart");
    }

    /**
     * Placeholder code
     *
     * @throws Exception
     */
    @Test
    public void testUpdateItemInCart() throws Exception {
        assertEquals(1, 1, "test update item in cart");
    }

    /**
     * Placeholder code
     *
     * @throws Exception
     */
    @Test
    public void testGetCart() throws Exception {
        assertEquals(1, 1, "test get cart");
    }

    /**
     * Placeholder code
     *
     * @throws Exception
     */
    @Test
    public void testCreateCart() throws Exception {
        assertEquals(1, 1, "test create cart");
    }
}
