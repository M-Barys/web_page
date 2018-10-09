package com.webshop.api;

import com.webshop.controllers.params.RelationParams;
import com.webshop.model.StoreLanguage;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.PictureRef;
import com.webshop.model.instance.Product;
import com.webshop.model.view.CategoryTreeNode;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static com.webshop.api.ApiEndpointSpecification.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public abstract class AbstractApiTest {


    protected PictureRef createNewPicture(URL resource) throws IOException {
        File file = new File(resource.getFile());
        String mimetype = Files.probeContentType(file.toPath());

        return given()
                .log().all()
                .multiPart("file", file, mimetype)
                .when()
                .post(pictureEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PictureRef.class);
    }

    protected PictureRef loadPictureByID(Long pictureID) {
        return when()
                .get(pictureByIDEndpoint, pictureID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PictureRef.class);
    }


    protected Product createNewProduct(Product newProduct) {
        return given()
                .log().all()
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(productEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
    }

    protected Product loadProductByID(Long id) {
        return loadProductByID(id, StoreLanguage.PL);
    }

    protected Product loadProductByID(Long id, StoreLanguage language) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(StoreLanguage.languageHeader, language.name())
                .accept(ContentType.JSON)
                .when()
                .get(productByIDEndpoint, id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);
    }

    protected Category createNewCategory(Category newCategory) {
        return given()
                .log().all()
                .body(newCategory)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(categoriesEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
    }

    protected Category loadCategoryByID(Long id, StoreLanguage language) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(StoreLanguage.languageHeader, language.name())
                .accept(ContentType.JSON)
                .when()
                .get(categoryByIDEndpoint, id)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);
    }

    protected void createNewCategoryRelationship(RelationParams relation, Category children){
        given()
                .log().all()
                .body(relation)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(ApiEndpointSpecification.categoryByIDRelationEndpoint, children.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    protected void loopingCategories(RelationParams relation, Category children){
        given()
                .log().all()
                .body(relation)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(ApiEndpointSpecification.categoryByIDRelationEndpoint, children.getId())
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    protected CategoryTreeNode getCategoryTree(){
    return given()
            .log().all()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .when()
            .get(ApiEndpointSpecification.categoriesTreeEndpoint)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().body().as(CategoryTreeNode.class);
    }
}
