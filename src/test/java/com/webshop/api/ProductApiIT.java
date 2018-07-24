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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.webshop.api.ApiEndpointSpecification.*;
import static io.restassured.RestAssured.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductApiIT extends AbstractApiTest {

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
    public void managePictureRelation() throws IOException {
        Product newProduct = productDataTest.createRandomProduct();
        Product created = createNewProduct(newProduct);

        //Add picture to database
        URL resource1 = Resources.getResource("baseball.jpg");
        //Load picture from data base
        PictureRef picture1 = createNewPicture(resource1);
        PictureRef loadedPicture1 = loadPictureByID(picture1.getPictureID());

        Product updated = given()
                .queryParam("pictureID", loadedPicture1.getPictureID())
                .when()
                .put(productByIDAddPictureEndpoint, created.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(updated.getPictures()).hasSize(1);

        //Add second picture to database
        URL resource2 = Resources.getResource("cnc_milling_machine.jpg");
        //Load second picture from data base
        PictureRef picture2 = createNewPicture(resource2);
        PictureRef loadedPicture2 = loadPictureByID(picture2.getPictureID());

        Product addedSecondPic = given()
                .queryParam("pictureID", loadedPicture2.getPictureID())
                .when()
                .put(productByIDAddPictureEndpoint, updated.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(addedSecondPic.getPictures()).hasSize(2);

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
        Product loadedEN = loadProductByID(created.getId(), StoreLanguage.EN);
        //then the information is null, because it was not setup on EN
        Assertions.assertThat(loadedEN.getId()).isEqualTo(created.getId());
        Assertions.assertThat(loadedEN.getData()).isEqualTo(created.getData());
        Assertions.assertThat(loadedEN.getInfo()).isNull();

        //when
        Product loadedPL = loadProductByID(created.getId(), StoreLanguage.PL);
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
        Product loaded = loadProductByID(createdId);
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
                .isEqualTo(loadProductByID(createdId));
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

    @Test
    public void getAllCategories() {

        //given
        Product newProduct1 = productDataTest.createRandomProduct();
        Product newProduct2 = productDataTest.createRandomProduct();
        //Create
        Product created1 = createNewProduct(newProduct1);
        Product created2 = createNewProduct(newProduct2);

        //GetAllPictures
        List<Product> expectedProductList = new ArrayList<>();
        expectedProductList.add(created1);
        expectedProductList.add(created2);

        List<Product> productsList = Arrays.asList(when()
                .get(productEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product[].class));

        Assertions.assertThat(productsList).isEqualTo(expectedProductList);

    }


}
