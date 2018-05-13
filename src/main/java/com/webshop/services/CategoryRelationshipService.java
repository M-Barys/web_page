package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.webshop.controllers.params.RelationParams;
import com.webshop.model.Category;
import com.webshop.model.CategoryRelationship;
import com.webshop.model.ModelObjectReference;
import com.webshop.model.ModelObjectType;
import com.webshop.model.tree.CategoryTreeNode;
import com.webshop.repositories.CategoryRelationshipRepository;
import com.webshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryRelationshipService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryRelationshipRepository categoryRelationshipRepository;

    public void createRelation(Long mainCategoryId, RelationParams relation) {
        Preconditions.checkArgument(relation.getParent().getType().compareTo(ModelObjectType.CATEGORY) == 0);
        //TODO check that tree make sense
        CategoryRelationship data = CategoryRelationship.builder()
                .categoryId(mainCategoryId)
                .parentId(relation.getParent().getObjectID())
                .build();
        categoryRelationshipRepository.save(data);

    }

    public CategoryTreeNode getTree() {
        List<Category> allCategories = categoryService.getAllCategories();
        Iterable<CategoryRelationship> relations = categoryRelationshipRepository.findAll();
        return createTreeFrom(allCategories,relations);
    }

    private CategoryTreeNode createTreeFrom(List<Category> allCategories, Iterable<CategoryRelationship> relations) {
        return null;
    }
}
