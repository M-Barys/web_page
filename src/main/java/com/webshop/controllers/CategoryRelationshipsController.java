package com.webshop.controllers;

import com.webshop.model.CategoryRelationship;
import com.webshop.services.CategoryRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/categories")
public class CategoryRelationshipsController {

    @Autowired
    private CategoryRelationshipService categoryRelationshipService;

    @RequestMapping(value = "/{categoryId}/relationships/categories",method = RequestMethod.POST)
    public CategoryRelationship createRelationship(CategoryRelationship categoryRelationship){
        return categoryRelationshipService.crateCategoryRelationship(categoryRelationship);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {EmptyResultDataAccessException.class, EntityNotFoundException.class, NoSuchElementException.class})
    public void handleNotFound() {
    }

}


