package com.webshop.api.data;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.webshop.model.entity.ProductEntity;

public class ProductData {
    private final Fairy fairy = Fairy.create();

    public ProductEntity randomProductWithID(Long id) {
        TextProducer textProducer = fairy.textProducer();
        return ProductEntity.builder()
                .id(id)
                .name(textProducer.latinWord())
                .description(textProducer.paragraph())
                .build();
    }

    public ProductEntity randomNewProduct() {
        TextProducer textProducer = fairy.textProducer();
        return ProductEntity.builder()
                .name(textProducer.latinWord())
                .description(textProducer.paragraph())
                .build();
    }

}
