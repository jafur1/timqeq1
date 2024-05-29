package ru.shop.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shop.model.ProductReturn;
import ru.shop.repository.ProductReturnRepository;


import java.time.LocalDate;
import java.util.*;

import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductReturnControllerTest {

    @MockBean
    ProductReturnRepository productReturnRepository;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void shouldSaveProductReturn() {
        // given
        Mockito.when(productReturnRepository.findAll()).thenReturn(List.of(new ProductReturn()));

        // then
        when()
                .get(getRootUrl() + "/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ProductReturn[].class);
    }

    @Test
    public void shouldSaveProductReturnGete() {
        UUID customerId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        var productReturn = new ProductReturn(customerId, orderId, LocalDate.now(), 10);
        // given
        Mockito.when(productReturnRepository.findById(customerId)).thenReturn(Optional.of(productReturn));

        // then
        when()
                .get(getRootUrl() + "/" + customerId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ProductReturn[].class);
    }
}
