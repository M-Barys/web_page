package com.webshop.api;

import com.webshop.WebShopApplication;
import com.webshop.api.data.CategoryDataTest;
import com.webshop.model.StoreLanguage;
import com.webshop.model.instance.Category;
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

import static com.webshop.api.ApiEndpointSpecification.categoriesEndpoint;
import static com.webshop.api.ApiEndpointSpecification.categoryByIDEndpoint;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryApiIT {

    @Value("${local.server.port}")
    private int serverPort;
    private final CategoryDataTest categoryDataTest = new CategoryDataTest();

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
        config = config()
                .logConfig(LogConfig
                        .logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails()
                );
    }


    @Test
    public void testLanguageData() {
        //given
        Category newCategory = categoryDataTest.createRandomCategory();
        Category created = createNewCategory(newCategory);
        //when
        Category loadedEN = loadByID(created.getId(), StoreLanguage.EN);
        //then the information is null, because it was not setup on EN
        Assertions.assertThat(loadedEN.getId()).isEqualTo(created.getId());
        Assertions.assertThat(loadedEN.getData()).isEqualTo(created.getData());
        Assertions.assertThat(loadedEN.getInfo()).isNull();

        //when
        Category loadedPL = loadByID(created.getId(), StoreLanguage.PL);
        //then the information on PL match the created values
        Assertions.assertThat(loadedPL).isEqualTo(created);

    }

    @Test
    public void testCrud() {
        //given
        Category newCategory = categoryDataTest.createRandomCategory();
        //Create
        Category created = createNewCategory(newCategory);
        Assertions.assertThat(newCategory).isEqualToIgnoringGivenFields(created, "id");
        Long createdId = created.getId();
        //Read
        Category loaded = loadByID(createdId);
        Assertions.assertThat(loaded).isEqualTo(created);
        //Update
        Category updateInput = categoryDataTest.createRandomCategoryWithID(loaded.getId());
        Category updated = given()
                .body(updateInput)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put(categoryByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
        Assertions.assertThat(updated)
                .isEqualTo(updateInput)
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

    private Category createNewCategory(Category newCategory) {
        return given()
                .body(newCategory)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(categoriesEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
    }

    private Category loadByID(Long id) {
        return loadByID(id, StoreLanguage.PL);
    }

    private Category loadByID(Long id, StoreLanguage language) {
        return given()
                .contentType(ContentType.JSON)
                .header(StoreLanguage.languageHeader, language.name())
                .accept(ContentType.JSON)
                .when()
                .get(categoryByIDEndpoint, id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
    }


}
