package com.webshop.model.view;

import com.webshop.model.entity.Category;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTreeNode {
    private Category value;
    private List<CategoryTreeNode> childrens;
}
