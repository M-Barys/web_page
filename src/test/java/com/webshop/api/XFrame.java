package com.webshop.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
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
import com.webshop.model.instance.info.ProductInfo;
import com.webshop.model.view.CategoryTreeNode;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
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


@Ignore
public class XFrame extends AbstractApiTest {

    private int serverPort = 8081;

//   private final ProductDataTest productDataTest = new ProductDataTest();
 //   private final CategoryDataTest categoryDataTest = new CategoryDataTest();

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
        //RestAssured.baseURI = "http://webshopdocker.us-east-2.elasticbeanstalk.com";
        config = config()
                .logConfig(LogConfig
                        .logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails()
                );
    }

    @Test
    public void createProduct() throws IOException {

        //noumber of products
        int in=2;

        //Create product
        Product frezarka1 = Product.builder()
                .info(ProductInfo.builder()
                        .name("Mini Frezarka CNC 30x40 500W")
                        .description("Najnowszy model mini frezarki XFrame Z2 posiada pole robocze o wymiarach " +
                                "300mm x 400mm. Urządzenie zostało zaprojektowane i wyprodukowane z myślą o uzyskaniu " +
                                "jak największej dokładności oraz prędkości pracy z jednoczesną eliminacją drgań w " +
                                "trakcie obróbki. Wykorzystano do tego wzmocnioną konstrukcję oraz użyto najlepszej " +
                                "jakości materiały oraz podzespoły. Maszyna przystosowana jest do obróbki materiałów " +
                                "niemetalowych np. ABS, pleksi, drewno, inne tworzywa sztuczne. \n" +
                                "\n" +
                                "Idealna do grawerowania oraz cięcia na powierzchniach płaskich jak i przedmiotach " +
                                "obrotowych, dzięki czwartej osi w opcjonalnym wyposażeniu. Jej dużym atutem jest " +
                                "możliwość pracy w opcji 3D.\n" +
                                "Maszyna sterowana jest z poziomu komputera za pomocą programu Mach 3, obsługującego " +
                                "format G-code."+
                                "Podana kwota jest ceną brutto, dotyczy frezarki XFrame Z2 30x40 z podstawowym wyposażeniem."+
                                "Pole robocze                               300 x 400 mm\n" +
                                "\n" +
                                "Prześwit bramy                           80 mm\n" +
                                "\n" +
                                "Prędkość przejazdów                 do 3,5 m/min\n" +
                                "\n" +
                                "Moc wrzeciona                            500 W (opcja do 2,2 kW)\n" +
                                "\n" +
                                "Obroty wrzeciona                       11000 obr/min\n" +
                                " Dokładność pozycjonowania     0,03 mm\n" +
                                "\n" +
                                "Waga                                             28 kg\n" +
                                "\n" +
                                "Średnica trzpienia frezu              do 7,5 mm\n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "- Frezarka XFrame Z2 30x40\n" +
                                "\n" +
                                "- Sterownik wraz z okablowaniem\n" +
                                "\n" +
                                "- Wrzeciono 500 W chłodzone powietrzem\n" +
                                "\n" +
                                "- Tuleja mocująca frez (3,175 mm)\n" +
                                "\n" +
                                "- Podstawowy zestaw frezów\n" +
                                "\n" +
                                "- Uchwyty mocujące obrabiany przedmiot\n" +
                                "\n" +
                                "- Instrukcja obsługi\n" +
                                "\n" +
                                "Dodatkowo zapewniamy szkolenie z obsługi maszyny oraz programów niezbędnych do pracy.\n" +
                                "\n" +
                                "Pełne wsparcie podczas uruchomienia urządzenia.\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "- Oś obrotowa\n" +
                                "\n" +
                                "- System chłodzenia narzędzia\n" +
                                "\n" +
                                "- Stół podciśnieniowy\n" +
                                "\n" +
                                "- Kontroler DSP\n" +
                                "\n" +
                                "- Oprogramowanie sterujące\n" +
                                "\n" +
                                "- Obudowa frezarki\n" +
                                "\n" +
                                "- Wrzeciono 230 W, 800 W, 1,5 kW lub 2,2 kW\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "Gwarancja maszyny udzielana jest na okres 12 miesięcy.")
                        .build())
                .data(ProductData.builder()
                        .slug("")
                        .status(Status.draft)
                        .build())
                .pictures(Lists.newArrayList())
                .build();


        Product frezarka2 = Product.builder()
                .info(ProductInfo.builder()
                        .name("Mini Frezarka CNC 30x40 1500 W")
                        .description("Najnowszy model mini frezarki XFrame Z2 posiada pole robocze o wymiarach " +
                                "300mm x 400mm. Urządzenie zostało zaprojektowane i wyprodukowane z myślą o uzyskaniu " +
                                "jak największej dokładności oraz prędkości pracy z jednoczesną eliminacją drgań w " +
                                "trakcie obróbki. Wykorzystano do tego wzmocnioną konstrukcję oraz użyto najlepszej " +
                                "jakości materiały oraz podzespoły. Maszyna przystosowana jest do obróbki materiałów " +
                                "niemetalowych np. ABS, pleksi, drewno, inne tworzywa sztuczne. \n" +
                                "\n" +
                                "Idealna do grawerowania oraz cięcia na powierzchniach płaskich jak i przedmiotach " +
                                "obrotowych, dzięki czwartej osi w opcjonalnym wyposażeniu. Jej dużym atutem jest " +
                                "możliwość pracy w opcji 3D.\n" +
                                "Maszyna sterowana jest z poziomu komputera za pomocą programu Mach 3, obsługującego " +
                                "format G-code."+
                                "Podana kwota jest ceną brutto, dotyczy frezarki XFrame Z2 30x40 z podstawowym wyposażeniem."+
                                "Pole robocze                               300 x 400 mm\n" +
                                "\n" +
                                "Prześwit bramy                           80 mm\n" +
                                "\n" +
                                "Prędkość przejazdów                 do 3,5 m/min\n" +
                                "\n" +
                                "Moc wrzeciona                            1500 W (opcja do 2,2 kW)\n" +
                                "\n" +
                                "Obroty wrzeciona                       24000 obr/min\n" +
                                " Dokładność pozycjonowania     0,03 mm\n" +
                                "\n" +
                                "Waga                                             30 kg\n" +
                                "\n" +
                                "Średnica trzpienia frezu              do 7,5 mm\n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "- Frezarka XFrame Z2 30x40\n" +
                                "\n" +
                                "- Sterownik wraz z okablowaniem\n" +
                                "\n" +
                                "- Wrzeciono 1500 W chłodzone powietrzem\n" +
                                "\n" +
                                "- Tuleja mocująca frez (3,175 mm)\n" +
                                "\n" +
                                "- Podstawowy zestaw frezów\n" +
                                "\n" +
                                "- Uchwyty mocujące obrabiany przedmiot\n" +
                                "\n" +
                                "- Instrukcja obsługi\n" +
                                "\n" +
                                "Dodatkowo zapewniamy szkolenie z obsługi maszyny oraz programów niezbędnych do pracy.\n" +
                                "\n" +
                                "Pełne wsparcie podczas uruchomienia urządzenia.\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "- Oś obrotowa\n" +
                                "\n" +
                                "- System chłodzenia narzędzia\n" +
                                "\n" +
                                "- Stół podciśnieniowy\n" +
                                "\n" +
                                "- Kontroler DSP\n" +
                                "\n" +
                                "- Oprogramowanie sterujące\n" +
                                "\n" +
                                "- Obudowa frezarki\n" +
                                "\n" +
                                "- Wrzeciono 230 W, 800 W, 1,5 kW lub 2,2 kW\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "Gwarancja maszyny udzielana jest na okres 12 miesięcy.")
                        .build())
                .data(ProductData.builder()
                        .slug("")
                        .status(Status.draft)
                        .build())
                .pictures(Lists.newArrayList())
                .build();

        Product frezarka3 = Product.builder()
                .info(ProductInfo.builder()
                        .name("Mini Frezarka CNC 20x30 800W")
                        .description("Najnowszy model mini frezarki XFrame Z2 posiada pole robocze o wymiarach " +
                                "200mm x 300mm. Urządzenie zostało zaprojektowane i wyprodukowane z myślą o uzyskaniu " +
                                "jak największej dokładności oraz prędkości pracy z jednoczesną eliminacją drgań w " +
                                "trakcie obróbki. Wykorzystano do tego wzmocnioną konstrukcję oraz użyto najlepszej " +
                                "jakości materiały oraz podzespoły. Maszyna przystosowana jest do obróbki materiałów " +
                                "niemetalowych np. ABS, pleksi, drewno, inne tworzywa sztuczne. \n" +
                                "\n" +
                                "Idealna do grawerowania oraz cięcia na powierzchniach płaskich jak i przedmiotach " +
                                "obrotowych, dzięki czwartej osi w opcjonalnym wyposażeniu. Jej dużym atutem jest " +
                                "możliwość pracy w opcji 3D.\n" +
                                "Maszyna sterowana jest z poziomu komputera za pomocą programu Mach 3, obsługującego " +
                                "format G-code."+
                                "Podana kwota jest ceną brutto, dotyczy frezarki XFrame Z2 20x30 z podstawowym wyposażeniem."+
                                "Pole robocze                               200 x 300 mm\n" +
                                "\n" +
                                "Prześwit bramy                           60 mm\n" +
                                "\n" +
                                "Prędkość przejazdów                 do 3,5 m/min\n" +
                                "\n" +
                                "Moc wrzeciona                            800 W (opcja do 2,2 kW)\n" +
                                "\n" +
                                "Obroty wrzeciona                       24000 obr/min\n" +
                                " Dokładność pozycjonowania     0,03 mm\n" +
                                "\n" +
                                "Waga                                             23 kg\n" +
                                "\n" +
                                "Średnica trzpienia frezu              do 7 mm\n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "- Frezarka XFrame Z2 20x30\n" +
                                "\n" +
                                "- Sterownik wraz z okablowaniem\n" +
                                "\n" +
                                "- Wrzeciono 800 W chłodzone powietrzem\n" +
                                "\n" +
                                "- Tuleja mocująca frez (3,175 mm)\n" +
                                "\n" +
                                "- Podstawowy zestaw frezów\n" +
                                "\n" +
                                "- Uchwyty mocujące obrabiany przedmiot\n" +
                                "\n" +
                                "- Instrukcja obsługi\n" +
                                "\n" +
                                "Dodatkowo zapewniamy szkolenie z obsługi maszyny oraz programów niezbędnych do pracy.\n" +
                                "\n" +
                                "Pełne wsparcie podczas uruchomienia urządzenia.\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "- Oś obrotowa\n" +
                                "\n" +
                                "- System chłodzenia narzędzia\n" +
                                "\n" +
                                "- Stół podciśnieniowy\n" +
                                "\n" +
                                "- Kontroler DSP\n" +
                                "\n" +
                                "- Oprogramowanie sterujące\n" +
                                "\n" +
                                "- Obudowa frezarki\n" +
                                "\n" +
                                "- Wrzeciono 230 W, 800 W, 1,5 kW lub 2,2 kW\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "Gwarancja maszyny udzielana jest na okres 12 miesięcy.")
                        .build())
                .data(ProductData.builder()
                        .slug("")
                        .status(Status.draft)
                        .build())
                .pictures(Lists.newArrayList())
                .build();

        Product frezarka4 = Product.builder()
                .info(ProductInfo.builder()
                        .name("Mini Frezarka CNC 30x40 800W")
                        .description("Najnowszy model mini frezarki XFrame Z2 posiada pole robocze o wymiarach " +
                                "300mm x 400mm. Urządzenie zostało zaprojektowane i wyprodukowane z myślą o uzyskaniu " +
                                "jak największej dokładności oraz prędkości pracy z jednoczesną eliminacją drgań w " +
                                "trakcie obróbki. Wykorzystano do tego wzmocnioną konstrukcję oraz użyto najlepszej " +
                                "jakości materiały oraz podzespoły. Maszyna przystosowana jest do obróbki materiałów " +
                                "niemetalowych np. ABS, pleksi, drewno, inne tworzywa sztuczne. \n" +
                                "\n" +
                                "Maszynę dzięki dodatkowemu uchwytowi można dostosować do grawerowania obrączek" +
                                "(wewnątrz oraz na zewnątrz obrączki). Grawer wykonywany jest diamentowym rysikiem." +
                                "Oprócz tego frezarka świetnie nadaję się do wycinania wzorów w srebrze oraz złocie " +
                                "doskonale zastępując drogie lasery."+
                                "\n"+
                                "Frezarka XFrame jest to wielofunkcyjne urządzenie które z powodzeniem  sprawdzi się w" +
                                "branży jubilerskiej. Bez problemu wykona każdy grawer oraz wytnie dowolny kształt, " +
                                "nawet ten najmniejszy. Zapewniamy również frezy do takich materiałów jak srebro i " +
                                "złoto pozwalających uzyskać wysokie jakości wykonania." +
                                "Podana kwota jest ceną brutto, dotyczy frezarki XFrame Z2 30x40 z podstawowym wyposażeniem."+
                                "Pole robocze                               300 x 400 mm\n" +
                                "\n" +
                                "Prześwit bramy                           60 mm\n" +
                                "\n" +
                                "Prędkość przejazdów                 do 3,5 m/min\n" +
                                "\n" +
                                "Moc wrzeciona                            800 W (opcja do 2,2 kW)\n" +
                                "\n" +
                                "Obroty wrzeciona                       24000 obr/min\n" +
                                " Dokładność pozycjonowania     0,03 mm\n" +
                                "\n" +
                                "Waga                                             28 kg\n" +
                                "\n" +
                                "Średnica trzpienia frezu              do 7 mm\n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "- Frezarka XFrame Z2 30x40\n" +
                                "\n" +
                                "- Sterownik wraz z okablowaniem\n" +
                                "\n" +
                                "- Wrzeciono 800 W chłodzone powietrzem\n" +
                                "\n" +
                                "- Tuleja mocująca frez (3,175 mm)\n" +
                                "\n" +
                                "- Podstawowy zestaw frezów\n" +
                                "\n" +
                                "- Uchwyty mocujące obrabiany przedmiot\n" +
                                "\n" +
                                "- Instrukcja obsługi\n" +
                                "\n" +
                                "Dodatkowo zapewniamy szkolenie z obsługi maszyny oraz programów niezbędnych do pracy.\n" +
                                "\n" +
                                "Pełne wsparcie podczas uruchomienia urządzenia.\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "- Oś obrotowa\n" +
                                "\n" +
                                "- System chłodzenia narzędzia\n" +
                                "\n" +
                                "- Stół podciśnieniowy\n" +
                                "\n" +
                                "- Kontroler DSP\n" +
                                "\n" +
                                "- Oprogramowanie sterujące\n" +
                                "\n" +
                                "- Obudowa frezarki\n" +
                                "\n" +
                                "- Wrzeciono 230 W, 800 W, 1,5 kW lub 2,2 kW\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                " \n" +
                                "Gwarancja maszyny udzielana jest na okres 12 miesięcy.")
                        .build())
                .data(ProductData.builder()
                        .slug("")
                        .status(Status.draft)
                        .build())
                .pictures(Lists.newArrayList())
                .build();

        //Add price to product
        Locale plPLLocale = new Locale.Builder().setLanguage("pl").setRegion("PL").build();
        Currency currencyInstance = Currency.getInstance(plPLLocale);
        Map<Currency,BigDecimal> pricefrezarka1 = new HashMap<>();
        pricefrezarka1.put(currencyInstance, BigDecimal.valueOf(5690.00));

        Map<Currency,BigDecimal> pricefrezarka2 = new HashMap<>();
        pricefrezarka2.put(currencyInstance, BigDecimal.valueOf(8790.00));

        Map<Currency,BigDecimal> pricefrezarka3 = new HashMap<>();
        pricefrezarka3.put(currencyInstance, BigDecimal.valueOf(7690.00));

        Map<Currency,BigDecimal> pricefrezarka4 = new HashMap<>();
        pricefrezarka4.put(currencyInstance, BigDecimal.valueOf(8090.00));

        frezarka1.getData().setPrices(pricefrezarka1);
        frezarka2.getData().setPrices(pricefrezarka2);
        frezarka3.getData().setPrices(pricefrezarka3);
        frezarka4.getData().setPrices(pricefrezarka4);

        // Add product to database
        Product AddFrezarka1 = createNewProduct(frezarka1);
        Product AddFrezarka2 = createNewProduct(frezarka2);
        Product AddFrezarka3 = createNewProduct(frezarka3);
        Product AddFrezarka4 = createNewProduct(frezarka4);
        Assertions.assertThat(frezarka1).isEqualToIgnoringGivenFields(AddFrezarka1,"id");
        Assertions.assertThat(frezarka2).isEqualToIgnoringGivenFields(AddFrezarka2,"id");
        Assertions.assertThat(frezarka3).isEqualToIgnoringGivenFields(AddFrezarka3,"id");
        Assertions.assertThat(frezarka4).isEqualToIgnoringGivenFields(AddFrezarka4,"id");

        //Add picture to product
        //Add picture to database
        URL resourceFrezarka1 = Resources.getResource("frezarka1.jpg");
        URL resourceFrezarka2 = Resources.getResource("frezarka2.jpg");
        URL resourceFrezarka3 = Resources.getResource("frezarka3.jpg");
        URL resourceFrezarka4 = Resources.getResource("frezarka4.jpg");
        //Load picture from data base
        PictureRef pictureFrezarka1 = createNewPicture(resourceFrezarka1);
        PictureRef pictureFrezarka2 = createNewPicture(resourceFrezarka2);
        PictureRef pictureFrezarka3 = createNewPicture(resourceFrezarka3);
        PictureRef pictureFrezarka4 = createNewPicture(resourceFrezarka4);
        PictureRef loadedFrezarka1Picture1 = loadPictureByID(pictureFrezarka1.getPictureID());
        PictureRef loadedFrezarka2Picture1 = loadPictureByID(pictureFrezarka2.getPictureID());
        PictureRef loadedFrezarka3Picture1 = loadPictureByID(pictureFrezarka3.getPictureID());
        PictureRef loadedFrezarka4Picture1 = loadPictureByID(pictureFrezarka4.getPictureID());

        Product frezarka1WithPicture = given()
                .log().all()
                .queryParam("pictureID", loadedFrezarka1Picture1.getPictureID())
                .when()
                .put(productByIDAddPictureEndpoint, AddFrezarka1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(frezarka1WithPicture.getPictures()).hasSize(1);

        Product frezarka2WithPicture = given()
                .log().all()
                .queryParam("pictureID", loadedFrezarka2Picture1.getPictureID())
                .when()
                .put(productByIDAddPictureEndpoint, AddFrezarka2.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(frezarka2WithPicture.getPictures()).hasSize(1);

        Product frezarka3WithPicture = given()
                .log().all()
                .queryParam("pictureID", loadedFrezarka3Picture1.getPictureID())
                .when()
                .put(productByIDAddPictureEndpoint, AddFrezarka3.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(frezarka3WithPicture.getPictures()).hasSize(1);

        Product frezarka4WithPicture = given()
                .log().all()
                .queryParam("pictureID", loadedFrezarka4Picture1.getPictureID())
                .when()
                .put(productByIDAddPictureEndpoint, AddFrezarka4.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Product.class);

        Assertions.assertThat(frezarka4WithPicture.getPictures()).hasSize(1);

        //Create and add categories
        Category stronaGlowna = Category.builder()
                .info(CategoryInfo.builder()
                        .name("STRONA GŁÓWNA")
                        .description("Strona główna")
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

       Category frezarka1AddedToCategory = given()
                .log().all()
                .queryParam("productID", frezarka1WithPicture.getId())
                .when()
                .put(categoryByIDAddProductEndpoint, loadedFrezarki.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);

        Assertions.assertThat(frezarka1AddedToCategory.getProducts()).hasSize(1);


        Category frezarka2AddedToCategory = given()
            .log().all()
            .queryParam("productID", frezarka2WithPicture.getId())
            .when()
            .put(categoryByIDAddProductEndpoint, loadedFrezarki.getId())
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().body().as(Category.class);

        Assertions.assertThat(frezarka2AddedToCategory.getProducts()).hasSize(2);

        Category frezarka3AddedToCategory = given()
                .log().all()
                .queryParam("productID", frezarka3WithPicture.getId())
                .when()
                .put(categoryByIDAddProductEndpoint, loadedFrezarki.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);

        Assertions.assertThat(frezarka3AddedToCategory.getProducts()).hasSize(3);

        Category frezarka4AddedToCategory = given()
                .log().all()
                .queryParam("productID", frezarka4WithPicture.getId())
                .when()
                .put(categoryByIDAddProductEndpoint, loadedFrezarki.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(Category.class);

        Assertions.assertThat(frezarka4AddedToCategory.getProducts()).hasSize(4);



    }
}
