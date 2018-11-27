package com.webshop.model.instance;

import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.info.CategoryInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Long id;
    private CategoryData data;
    private CategoryInfo info;
    private List<Product> products;
    private PictureUrlInfo picture;

    public static final Category CATEGORY_ROOT = Category.builder()
            .id(0L)
            .data(null)
            .info(null)
            .products(null)
            .picture(null)
            .build();
}
