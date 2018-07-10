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
import com.webshop.model.instance.data.ProductData;
import com.webshop.model.instance.info.CategoryInfo;
import com.webshop.model.view.CategoryTreeNode;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.internal.assertion.Assertion;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.webshop.api.ApiEndpointSpecification.categoryByIDAddProductEndpoint;
import static com.webshop.api.ApiEndpointSpecification.productByIDAddPictureEndpoint;
import static com.webshop.model.instance.Category.CATEGORY_ROOT;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebShopIT extends AbstractApiTest {
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

        //Add picture to product
        //Add picture to database
        URL resource1 = Resources.getResource("baseball.jpg");
        //Load picture from data base
        PictureRef picture1 = createNewPicture(resource1);
        PictureRef loadedPicture1 = loadPictureByID(picture1.getPictureID());

        Product productWithPicture = given()
                .queryParam("pictureID", loadedPicture1.getPictureID())
                .when()
                .put(productByIDAddPictureEndpoint, frezarka.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(productWithPicture.getPictures()).hasSize(1);

        //Create and add categories
        Category frezarki = Category.builder()
                .info(CategoryInfo.builder()
                        .name("Mini Frezarki CNC")
                        .description("Najlepsze frezarki cnc")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category lasery = Category.builder()
                .info(CategoryInfo.builder()
                        .name("Lasery CNC")
                        .description("Najlepsze lasery CNC")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category akcesoria = Category.builder()
                .info(CategoryInfo.builder()
                        .name("akcesoria")
                        .description("Najlepsze akcesoria do maszyn CNC")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category wrzeciona = Category.builder()
                .info(CategoryInfo.builder()
                        .name("Wrzeciona")
                        .description("Najlepsze wrzeciona")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category frezy = Category.builder()
                .info(CategoryInfo.builder()
                        .name("Frezy")
                        .description("Najlepsze frezy")
                        .build())
                .data(CategoryData.builder()
                        .slug("po co jest?")
                        .status(Status.live)
                        .build())
                .build();

        Category addedFrezarki = createNewCategory(frezarki);
        Category addedLasery = createNewCategory(lasery);
        Category addedAkcesoria = createNewCategory(akcesoria);
        Category addedWrzeciona = createNewCategory(wrzeciona);
        Category addedFrezy = createNewCategory(frezy);

        Category loadedFrezarki = loadCategoryByID(addedFrezarki.getId(), StoreLanguage.PL);
        Category loadedLasery = loadCategoryByID(addedLasery.getId(), StoreLanguage.PL);
        Category loadedAkcesoria = loadCategoryByID(addedAkcesoria.getId(), StoreLanguage.PL);
        Category loadedWrzeciona = loadCategoryByID(addedWrzeciona.getId(), StoreLanguage.PL);
        Category loadedFrezy = loadCategoryByID(addedFrezy.getId(), StoreLanguage.PL);

        RelationParams relationRoot = RelationParams.builder()
                .parent(ModelObjectReference.builder()
                        .objectID(CATEGORY_ROOT.getId())
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

        createNewCategoryRelationship(relationRoot, loadedFrezarki);
        createNewCategoryRelationship(relationRoot, loadedLasery);
        createNewCategoryRelationship(relationRoot, loadedAkcesoria);
        createNewCategoryRelationship(relationAkcesoria, loadedWrzeciona);
        createNewCategoryRelationship(relationAkcesoria, loadedFrezy);

        //Then
        CategoryTreeNode expected = CategoryTreeNode.builder()
                .value(CATEGORY_ROOT)
                .childrens(ImmutableList.of(
                        CategoryTreeNode.builder().value(loadedFrezarki).build(),
                        CategoryTreeNode.builder().value(loadedLasery).build(),
                        CategoryTreeNode.builder().value(loadedAkcesoria).childrens(ImmutableList.of(
                                CategoryTreeNode.builder().value(loadedWrzeciona).build(),
                                CategoryTreeNode.builder().value(loadedFrezy).build()))
                                .build()))
                .build();


        CategoryTreeNode categoryTree = getCategoryTree();
        Assertions.assertThat(categoryTree).isEqualToComparingFieldByFieldRecursively(expected);

        //Add product to category
        Category productAddedToCategory = given()
                .queryParam("productID", productWithPicture.getId())
                .when()
                .put(categoryByIDAddProductEndpoint, loadedFrezarki.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);

        Assertions.assertThat(productAddedToCategory.getProducts()).hasSize(1);

    }
}
