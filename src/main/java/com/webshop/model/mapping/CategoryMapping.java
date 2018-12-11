package com.webshop.model.mapping;

import com.google.common.reflect.TypeToken;
import com.webshop.model.StoreLanguage;
import com.webshop.model.entity.CategoryEntity;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.info.CategoryInfo;
import com.webshop.services.ConfigurationService;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CategoryMapping {

    private final StoreLanguage language;

    private final ConfigurationService configuration;

    private final ProductMapping productMapping;

    private final PictureMapping pictureMapping;

    public CategoryMapping(StoreLanguage language, ConfigurationService configuration, ProductMapping productMapping, PictureMapping pictureMapping) {
        this.language = language == null ? StoreLanguage.EN : language;
        this.configuration = configuration;
        this.productMapping = productMapping;
        this.pictureMapping = pictureMapping;
    }


    public CategoryEntity createEntity(Category category) {
        return internalCreate(category, new HashMap<>());
    }

    public CategoryEntity updateEntity(CategoryEntity old, Category category) {
        Map<StoreLanguage, CategoryInfo> info = deserializeInfoMap(old);
        return internalCreate(category, info);
    }

    public Category fromEntity(CategoryEntity categoryEntity) {
        Map<StoreLanguage, CategoryInfo> info = deserializeInfoMap(categoryEntity);
        return Category.builder()
                .id(categoryEntity.getId())
                .data(configuration.getGson().fromJson(categoryEntity.getCategoryData(), CategoryData.class))
                .info(info.get(language))
//                .products(productMapping.mapToProductList(categoryEntity.getProductEntities()))
                .picture(pictureMapping.urlInfoFromEntity(categoryEntity.getPictureEntity()))
                .build();
    }

    private CategoryEntity internalCreate(Category category, Map<StoreLanguage, CategoryInfo> info) {
        String jsonCategory = configuration.getGson().toJson(category.getData());
        info.put(language, category.getInfo());
        String infoJson = configuration.getGson().toJson(info);
        return CategoryEntity.builder()
                .id(category.getId())
                .categoryData(jsonCategory)
                .categoryInfoBlob(infoJson)
//                .productEntities(productMapping.mapToProductEntityList(category.getProducts()))
                .build();
    }

    private Map<StoreLanguage, CategoryInfo> deserializeInfoMap(CategoryEntity categoryEntity) {
        Type typeOfHashMap = new TypeToken<Map<StoreLanguage, CategoryInfo>>() {
        }.getType();
        return configuration.getGson().fromJson(categoryEntity.getCategoryInfoBlob(), typeOfHashMap);
    }

}
