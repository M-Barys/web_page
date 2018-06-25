package com.webshop.api;


import com.google.common.io.Resources;
import com.webshop.WebShopApplication;
import com.webshop.api.data.ProductDataTest;
import com.webshop.model.StoreLanguage;
import com.webshop.model.instance.PictureRef;
import com.webshop.model.instance.Product;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URL;

import static com.webshop.api.ApiEndpointSpecification.*;
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

    @Test
    public void managePictureRelation() {
        Product newProduct = productDataTest.createRandomProduct();
        Product created = createNewProduct(newProduct);

        //TODO create picture on database. Add util method to use here and on PictureTests
        //Add picture to database
        URL resource1 = Resources.getResource("baseball.jpg");
        //Load picture from data base
        PictureRef picture1 = createNewPicture(resource1);
        PictureRef loadedPicture1 = loadPicByID(picture1.getPictureID());

        Product updated = given()
                .queryParam("pictureID", loadedPicture1.getPictureID()) //TODO use pictureID from database
                .when()
                .put(productByIDAddPictureEndpoint, created.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(updated.getPictures()).hasSize(1);

        //TODO Add 1 more picture
        //Add second picture to database
        URL resource2 = Resources.getResource("cnc_milling_machine.jpg");
        //Load second picture from data base
        PictureRef picture2 = createNewPicture(resource1);
        PictureRef loadedPicture2 = loadPicByID(picture1.getPictureID());

        Product addedSecondPic = given()
                .queryParam("pictureID", loadedPicture2.getPictureID()) //TODO use pictureID from database
                .when()
                .put(productByIDAddPictureEndpoint, updated.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(addedSecondPic.getPictures()).hasSize(2);

        //TODO Delete one picture
        Product deletedPic = given()
                .queryParam("pictureID", loadedPicture1.getPictureID())
                .when()
                .put(productByIDDeletePictureEndpoint, addedSecondPic.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(deletedPic.getPictures()).hasSize(1);
    }

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

    private PictureRef createNewPicture(URL resource) {
        String filePath = resource.getFile();
        File file = new File(filePath);

        return given()
                .multiPart("file", file, filePath)
                .when()
                .post(pictureEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PictureRef.class);
    }

    private PictureRef loadPicByID(Long pictureID) {
        return when()
                .get(pictureByIDEndpoint, pictureID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PictureRef.class);
    }


}
