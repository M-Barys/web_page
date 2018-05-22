package com.webshop.api;


import com.webshop.WebShopApplication;
import com.webshop.api.data.ProductData;
import com.webshop.model.entity.Product;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductApiIT {

//    @Autowired
//    private ProductRepository productrepository;
    @Value("${local.server.port}")
    private int serverPort;
    private final ProductData productData = new ProductData();

    @Before
    public void setUp() {
//        productrepository.deleteAll();
        RestAssured.port = serverPort;
        config = config()
                .logConfig(LogConfig
                        .logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails()
                );
    }

    //CRUD operations
    private String productsEndpoint = "/products";
    private String productByIDEndpoint = "/products/{id}";

    @Test
    public void testCrud() {
        //given
        Product newProduct = productData.randomNewProduct();
        //Create
        Product created = given()
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(productsEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
        Assertions.assertThat(newProduct).isEqualToIgnoringGivenFields(created, "id");
        Long createdId = created.getId();
        //Read
        Product loaded = loadByID(createdId);
        Assertions.assertThat(loaded).isEqualTo(created);
        //Update
        Product toUpdate = productData.randomProductWithID(loaded.getId());
        Product updated = given()
                .body(toUpdate)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put(productByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
        Assertions.assertThat(updated)
                .isEqualTo(toUpdate)
                .isEqualTo(loadByID(createdId));
        //Delete
        when()
                .delete(productByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        when()
                .get(productByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private Product loadByID(Long id) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(productByIDEndpoint, id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
    }


}
