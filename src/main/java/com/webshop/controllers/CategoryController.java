package com.webshop.controllers;

import com.google.common.base.Preconditions;
import com.webshop.controllers.params.ModelObjectReference;
import com.webshop.controllers.params.ModelObjectType;
import com.webshop.controllers.params.RelationParams;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.Product;
import com.webshop.services.CategoryRelationshipService;
import com.webshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


//TODO Add cache. minor priority.

@RestController
@RequestMapping("/api/categories")

public class CategoryController extends AbstractControllerExceptionHandler {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRelationshipService categoryRelationshipService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @CacheEvict(value="categoriesTree", allEntries=true)
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Category addCategory(@RequestBody Category category) {
        Category updated = categoryService.addCategory(category);
        categoryRelationshipService.createRelation(updated.getId(), RelationParams.builder()
                .parent(ModelObjectReference.builder().objectID(0L).type(ModelObjectType.CATEGORY).build())
                .build());
        return updated;
    }


    @CacheEvict(value="categoriesTree", allEntries=true)
    @RequestMapping(value = "/{id}/setPicture", method = RequestMethod.PUT)
    Category setupCategoryPicture(@NotNull @PathVariable Long id, @RequestParam("pictureID") Long pictureID) {
        return categoryService.setupCategoryPicture(id, pictureID);
    }

    @RequestMapping(value = "/{id}/deletePicture", method = RequestMethod.PUT)
    Category deletePictureToCategory(@NotNull @PathVariable Long id) {
        return categoryService.deleteCategoryPicture(id);
    }

    @CacheEvict(value="categoriesTree", allEntries=true)
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

    @CacheEvict(value="categoriesTree", allEntries=true)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}


