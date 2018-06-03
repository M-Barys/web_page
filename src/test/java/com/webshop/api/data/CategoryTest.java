package com.webshop.api.data;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.webshop.model.Status;
import com.webshop.model.instance.Category;


public class CategoryTest {

    private final Fairy fairy = Fairy.create();

    public Category createRandomCategory() {
        return generate().build();
    }

    private Category.CategoryBuilder generate() {
        TextProducer text = fairy.textProducer();
        return Category.builder()
                .slug(text.randomString(fairy.baseProducer().randomBetween(2,20)))
                .status(Status.draft);
    }

}
