package com.webshop.controllers;

import com.google.gson.Gson;
import com.webshop.model.StoreLanguage;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.Category;
import com.webshop.services.CategoryService;
import com.webshop.model.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody CategoryEntity addCategory(@RequestBody CategoryData categoryData,
                                                    @RequestBody Category category,
                                                    @RequestBody StoreLanguage language) {

        category.getData().update(language, categoryData);

        Gson gson = new Gson();
        String newCategory = gson.toJson(category);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory(newCategory);

        return categoryService.addCategory(categoryEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Category updateCategory(@RequestBody CategoryData updatedCategoryData,
                                   @RequestBody Category updatedCategory,
                                   @PathVariable Long id, HttpServletRequest request) {

        CategoryEntity categoryEntity = categoryService.getCategory(id);
        String json = categoryEntity.getCategory();
        Gson gson = new Gson();
        Category currentState = gson.fromJson(json, Category.class);

        StoreLanguage language = StoreLanguage.PL;
        String header = request.getHeader("X-Language");
        if (header != null && header.compareTo("en") == 0) {
            language = StoreLanguage.EN;
        }

        currentState.getData().update(language, updatedCategoryData);
        currentState.setSlug(updatedCategory.getSlug());
        currentState.setStatus(updatedCategory.getStatus());

        String updated = gson.toJson(currentState);
        categoryEntity.setCategory(updated);
        categoryService.updateCategory(categoryEntity);

        return currentState;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CategoryEntity getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {EmptyResultDataAccessException.class, EntityNotFoundException.class, NoSuchElementException.class})
    public void handleNotFound() {
    }

}


