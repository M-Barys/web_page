package com.webshop;

import com.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProduct(Product product){
        productService.addProduct(product);
    }

    @RequestMapping(value="/productList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }


}


