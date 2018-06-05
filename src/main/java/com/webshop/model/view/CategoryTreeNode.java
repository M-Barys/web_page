package com.webshop.model.view;

import com.webshop.model.instance.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTreeNode {
    private Category value;
    private List<CategoryTreeNode> childrens;
}
