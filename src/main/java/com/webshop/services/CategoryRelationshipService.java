package com.webshop.services;

import com.google.common.base.Preconditions;
import com.webshop.controllers.params.RelationParams;
import com.webshop.model.entity.CategoryEntity;
import com.webshop.model.entity.CategoryRelationship;
import com.webshop.controllers.params.ModelObjectType;
import com.webshop.model.view.CategoryTreeNode;
import com.webshop.repositories.CategoryRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryRelationshipService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRelationshipRepository categoryRelationshipRepository;

    public void createRelation(Long mainCategoryId, RelationParams relation) {
        Preconditions.checkArgument(relation.getParent().getType().compareTo(ModelObjectType.CATEGORY) == 0);
        CategoryRelationship data = CategoryRelationship.builder()
                .categoryId(mainCategoryId)
                .parentId(relation.getParent().getObjectID())
                .build();
        categoryRelationshipRepository.save(data);

    }

    public CategoryTreeNode getTree() {

        Iterable<CategoryRelationship> relations = categoryRelationshipRepository.findAll();
        CategoryEntity localRoot = CategoryEntity.CATEGORY_ENTITY_ROOT;
        CategoryTreeNode categoryTreeNode = new CategoryTreeNode();
        return createTreeFrom(relations, localRoot, categoryTreeNode);
    }

    private CategoryTreeNode createTreeFrom(Iterable<CategoryRelationship> relations,
                                            CategoryEntity localRoot, CategoryTreeNode categoryTreeNode) {
        categoryTreeNode.setValue(localRoot);

        List<CategoryTreeNode> children = new ArrayList<>();
        for (CategoryRelationship o : relations) {
            if (o.getParentId() == localRoot.getId()) {
                CategoryTreeNode child = CategoryTreeNode.builder()
                        .value(categoryService.getCategory(o.getCategoryId()))
                        .build();
                children.add(child);

            }
        }

        if (children.size() == 0) {
            categoryTreeNode.setChildrens(null);
        } else {

            categoryTreeNode.setChildrens(children);
        }

        for (CategoryTreeNode c : children) {
            CategoryEntity newRoot = c.getValue();
            createTreeFrom(relations, newRoot, c);
        }
        System.out.print(categoryTreeNode);
        return categoryTreeNode;

    }
}