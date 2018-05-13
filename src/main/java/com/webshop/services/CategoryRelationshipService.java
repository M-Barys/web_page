package com.webshop.services;

import com.google.common.base.Preconditions;
import com.webshop.controllers.params.RelationParams;
import com.webshop.model.Category;
import com.webshop.model.CategoryRelationship;
import com.webshop.model.ModelObjectType;
import com.webshop.model.tree.CategoryTreeNode;
import com.webshop.repositories.CategoryRelationshipRepository;
import com.webshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        Iterable<CategoryRelationship> relations = categoryRelationshipRepository.findAll();
        Category localRoot = Category.categoryRoot;
        CategoryTreeNode categoryTreeNode = new CategoryTreeNode();
        return createTreeFrom(relations, localRoot, categoryTreeNode);
    }

    private CategoryTreeNode createTreeFrom(Iterable<CategoryRelationship> relations,
                                            Category localRoot, CategoryTreeNode categoryTreeNode) {
        categoryTreeNode.setValue(localRoot);

        List<CategoryTreeNode> children = new ArrayList<CategoryTreeNode>();
        for (CategoryRelationship o : relations) {
            if (o.getParentId() == localRoot.getId()) {
                CategoryTreeNode child = CategoryTreeNode.builder()
                        .value(categoryService.getCategory(o.getCategoryId()))
                        .build();
                children.add(child);

            }
        }

        if(children.size()==0){
            categoryTreeNode.setChildrens(null);
                  }else {

            categoryTreeNode.setChildrens(children);
        }

        for(CategoryTreeNode c : children){
            Category newRoot = c.getValue();
            createTreeFrom(relations, newRoot, c);
        }
        System.out.print(categoryTreeNode);
        return categoryTreeNode;

    }
}
