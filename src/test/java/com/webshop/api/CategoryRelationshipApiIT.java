package com.webshop.api;


import com.google.common.collect.ImmutableList;
import com.webshop.WebShopApplication;
import com.webshop.api.data.CategoryDataTest;
import com.webshop.controllers.params.ModelObjectReference;
import com.webshop.controllers.params.ModelObjectType;
import com.webshop.controllers.params.RelationParams;
import com.webshop.model.instance.Category;
import com.webshop.model.view.CategoryTreeNode;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.webshop.model.instance.Category.CATEGORY_ROOT;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class CategoryRelationshipApiIT extends AbstractApiTest {

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
    public void createCategoryTree() {
        //given
        Category categoryOne = categoryDataTest.createRandomCategory();
        Category categoryTwo = categoryDataTest.createRandomCategory();
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
                        .objectID(CATEGORY_ROOT.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        //When
        given()
                .body(relation)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(ApiEndpointSpecification.categoryByIDRelationEndpoint, c1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
        given()
                .body(relation2)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(ApiEndpointSpecification.categoryByIDRelationEndpoint, c2.getId());
        //Then
        CategoryTreeNode expected = CategoryTreeNode.builder()
                .value(CATEGORY_ROOT)
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
                .get(ApiEndpointSpecification.categoriesTreeEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(CategoryTreeNode.class);

        Assertions.assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        //Check whether category looping is not possible
        RelationParams relation3 = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(c1.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        loopingCategories(relation2, CATEGORY_ROOT );
        loopingCategories(relation3, c2);
    }

    private Category createCategory(Category categoryData) {
        return given()
                .body(categoryData)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(ApiEndpointSpecification.categoriesEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
    }

}
