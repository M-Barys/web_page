package com.webshop;

import com.google.common.collect.Lists;
import com.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

//    public Product getProduct(Long id){
 //       return productRepository.findOne(id);
 //   }

    public Product addProduct(Product product){
        productRepository.save(product);
        return product;
    }

 //   public void deleteProduct(Long id) {
 //       productRepository.delete(id);
//   }

    public Product saveOrUpdateProductForm(Product product) {
        Product savedProduct = addProduct(product);
        return savedProduct;
    }

    public  List<Product> filterName(String name){
        return getAllProducts().stream()
                .filter(p -> name.equals(p.getName()) )
                .collect(Collectors.toList());
    }
    public List<Product> filterButton(){
        return getAllProducts().stream()
                .filter(distinctByKey(Product::getName))
                .collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,String> seen = new ConcurrentHashMap<>();
        return t -> seen.put(keyExtractor.apply(t), "") == null;
    }
}
