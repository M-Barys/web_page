package com.webshop;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return Lists.newArrayList(productRepository.findAll());
    }

    public Product getProduct(Long id){
        Optional<Product> found = productRepository.findById(id);
        return found.get();
    }

    public Product addProduct(Product product){
        Preconditions.checkArgument(product.getId() == null, "A new product can not have a ID setup");
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
   }

    public Product updateProduct(Product product) {
        Preconditions.checkArgument(product.getId() != null, "Only update products already inserted with an valid ID");
        return productRepository.save(product);
    }

}
