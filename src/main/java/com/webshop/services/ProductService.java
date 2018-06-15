package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.webshop.model.entity.ProductEntity;
import com.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getAllProducts() {
        return Lists.newArrayList(productRepository.findAll());
    }

    public ProductEntity getProduct(Long id){
        Optional<ProductEntity> found = productRepository.findById(id);
        if(found.isPresent()) {
            return found.get();
        } else {
            throw new EntityNotFoundException();
        }

    }

    public ProductEntity addProduct(ProductEntity productEntity){
        Preconditions.checkArgument(productEntity.getId() == null, "A new productEntity can not have a ID setup");
        return productRepository.save(productEntity);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
   }

    public ProductEntity updateProduct(ProductEntity productEntity) {
        Preconditions.checkArgument(productEntity.getId() != null, "Only update products already inserted with an valid ID");
        return productRepository.save(productEntity);
    }

}
