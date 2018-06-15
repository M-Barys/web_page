package com.webshop.api;


import com.webshop.WebShopApplication;
import com.webshop.api.data.CategoryDataTest;
import com.webshop.api.data.ProductDataTest;
import com.webshop.model.StoreLanguage;
import com.webshop.model.entity.ProductEntity;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.Product;
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


    @Test
    public void testLanguageData() {
        //given
        Product newProduct = productDataTest.createRandomProduct();
        Product created = createNewProduct(newProduct);
        //when
        Product loadedEN = loadByID(created.getId(), StoreLanguage.EN);
        //then the information is null, because it was not setup on EN
        Assertions.assertThat(loadedEN.getId()).isEqualTo(created.getId());
        Assertions.assertThat(loadedEN.getData()).isEqualTo(created.getData());
        Assertions.assertThat(loadedEN.getInfo()).isNull();

        //when
        Product loadedPL = loadByID(created.getId(), StoreLanguage.PL);
        //then the information on PL match the created values
        Assertions.assertThat(loadedPL).isEqualTo(created);

    }

    @Test
    public void testCrud() {
        //given
        Product newProduct = productDataTest.createRandomProduct();
        //Create
        Product created = createNewProduct(newProduct);
        Assertions.assertThat(newProduct).isEqualToIgnoringGivenFields(created, "id");
        Long createdId = created.getId();
        //Read
        Product loaded = loadByID(createdId);
        Assertions.assertThat(loaded).isEqualTo(created);
        //Update
        Product updateInput = productDataTest.createRandomProductWithID(loaded.getId());
        Product updated = given()
                .body(updateInput)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put(productByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
        Assertions.assertThat(updated)
                .isEqualTo(updateInput)
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

    private Product createNewProduct(Product newProduct) {
        return given()
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(productEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
    }

    private Product loadByID(Long id) {
        return loadByID(id, StoreLanguage.PL);
    }

    private Product loadByID(Long id, StoreLanguage language) {
        return given()
                .contentType(ContentType.JSON)
                .header(StoreLanguage.languageHeader, language.name())
                .accept(ContentType.JSON)
                .when()
                .get(productByIDEndpoint, id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
    }


}
