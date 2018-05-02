package com.webshop.api.data;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.webshop.model.Product;

public class ProductData {
    private final Fairy fairy = Fairy.create();

    public Product randomProductWithID(Long id) {
        TextProducer textProducer = fairy.textProducer();
        return Product.builder()
                .id(id)
                .name(textProducer.latinWord())
                .description(textProducer.paragraph())
                .build();
    }

    public Product randomNewProduct() {
        TextProducer textProducer = fairy.textProducer();
        return Product.builder()
                .name(textProducer.latinWord())
                .description(textProducer.paragraph())
                .build();
    }

}
