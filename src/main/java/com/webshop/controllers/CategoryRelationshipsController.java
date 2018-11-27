package com.webshop.controllers;

import com.webshop.controllers.params.RelationParams;
import com.webshop.model.view.CategoryTreeNode;
import com.webshop.services.CategoryRelationshipService;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/categories")

public class CategoryRelationshipsController extends AbstractControllerExceptionHandler {

    @Autowired
    private CategoryRelationshipService categoryRelationshipService;

    @CacheEvict(value="categoriesTree", allEntries=true)
    @RequestMapping(value = "/{mainCategoryId}/relationships/categories",method = RequestMethod.POST)
    public @ResponseBody void createRelation(@PathVariable Long mainCategoryId, @RequestBody RelationParams relation){
        categoryRelationshipService.createRelation(mainCategoryId, relation);
    }

    @Cacheable("categoriesTree")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public CategoryTreeNode getTree(){
        return categoryRelationshipService.getTree();
    }

}


