package com.webshop.model.tree;

import com.webshop.model.Category;
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
