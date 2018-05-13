package com.webshop.api;


import com.google.common.collect.ImmutableList;
import com.webshop.WebShopApplication;
import com.webshop.api.data.CategoryData;
import com.webshop.controllers.params.RelationParams;
import com.webshop.model.Category;
import com.webshop.model.ModelObjectReference;
import com.webshop.model.ModelObjectType;
import com.webshop.model.tree.CategoryTreeNode;
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

import static com.webshop.model.Category.categoryRoot;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryRelationshipApiIT {

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


    //TODO create single place for API endpoints
    private String categoriesEndpoint = "/categories";
    private String categoriesTreeEndpoint = "/categories/tree";
    private String categoryByIDEndpoint = "/categories/{id}";
    private String categoryByIDRelationEndpoint = "/categories/{mainCategoryId}/relationships/categories";

    @Test
    public void createCategoryTree() {
        //given
        Category categoryOne = categoryData.createRandomCategory();
        Category categoryTwo = categoryData.createRandomCategory();
        Category c1 = createCategory(categoryOne);
        Category c2 = createCategory(categoryTwo);

        RelationParams relation = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(c2.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relation2 = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(categoryRoot.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        //When
        given()
                .body(relation)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(categoryByIDRelationEndpoint, c1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
        given()
                .body(relation2)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(categoryByIDRelationEndpoint, c2.getId());
        //Then
        CategoryTreeNode expected = CategoryTreeNode.builder()
                .value(categoryRoot)
                .childrens(ImmutableList.of(
                        CategoryTreeNode.builder().value(c2).childrens(
                                ImmutableList.of(
                                        CategoryTreeNode.builder().value(c1).build()
                                )
                        ).build()
                ))
                .build();
        CategoryTreeNode result = given()
                .when()
                .get(categoriesTreeEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(CategoryTreeNode.class);

        Assertions.assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

    }

    private Category createCategory(Category categoryData) {
        return given()
                .body(categoryData)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(categoriesEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
    }

}
