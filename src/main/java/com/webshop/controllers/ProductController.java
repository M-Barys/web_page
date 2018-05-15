package com.webshop.controllers;

import com.webshop.services.ProductService;
import com.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product updatedProduct, @PathVariable Long id) {
        Product product = productService.getProduct(id);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        return productService.updateProduct(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {
            EmptyResultDataAccessException.class,
            EntityNotFoundException.class})
    public void handleNotFound() {
    }
}


