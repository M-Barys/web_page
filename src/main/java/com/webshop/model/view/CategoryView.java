package com.webshop.model.view;

import com.webshop.model.Status;
import com.webshop.model.StoreLanguage;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.data.CategoryData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryView {

    private Long id;
    private CategoryData data;
    private String slug;
    private Status status;

    public static CategoryView fromCategory(Category category, StoreLanguage language) {
            return CategoryView.builder()
                    .id(category.getId())
                    .slug(category.getSlug())
                    .data(category.getData().getPerLanguage().get(language))
                    .build();
    }
}
