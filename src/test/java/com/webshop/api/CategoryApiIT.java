package com.webshop.api;

import com.webshop.WebShopApplication;
import com.webshop.api.data.CategoryData;
import com.webshop.model.entity.Category;
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

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryApiIT {

    @Value("${local.server.port}")
    private int serverPort;
    private final CategoryData categoryData = new CategoryData();

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
        config = config()
                .logConfig(LogConfig
                        .logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails()
                );
    }


    private String categoriesEndpoint = "/categories";
    private String categoryByIDEndpoint = "/categories/{id}";

    @Test
    public void testCrud() {
        //given
        Category newCategory = categoryData.createRandomCategory();
        //Create
        Category created = given()
                .body(newCategory)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(categoriesEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
        Assertions.assertThat(newCategory).isEqualToIgnoringGivenFields(created, "id");
        Long createdId = created.getId();
        //Read
        Category loaded = loadByID(createdId);
        Assertions.assertThat(loaded).isEqualTo(created);
        //Update
        Category toUpdate = categoryData.createRandomCategoryWithID(loaded.getId());
        Category updated = given()
                .body(toUpdate)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put(categoryByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
        Assertions.assertThat(updated)
                .isEqualTo(toUpdate)
                .isEqualTo(loadByID(createdId));
        //Delete
        when()
                .delete(categoryByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        when()
                .get(categoryByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private Category loadByID(Long id) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(categoryByIDEndpoint, id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
    }


}
