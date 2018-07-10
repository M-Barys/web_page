package com.webshop.api.data;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.webshop.model.Status;
import com.webshop.model.instance.Product;
import com.webshop.model.instance.data.ProductData;
import com.webshop.model.instance.info.ProductInfo;

import java.math.BigDecimal;
import java.util.*;

public class ProductDataTest {

    private final Fairy fairy = Fairy.create();

    public Product createRandomProduct() {
        return generate().build();
    }

    public Product createRandomProductWithID(Long id) {
        return generate().id(id).build();
    }

    private Product.ProductBuilder generate() {
        TextProducer text = fairy.textProducer();

        return Product.builder()
                .info(ProductInfo.builder()
                        .description(text.paragraph())
                        .name(text.latinWord())
                        .build())
                .data(ProductData.builder()
                        .slug(text.randomString(fairy.baseProducer().randomBetween(2, 20)))
                        .status(Status.draft)
                        .build())
                .pictures(Lists.newArrayList());

    }


}
