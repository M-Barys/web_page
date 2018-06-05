package com.webshop.model.instance;

import com.webshop.model.Status;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.data.PerLanguageData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {

    private Long id;
    private PerLanguageData<CategoryData> data;
    private String slug;
    private Status status;

}
