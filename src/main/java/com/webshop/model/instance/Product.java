package com.webshop.model.instance;

import com.webshop.model.entity.PictureEntity;
import com.webshop.model.instance.data.ProductData;
import com.webshop.model.instance.info.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private ProductData data;
    private ProductInfo info;
    private List<PictureUrlInfo> pictures;

    public static final Product CATEGORY_ROOT = Product.builder()
            .id(0L)
            .data(null)
            .info(null)
            .pictures(null)
            .build();
}
