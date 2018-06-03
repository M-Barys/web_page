package com.webshop.api.data;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.google.gson.Gson;
import com.webshop.model.Status;
import com.webshop.model.StoreLanguage;
import com.webshop.model.entity.CategoryEntity;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.data.PerLanguageData;

public class CategoryDataTest {

    private final Fairy fairy = Fairy.create();

    public CategoryEntity createRandomCategory() {
        return generate().build();
    }

    public CategoryEntity createRandomCategoryWithID(Long id) {
        return generate().id(id).build();
    }

    private CategoryEntity.CategoryEntityBuilder generate() {
        TextProducer text = fairy.textProducer();

        CategoryData randomCategoryData = CategoryData.builder()
                .description(text.paragraph())
                .name(text.latinWord())
                .build();

        PerLanguageData<CategoryData> savedToMap = new PerLanguageData<>();
        savedToMap.update(StoreLanguage.EN, randomCategoryData);

        Category randomCategory = Category.builder()
                .data(savedToMap)
                .slug(text.randomString(fairy.baseProducer().randomBetween(2, 20)))
                .status(Status.draft)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(randomCategory);

        return CategoryEntity.builder().categoryData(json);
    }

}
