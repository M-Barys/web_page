package com.webshop.api;


import com.webshop.WebShopApplication;
import com.webshop.api.data.PictureDataTest;
import com.webshop.model.entity.PictureEntity;
import com.webshop.model.instance.PictureRef;
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

import static com.webshop.api.ApiEndpointSpecification.*;
import static io.restassured.RestAssured.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PictureEntityApiIT {

    @Value("${local.server.port}")
    private int serverPort;
    private final PictureDataTest pictureDataTest = new PictureDataTest();

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
    public void testCrud() {
        //given
        PictureRef newPictureEntity = null; // pictureDataTest.createRandomPicture();
        //Create
        PictureEntity created = createNewPicture(newPictureEntity);
        Assertions.assertThat(newPictureEntity).isEqualToIgnoringGivenFields(created, "pictureID");
        Long createdId = created.getPictureID();
        //Read
        PictureEntity loaded = loadByID(createdId);
        Assertions.assertThat(loaded).isEqualTo(created);
        //Delete
        when()
                .delete(pictureByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        when()
                .get(pictureByIDEndpoint, createdId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private PictureEntity createNewPicture(PictureRef newPictureEntity) {
        return given()
                .body(newPictureEntity)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(pictureEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PictureEntity.class);
    }

    private PictureEntity loadByID(Long pictureID) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(pictureByIDEndpoint, pictureID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PictureEntity.class);
    }


}
