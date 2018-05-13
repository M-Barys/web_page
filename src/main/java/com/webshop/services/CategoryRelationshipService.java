package com.webshop.services;

import com.google.common.collect.ImmutableList;
import com.webshop.controllers.params.RelationParams;
import com.webshop.model.ModelObjectReference;
import com.webshop.model.ModelObjectType;
import com.webshop.model.tree.CategoryTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryRelationshipService {

    @Autowired
    public CategoryService categoryService;

    public CategoryTreeNode createRelation(Long id, RelationParams relation){
        return CategoryTreeNode.builder()
                .value(categoryService.getCategory(relation.getParent().getObjectID()))
                .childrens(ImmutableList.of(
                        CategoryTreeNode.builder().value(categoryService.getCategory(id)).build()))
                .build();
    }

//    public CategoryTreeNode getTree(){
//        return CategoryTreeNode.
//    }
}
