package com.webshop.controller;

import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.http.ContentType;
import com.webshop.WebShopApplication;
import com.webshop.builders.ProductBuilder;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.webshop.ProductRepository;
import com.webshop.model.Product;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIT {
    private static final String NAME_FIELD = "name";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String ITEMS_RESOURCE = "/products";
    private static final String ITEM_RESOURCE = "/products/{id}";
    private static final int NON_EXISTING_ID = 999;
    private static final String FIRST_ITEM_NAME = "First item name";
    private static final String FIRST_ITEM_DESCRIPTION = "First item";
    private static final String SECOND_ITEM_NAME = "Second item name";
    private static final String SECOND_ITEM_DESCRIPTION = "Second item";
    private static final String THIRD_ITEM_NAME = "Third item name";
    private static final String THIRD_ITEM_DESCRIPTION = "Third item";
    private static final Product FIRST_ITEM = new ProductBuilder()
            .name(FIRST_ITEM_NAME)
            .description(FIRST_ITEM_DESCRIPTION)
            .build();
    private static final Product SECOND_ITEM = new ProductBuilder()
            .name(SECOND_ITEM_NAME)
            .description(SECOND_ITEM_DESCRIPTION)
            .build();
    private static final Product THIRD_ITEM = new ProductBuilder()
            .name(THIRD_ITEM_NAME)
            .description(THIRD_ITEM_DESCRIPTION)
            .build();
    @Autowired
    private ProductRepository productrepository;
    @Value("${local.server.port}")
    private int serverPort;
    private Product firstItem;
    private Product secondItem;

    @Before
    public void setUp() {
        productrepository.deleteAll();
        firstItem = productrepository.save(FIRST_ITEM);
        secondItem = productrepository.save(SECOND_ITEM);
        RestAssured.port = serverPort;
        RestAssured.config = config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @Test
    public void getProductShouldReturnBothProducts() {
        when()
                .get(ITEMS_RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(DESCRIPTION_FIELD, hasItems(FIRST_ITEM_DESCRIPTION, SECOND_ITEM_DESCRIPTION))
                .body(NAME_FIELD, hasItems(FIRST_ITEM_NAME, SECOND_ITEM_NAME));
    }

    @Test
    public void addProductShouldReturnSavedProduct() {
        given()
                .body(THIRD_ITEM)
                .contentType(ContentType.JSON)
        .when()
                .post(ITEMS_RESOURCE)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body(DESCRIPTION_FIELD, is(THIRD_ITEM_DESCRIPTION))
                .body(NAME_FIELD, is(THIRD_ITEM_NAME));
    }

    @Test
    public void addProductShouldReturnBadRequestWithoutBody() {
        given()
                .contentType(ContentType.JSON).
           when()
                .post(ITEMS_RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void addProductShouldReturnNotSupportedMediaTypeIfNonJSON() {
        given()
                .body(THIRD_ITEM)
                .when()
                .post(ITEMS_RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void updateProductShouldReturnUpdatedProduct() {
        // Given an unchecked first item
        Product product = new ProductBuilder()
                .name(FIRST_ITEM_NAME)
                .description(FIRST_ITEM_DESCRIPTION)
                .build();
        given()
                .body(product)
                .contentType(ContentType.JSON)
                .when()
                .put(ITEM_RESOURCE, firstItem.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(NAME_FIELD, is(FIRST_ITEM_NAME))
                .body(DESCRIPTION_FIELD, is(FIRST_ITEM_DESCRIPTION));
    }

    @Test
    public void updateProductShouldReturnBadRequestWithoutBody() {
        when()
                .put(ITEM_RESOURCE, firstItem.getId())
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void updateProductShouldReturnNotSupportedMediaTypeIfNonJSON() {
        given()
                .body(FIRST_ITEM)
                .when()
                .put(ITEM_RESOURCE, firstItem.getId())
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void updateProductShouldBeBadRequestIfNonExistingID() {
        given()
                .body(FIRST_ITEM)
                .contentType(ContentType.JSON)
                .when()
                .put(ITEM_RESOURCE, NON_EXISTING_ID)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void deleteProductShouldReturnNoContent() {
        when()
                .delete(ITEM_RESOURCE, secondItem.getId())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

    }

    @Test
    public void deleteProductShouldBeBadRequestIfNonExistingID() {
        when()
                .delete(ITEM_RESOURCE, NON_EXISTING_ID)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
