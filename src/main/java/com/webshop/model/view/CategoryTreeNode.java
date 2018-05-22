package com.webshop.model.view;

import com.webshop.model.entity.CategoryEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTreeNode {
    private CategoryEntity value;
    private List<CategoryTreeNode> childrens;
}
