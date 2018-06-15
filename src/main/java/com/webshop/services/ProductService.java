package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import com.webshop.model.entity.ProductEntity;
import com.webshop.model.instance.Product;
import com.webshop.model.mapping.ProductMapping;
import com.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductMapping mapping;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return Streams.stream(productRepository.findAll())
                .map(e -> mapping.fromEntity(e))
                .collect(Collectors.toList());
    }

    public Product getProduct(Long id) {
        Optional<ProductEntity> found = productRepository.findById(id);
        return mapping.fromEntity(found.get());
    }

    public Product addProduct(Product product) {
        Preconditions.checkArgument(product.getId() == null, "A new product can not have a ID setup");

        ProductEntity entity = mapping.createEntity(product);
        ProductEntity stored = productRepository.save(entity);
        return mapping.fromEntity(stored);

    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Product product) {
        Preconditions.checkArgument(product.getId() != null, "Only update products already inserted with an valid ID");
        ProductEntity oldValue = productRepository.findById(product.getId()).get();
        ProductEntity updated = mapping.updateEntity(oldValue, product);
        ProductEntity stored = productRepository.save(updated);
        return mapping.fromEntity(stored);
    }

}
