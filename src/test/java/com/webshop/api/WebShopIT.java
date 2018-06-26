package com.webshop.api;

import com.webshop.api.data.ProductDataTest;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.config;

public class WebShopIT {
    @Value("${local.server.port}")
    private int serverPort;
    private final ProductDataTest productDataTest = new ProductDataTest();

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
        config = config()
                .logConfig(LogConfig
                        .logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails()
                );
    }

    private String productEndpoint = "/products";
    private String productByIDEndpoint = "/products/{id}";

    //TODO Create tests from use cases. Priority
}
