package com.webshop.services;

import com.google.common.base.Preconditions;
import com.webshop.controllers.params.ModelObjectType;
import com.webshop.controllers.params.RelationParams;
import com.webshop.model.entity.CategoryRelationship;
import com.webshop.model.instance.Category;
import com.webshop.model.view.CategoryTreeNode;
import com.webshop.repositories.CategoryRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryRelationshipService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRelationshipRepository categoryRelationshipRepository;

    @Transactional
    public void createRelation(Long mainCategoryId, RelationParams relation) {
        Preconditions.checkArgument(relation.getParent().getType().compareTo(ModelObjectType.CATEGORY) == 0);
        Preconditions.checkArgument(!mainCategoryId.equals(0L), "Root category cannot be a child");

//        Iterable<CategoryRelationship> relationsList = categoryRelationshipRepository.findAll();
//        for (CategoryRelationship o : relationsList) {
//            Preconditions.checkArgument(!mainCategoryId.equals(o.getCategoryId()), "This category already is a parent.");
//        }
        categoryRelationshipRepository.deleteByCategoryId(mainCategoryId);


        CategoryRelationship data = CategoryRelationship.builder()
                .categoryId(mainCategoryId)
                .parentId(relation.getParent().getObjectID())
                .build();
        categoryRelationshipRepository.save(data);

    }

    public CategoryTreeNode getTree() {
        Iterable<CategoryRelationship> relations = categoryRelationshipRepository.findAll();
        Category localRoot = Category.CATEGORY_ROOT;
        CategoryTreeNode categoryTreeNode = new CategoryTreeNode();
        return createTreeFrom(relations, localRoot, categoryTreeNode);
    }

    private CategoryTreeNode createTreeFrom(Iterable<CategoryRelationship> relations,
                                            Category localRoot, CategoryTreeNode categoryTreeNode) {
        categoryTreeNode.setValue(localRoot);

        List<CategoryTreeNode> children = new ArrayList<>();
        for (CategoryRelationship o : relations) {
            if (o.getParentId().equals(localRoot.getId())) {
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
            Category newRoot = c.getValue();
            createTreeFrom(relations, newRoot, c);
        }
        System.out.print(categoryTreeNode);
        return categoryTreeNode;

    }
}
