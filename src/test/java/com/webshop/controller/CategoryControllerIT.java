package com.webshop.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.http.ContentType;
import com.webshop.repositories.CategoryRepository;
import com.webshop.WebShopApplication;
import com.webshop.model.Category;
import com.webshop.model.Status;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerIT {
    private static final String NAME_FIELD = "name";
    private static final String SLUG_FIELD = "slug";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String STATUS_FIELD = "status";
    private static final String ITEMS_RESOURCE = "/categories";
    private static final String ITEM_RESOURCE = "/categories/{id}";
    private static final int NON_EXISTING_ID = 999;
    private static final String FIRST_ITEM_NAME = "First item name";
    private static final String FIRST_ITEM_SLUG = "First item slug";
    private static final String FIRST_ITEM_DESCRIPTION = "First item";
    private static final Status FIRST_ITEM_STATUS = Status.live;
    private static final String SECOND_ITEM_NAME = "Second item name";
    private static final String SECOND_ITEM_SLUG = "Second item slug";
    private static final String SECOND_ITEM_DESCRIPTION = "Second item";
    private static final Status SECOND_ITEM_STATUS = Status.draft;
    private static final String THIRD_ITEM_NAME = "Third item name";
    private static final String THIRD_ITEM_SLUG = "Third item slug";
    private static final String THIRD_ITEM_DESCRIPTION = "Third item";
    private static final Status THIRD_ITEM_STATUS = Status.live;
    private static final Category FIRST_ITEM = Category.builder()
            .name(FIRST_ITEM_NAME)
            .slug(FIRST_ITEM_SLUG)
            .description(FIRST_ITEM_DESCRIPTION)
            .status(FIRST_ITEM_STATUS)
            .build();
    private static final Category SECOND_ITEM = Category.builder()
            .name(SECOND_ITEM_NAME)
            .slug(SECOND_ITEM_SLUG)
            .description(SECOND_ITEM_DESCRIPTION)
            .status(SECOND_ITEM_STATUS)
            .build();
    private static final Category THIRD_ITEM = Category.builder()
            .name(THIRD_ITEM_NAME)
            .slug(THIRD_ITEM_SLUG)
            .description(THIRD_ITEM_DESCRIPTION)
            .status(THIRD_ITEM_STATUS)
            .build();
    @Autowired
    private CategoryRepository categoryrepository;
    @Value("${local.server.port}")
    private int serverPort;
    private Category firstItem;
    private Category secondItem;

    @Before
    public void setUp() {
        categoryrepository.deleteAll();
        firstItem = categoryrepository.save(FIRST_ITEM);
        secondItem = categoryrepository.save(SECOND_ITEM);
        RestAssured.port = serverPort;
        RestAssured.config = config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @Test
    public void getCategoryShouldReturnBothCategories() {
        when()
                .get(ITEMS_RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(NAME_FIELD, hasItems(FIRST_ITEM_NAME, SECOND_ITEM_NAME))
                .body(SLUG_FIELD, hasItems(FIRST_ITEM_SLUG, SECOND_ITEM_SLUG))
                .body(DESCRIPTION_FIELD, hasItems(FIRST_ITEM_DESCRIPTION, SECOND_ITEM_DESCRIPTION))
                .body(STATUS_FIELD, hasItems(FIRST_ITEM_STATUS.toString(), SECOND_ITEM_STATUS.toString()));
    }

    @Test
    public void getOneCategoryShouldReturnCategory() {
        when()
                .get(ITEM_RESOURCE, firstItem.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(NAME_FIELD, is(FIRST_ITEM_NAME))
                .body(SLUG_FIELD, is(FIRST_ITEM_SLUG))
                .body(DESCRIPTION_FIELD, is(FIRST_ITEM_DESCRIPTION))
                .body(STATUS_FIELD, is(FIRST_ITEM_STATUS.toString()));
    }

    @Test
    public void addCategoryShouldReturnSavedCategory() {
        given()
                .body(THIRD_ITEM)
                .contentType(ContentType.JSON)
        .when()
                .post(ITEMS_RESOURCE)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body(NAME_FIELD, is(THIRD_ITEM_NAME))
                .body(SLUG_FIELD, is(THIRD_ITEM_SLUG))
                .body(DESCRIPTION_FIELD, is(THIRD_ITEM_DESCRIPTION))
                .body(STATUS_FIELD, equalTo(THIRD_ITEM_STATUS.toString()));
    }

    @Test
    public void addCategoryShouldReturnBadRequestWithoutBody() {
        given()
                .contentType(ContentType.JSON).
           when()
                .post(ITEMS_RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void addCategoryShouldReturnNotSupportedMediaTypeIfNonJSON() {
        given()
                .body(THIRD_ITEM)
                .when()
                .post(ITEMS_RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void updateCategoryShouldReturnUpdatedCategory() {
        // Given an unchecked first item
        Category category = Category.builder()
                .name(FIRST_ITEM_NAME)
                .slug(FIRST_ITEM_SLUG)
                .description(FIRST_ITEM_DESCRIPTION)
                .status(FIRST_ITEM_STATUS)
                .build();
        given()
                .body(category)
                .contentType(ContentType.JSON)
                .when()
                .put(ITEM_RESOURCE, firstItem.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(NAME_FIELD, is(FIRST_ITEM_NAME))
                .body(SLUG_FIELD, is(FIRST_ITEM_SLUG))
                .body(DESCRIPTION_FIELD, is(FIRST_ITEM_DESCRIPTION))
                .body(STATUS_FIELD, is(FIRST_ITEM_STATUS.toString()));
    }

    @Test
    public void updateCategoryShouldReturnBadRequestWithoutBody() {
        when()
                .put(ITEM_RESOURCE, firstItem.getId())
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void updateCategoryShouldReturnNotSupportedMediaTypeIfNonJSON() {
        given()
                .body(FIRST_ITEM)
                .when()
                .put(ITEM_RESOURCE, firstItem.getId())
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void updateCategoryShouldBeBadRequestIfNonExistingID() {
        given()
                .body(FIRST_ITEM)
                .contentType(ContentType.JSON)
                .when()
                .put(ITEM_RESOURCE, NON_EXISTING_ID)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void deleteCategoryShouldReturnNoContent() {
        when()
                .delete(ITEM_RESOURCE, secondItem.getId())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

    }

    @Test
    public void deleteCategoryShouldBeBadRequestIfNonExistingID() {
        when()
                .delete(ITEM_RESOURCE, NON_EXISTING_ID)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
