package com.webshop.builders;

import com.webshop.model.Product;

public class ProductBuilder {

    private Product product = new Product();

    public ProductBuilder id(Long id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder name(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder description(String description) {
        product.setDescription(description);
        return this;
    }


    public Product build() {
        return product;
    }
}
