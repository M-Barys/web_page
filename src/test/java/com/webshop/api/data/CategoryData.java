package com.webshop.api.data;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.webshop.model.entity.CategoryEntity;
import com.webshop.model.Status;

public class CategoryData {

    private final Fairy fairy = Fairy.create();

    public CategoryEntity createRandomCategory() {
        return generate().build();
    }

    public CategoryEntity createRandomCategoryWithID(Long id) {
        return generate().id(id).build();
    }

    private CategoryEntity.CategoryBuilder generate() {
        TextProducer text = fairy.textProducer();
        return CategoryEntity.builder();
//                .description(text.paragraph())
//                .name(text.latinWord())
//                .slug(text.randomString(fairy.baseProducer().randomBetween(2,20)))
//                .status(Status.draft);
    }

}
