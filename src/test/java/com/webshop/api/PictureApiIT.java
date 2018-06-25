package com.webshop.api;


import com.google.common.io.Resources;
import com.webshop.WebShopApplication;
import com.webshop.model.instance.PictureRef;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URL;

import static com.webshop.api.ApiEndpointSpecification.*;
import static io.restassured.RestAssured.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PictureApiIT {

    @Value("${local.server.port}")
    private int serverPort;


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
    public void crudTests() {
        //given
        URL resource1 = Resources.getResource("baseball.jpg");
        URL resource2 = Resources.getResource("cnc_milling_machine.jpg");

        //Add first pictures
        PictureRef picture1 = createNewPicture(resource1);
        Assertions.assertThat(picture1.getPictureID()).isNotNull();

        //Add second picture
        PictureRef picture2 = createNewPicture(resource2);
        Assertions.assertThat(picture2.getPictureID()).isNotNull();

        when()
                .delete(pictureByIDEndpoint, picture2.getPictureID())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        when()
                .get(pictureByIDEndpoint, picture2.getPictureID())
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        PictureRef pictureLoadedById = loadByID(picture1.getPictureID());
        Assertions.assertThat(pictureLoadedById.getPictureID()).isEqualTo(picture1.getPictureID());
        Assertions.assertThat(pictureLoadedById.getPictureName()).isEqualTo(picture1.getPictureName());
        Assertions.assertThat(pictureLoadedById.getPictureType()).isEqualTo(picture1.getPictureType());

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

    private PictureRef loadByID(Long pictureID) {
        return when()
                .get(pictureByIDEndpoint, pictureID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PictureRef.class);
    }


}
