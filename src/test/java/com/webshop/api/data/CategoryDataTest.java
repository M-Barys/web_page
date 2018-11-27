package com.webshop.api.data;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.webshop.model.Status;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.info.CategoryInfo;

public class CategoryDataTest {

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
                .products(ImmutableList.of())
                .info(CategoryInfo.builder()
                        .description(text.paragraph())
                        .name(text.latinWord())
                        .build())
                .data(CategoryData.builder()
                        .slug(text.randomString(fairy.baseProducer().randomBetween(2, 20)))
                        .status(Status.draft)
                        .build())
                .picture(null);
    }

}
