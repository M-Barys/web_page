package com.webshop.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
public class CategoryRelationship {
    private Long parentId;
    private Long categoryId;
    private List<Long> children;
}
