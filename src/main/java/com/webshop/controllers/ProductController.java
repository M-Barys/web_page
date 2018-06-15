package com.webshop.controllers;

import com.webshop.model.entity.ProductEntity;
import com.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ProductEntity> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ProductEntity addProduct(@RequestBody ProductEntity productEntity) {
        return productService.addProduct(productEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ProductEntity updateProduct(@RequestBody ProductEntity updatedProductEntity, @PathVariable Long id) {
        ProductEntity productEntity = productService.getProduct(id);
        productEntity.setName(updatedProductEntity.getName());
        productEntity.setDescription(updatedProductEntity.getDescription());
        return productService.updateProduct(productEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductEntity getProduct(@PathVariable Long id) {
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


