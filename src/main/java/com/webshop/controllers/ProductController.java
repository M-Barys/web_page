package com.webshop.controllers;

import com.google.common.base.Preconditions;
import com.webshop.model.instance.Product;
import com.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/products")

public class ProductController extends AbstractControllerExceptionHandler {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getAllProduct() {
        return productService.getAllProducts();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @RequestMapping(value = "/{id}/addPicture", method = RequestMethod.PUT)
    Product addPictureToProduct(@NotNull @PathVariable Long id, @RequestParam("pictureID") Long pictureID) {
        return productService.addPictureToProduct(id, pictureID);
    }

    @RequestMapping(value = "/{id}/deletePicture", method = RequestMethod.PUT)
    Product deletePictureToProduct(@NotNull @PathVariable Long id, @RequestParam("pictureID") Long pictureID) {
        return productService.deletePicture(id, pictureID);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product data, @NotNull @PathVariable Long id) {
        Preconditions.checkArgument(id.compareTo(data.getId()) == 0);
        return productService.updateProduct(data);
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

}