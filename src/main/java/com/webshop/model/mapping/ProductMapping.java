package com.webshop.model.mapping;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.webshop.model.StoreLanguage;
import com.webshop.model.entity.ProductEntity;
import com.webshop.model.instance.Product;
import com.webshop.model.instance.data.ProductData;
import com.webshop.model.instance.info.ProductInfo;
import com.webshop.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequestScope
public class ProductMapping {

    private final StoreLanguage language;

    private final ConfigurationService configuration;

    private final PictureMapping pictureMapping;

    @Autowired
    public ProductMapping(StoreLanguage language, ConfigurationService configuration, PictureMapping pictureMapping) {
        this.configuration = configuration;
        this.language = language == null ? StoreLanguage.EN : language;
        this.pictureMapping = pictureMapping;
    }

    public ProductEntity createEntity(Product product) {
        return internalCreate(product, new HashMap<>());
    }

    public ProductEntity updateEntity(ProductEntity old, Product product) {
        Map<StoreLanguage, ProductInfo> info = deserializeInfoMap(old);
        return internalCreate(product, info);
    }

    public Product fromEntity(ProductEntity productEntity) {
        Map<StoreLanguage, ProductInfo> info = deserializeInfoMap(productEntity);
        return Product.builder()
                .id(productEntity.getId())
                .data(configuration.getGson().fromJson(productEntity.getProductData(), ProductData.class))
                .info(info.get(language))
                .pictures(
                        pictureMapping.mapToUrlInfo(productEntity.getPictureEntities())
                ).build();
    }

    private ProductEntity internalCreate(Product product, Map<StoreLanguage, ProductInfo> info) {
        String jsonCategory = configuration.getGson().toJson(product.getData());
        info.put(language, product.getInfo());
        String infoJson = configuration.getGson().toJson(info);
        return ProductEntity.builder()
                .id(product.getId())
                .productData(jsonCategory)
                .productInfoBlob(infoJson)
                .build();
    }

    private Map<StoreLanguage, ProductInfo> deserializeInfoMap(ProductEntity productEntity) {
        Type typeOfHashMap = new TypeToken<Map<StoreLanguage, ProductInfo>>() {
        }.getType();
        return configuration.getGson().fromJson(productEntity.getProductInfoBlob(), typeOfHashMap);
    }

    public List<Product> mapToProductList(List<ProductEntity> productEntities) {
        if (productEntities == null) {
            return ImmutableList.of();
        }
        return productEntities.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductEntity> mapToProductEntityList(List<Product> products) {
        if (products == null) {
            return ImmutableList.of();
        }
        return products.stream()
                .map(this::createEntity)
                .collect(Collectors.toList());
    }

}
