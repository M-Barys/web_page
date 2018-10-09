package com.webshop.api;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.webshop.WebShopApplication;
import com.webshop.api.data.CategoryDataTest;
import com.webshop.api.data.ProductDataTest;
import com.webshop.controllers.params.ModelObjectReference;
import com.webshop.controllers.params.ModelObjectType;
import com.webshop.controllers.params.RelationParams;
import com.webshop.model.Status;
import com.webshop.model.StoreLanguage;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.PictureRef;
import com.webshop.model.instance.Product;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.info.CategoryInfo;
import com.webshop.model.view.CategoryTreeNode;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
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
import java.math.BigDecimal;
import java.net.URL;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.webshop.api.ApiEndpointSpecification.*;
import static com.webshop.model.instance.Category.CATEGORY_ROOT;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class XFrame extends AbstractApiTest {
    @Value("${local.server.port}")
    private int serverPort;

    private final ProductDataTest productDataTest = new ProductDataTest();
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
    public void createProduct() throws IOException {

        //Add product
        Product newFrezarka = productDataTest.createRandomProduct();

        //Add price to product
        Locale enUSLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        Currency currencyInstance = Currency.getInstance(enUSLocale);
        Map<Currency,BigDecimal> pricelist = new HashMap<>();
        pricelist.put(currencyInstance, BigDecimal.valueOf(8000));

        newFrezarka.getData().setPrices(pricelist);

        // Add product to database
        Product frezarka = createNewProduct(newFrezarka);
        Assertions.assertThat(frezarka).isEqualToIgnoringGivenFields(newFrezarka,"id");

        //Add picture to product
        //Add picture to database
        URL resource1 = Resources.getResource("baseball.jpg");
        //Load picture from data base
        PictureRef picture1 = createNewPicture(resource1);
        PictureRef loadedPicture1 = loadPictureByID(picture1.getPictureID());

        Product productWithPicture = given()
                .log().all()
                .queryParam("pictureID", loadedPicture1.getPictureID())
                .when()
                .put(productByIDAddPictureEndpoint, frezarka.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(productWithPicture.getPictures()).hasSize(1);

        //Create and add categories
        Category stronaGlowna = Category.builder()
                .info(CategoryInfo.builder()
                        .name("STRONA GŁÓWNA")
                        .description("Frezarki")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category frezarki = Category.builder()
                .info(CategoryInfo.builder()
                        .name("FREZARKI CNC")
                        .description("Frezarki")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category lasery = Category.builder()
                .info(CategoryInfo.builder()
                        .name("LASERY CNC")
                        .description("Lasery CNC")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category akcesoria = Category.builder()
                .info(CategoryInfo.builder()
                        .name("AKCESORIA")
                        .description("Akcesoria")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category wrzeciona = Category.builder()
                .info(CategoryInfo.builder()
                        .name("WRZECIONA")
                        .description("Wrzeciona")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category frezy = Category.builder()
                .info(CategoryInfo.builder()
                        .name("FREZY")
                        .description("Frezy")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category frezyUniwersalne = Category.builder()
                .info(CategoryInfo.builder()
                        .name("FREZY UNIWERSANE")
                        .description("Frezy universalne")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category frezyJubilerskie = Category.builder()
                .info(CategoryInfo.builder()
                        .name("FREZY DO JUBILERSTWA")
                        .description("Frezy do jubilerstwa")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category stoly = Category.builder()
                .info(CategoryInfo.builder()
                        .name("STOŁY PODCIŚNIENIOWE")
                        .description("Stoły podciśnieniowe")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category kontakt = Category.builder()
                .info(CategoryInfo.builder()
                        .name("KONTAKT")
                        .description("Kontakt")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category addedStronaGlowna = createNewCategory(stronaGlowna);
        Category addedFrezarki = createNewCategory(frezarki);
        Category addedLasery = createNewCategory(lasery);
        Category addedAkcesoria = createNewCategory(akcesoria);
        Category addedWrzeciona = createNewCategory(wrzeciona);
        Category addedFrezy = createNewCategory(frezy);
        Category addedFrezyUniwersalne = createNewCategory(frezyUniwersalne);
        Category addedFrezyJubilerskie = createNewCategory(frezyJubilerskie);
        Category addedStoly = createNewCategory(stoly);
        Category addedKontat = createNewCategory(kontakt);

        Category loadedStronaGlowna = loadCategoryByID(addedStronaGlowna.getId(), StoreLanguage.PL);
        Category loadedFrezarki = loadCategoryByID(addedFrezarki.getId(), StoreLanguage.PL);
        Category loadedLasery = loadCategoryByID(addedLasery.getId(), StoreLanguage.PL);
        Category loadedAkcesoria = loadCategoryByID(addedAkcesoria.getId(), StoreLanguage.PL);
        Category loadedWrzeciona = loadCategoryByID(addedWrzeciona.getId(), StoreLanguage.PL);
        Category loadedFrezy = loadCategoryByID(addedFrezy.getId(), StoreLanguage.PL);
        Category loadedFrezyUniwersalne = loadCategoryByID(addedFrezyUniwersalne.getId(), StoreLanguage.PL);
        Category loadedFrezyJubilerskie = loadCategoryByID(addedFrezyJubilerskie.getId(), StoreLanguage.PL);
        Category loadedStoly = loadCategoryByID(addedStoly.getId(), StoreLanguage.PL);
        Category loadedKontat = loadCategoryByID(addedKontat.getId(), StoreLanguage.PL);

        RelationParams relationRoot = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(CATEGORY_ROOT.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationStronaGlowna = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedStronaGlowna.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationFrezarki = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedFrezarki.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationLasery = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedLasery.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationAkcesoria = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedAkcesoria.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationWrzeciona = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedWrzeciona.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationFrezy = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedFrezy.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationFrezyUniversalne = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedFrezyUniwersalne.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationFrezyJubilerskie = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedFrezyJubilerskie.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationStoly = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedStoly.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        RelationParams relationKontakt = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(addedKontat.getId())
                        .type(ModelObjectType.CATEGORY)
                        .build())
                .build();

        createNewCategoryRelationship(relationRoot, loadedStronaGlowna);
        createNewCategoryRelationship(relationRoot, loadedFrezarki);
        createNewCategoryRelationship(relationRoot, loadedLasery);
        createNewCategoryRelationship(relationRoot, loadedAkcesoria);
        createNewCategoryRelationship(relationAkcesoria, loadedWrzeciona);
        createNewCategoryRelationship(relationAkcesoria, loadedFrezy);
        createNewCategoryRelationship(relationFrezy, loadedFrezyUniwersalne);
        createNewCategoryRelationship(relationFrezy, loadedFrezyJubilerskie);
        createNewCategoryRelationship(relationAkcesoria, loadedStoly);
        createNewCategoryRelationship(relationRoot, loadedKontat);

        //Then
        CategoryTreeNode expected = CategoryTreeNode.builder()
                .value(CATEGORY_ROOT)
                .childrens(ImmutableList.of(
                        CategoryTreeNode.builder().value(loadedStronaGlowna).build(),
                        CategoryTreeNode.builder().value(loadedFrezarki).build(),
                        CategoryTreeNode.builder().value(loadedLasery).build(),
                        CategoryTreeNode.builder().value(loadedAkcesoria).childrens(ImmutableList.of(
                                CategoryTreeNode.builder().value(loadedWrzeciona).build(),
                                CategoryTreeNode.builder().value(loadedFrezy).childrens(ImmutableList.of(
                                        CategoryTreeNode.builder().value(loadedFrezyUniwersalne).build(),
                                        CategoryTreeNode.builder().value(loadedFrezyJubilerskie).build()))
                                        .build(),
                                CategoryTreeNode.builder().value(loadedStoly).build()))
                                .build(),
                        CategoryTreeNode.builder().value(loadedKontat).build()))
                .build();


        CategoryTreeNode categoryTree = getCategoryTree();
        Assertions.assertThat(categoryTree).isEqualToComparingFieldByFieldRecursively(expected);

        //Add product to category
        Category productAddedToCategory = given()
                .log().all()
                .queryParam("productID", productWithPicture.getId())
                .when()
                .put(categoryByIDAddProductEndpoint, loadedFrezarki.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);

        Assertions.assertThat(productAddedToCategory.getProducts()).hasSize(1);

        //Add second product
        Product newFrezarka2 = productDataTest.createRandomProduct();

        // Add second product to database
        Product frezarka2 = createNewProduct(newFrezarka2);

        //Add product to category
        Category product2AddedToCategory = given()
                .log().all()
                .queryParam("productID", frezarka2.getId())
                .when()
                .put(categoryByIDAddProductEndpoint, loadedFrezarki.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);

        Assertions.assertThat(product2AddedToCategory.getProducts()).hasSize(2);

        logJsonRequestForCategoryData(loadedFrezarki.getId());

        //Delete product to category
        Category deleteProductToCategory = given()
                .log().all()
                .queryParam("productID", frezarka2.getId())
                .when()
                .put(categoryByIDAddDeleteEndpoint, loadedFrezarki.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);

        Assertions.assertThat(deleteProductToCategory.getProducts()).hasSize(1);
    }

    private void logJsonRequestForCategoryData(Long categoryId) {
        String json = given()
                .log().all()
                .when()
                .get(categoryByIDEndpoint, categoryId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().asString();
        System.err.println(json);
    }


}
