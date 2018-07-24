package com.webshop.controllers;

import com.webshop.controllers.params.RelationParams;
import com.webshop.model.view.CategoryTreeNode;
import com.webshop.services.CategoryRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories")

public class CategoryRelationshipsController extends AbstractControllerExceptionHandler {

    @Autowired
    private CategoryRelationshipService categoryRelationshipService;

    //TODO relations can be more generic to create other trees. For now only categories use then.

    @RequestMapping(value = "/{mainCategoryId}/relationships/categories",method = RequestMethod.POST)
    public @ResponseBody void createRelation(@PathVariable Long mainCategoryId, @RequestBody RelationParams relation){
        categoryRelationshipService.createRelation(mainCategoryId, relation);
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public CategoryTreeNode getTree(){
        return categoryRelationshipService.getTree();
    }

}


