package com.webshop.controllers;

import com.webshop.controllers.params.RelationParams;
import com.webshop.model.view.CategoryTreeNode;
import com.webshop.services.CategoryRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/categories")
public class CategoryRelationshipsController {

    @Autowired
    private CategoryRelationshipService categoryRelationshipService;

    @RequestMapping(value = "/{mainCategoryId}/relationships/categories",method = RequestMethod.POST)
    public @ResponseBody void createRelation(@PathVariable Long mainCategoryId, @RequestBody RelationParams relation){
        categoryRelationshipService.createRelation(mainCategoryId, relation);
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public CategoryTreeNode getTree(){
        return categoryRelationshipService.getTree();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {EmptyResultDataAccessException.class, EntityNotFoundException.class, NoSuchElementException.class})
    public void handleNotFound() {
    }

}


