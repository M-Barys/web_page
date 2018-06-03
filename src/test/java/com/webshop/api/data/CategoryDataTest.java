package com.webshop.api.data;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.webshop.model.instance.data.CategoryData;


public class CategoryDataTest {

    private final Fairy fairy = Fairy.create();

    public CategoryData createRandomCategoryData() {
        return generate().build();
    }

    private CategoryData.CategoryDataBuilder generate() {
        TextProducer text = fairy.textProducer();
        return CategoryData.builder()
                .description(text.paragraph())
                .name(text.latinWord());
    }

}
