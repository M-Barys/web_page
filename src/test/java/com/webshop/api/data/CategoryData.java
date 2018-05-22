package com.webshop.api.data;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.webshop.model.entity.Category;
import com.webshop.model.Status;

public class CategoryData {

    private final Fairy fairy = Fairy.create();

    public Category createRandomCategory() {
        return generate().build();
    }

    public Category createRandomCategoryWithID(Long id) {
        return generate().id(id).build();
    }

    private Category.CategoryBuilder generate() {
        TextProducer text = fairy.textProducer();
        return Category.builder()
                .description(text.paragraph())
                .name(text.latinWord())
                .slug(text.randomString(fairy.baseProducer().randomBetween(2,20)))
                .status(Status.draft);
    }

}
