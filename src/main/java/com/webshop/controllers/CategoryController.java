package com.webshop.controllers;

import com.google.common.base.Preconditions;
import com.webshop.model.instance.Category;
import com.webshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //TODO test or delete
    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Category updateCategory(@RequestBody Category data, @NotNull @PathVariable Long id) {
        Preconditions.checkArgument(id.compareTo(data.getId()) == 0);
        return categoryService.updateCategory(data);
    }

    @RequestMapping(value = "/{id}/addProduct", method = RequestMethod.PUT)
    Category addProductToCategory(@NotNull @PathVariable Long id, @RequestParam("productID") Long productID) {
        return categoryService.addProductToCategory(id, productID);
    }

    @RequestMapping(value = "/{id}/deleteProduct", method = RequestMethod.PUT)
    Category deleteProductToCategory(@NotNull @PathVariable Long id, @RequestParam("productID") Long productID) {
        return categoryService.deleteProduct(id, productID);
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {EmptyResultDataAccessException.class, EntityNotFoundException.class,
            NoSuchElementException.class})
    public void handleNotFound() {
    }

}


