package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.google.gson.Gson;
import com.webshop.model.StoreLanguage;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.data.PerLanguageData;
import com.webshop.model.mapping.CategoryMapping;
import com.webshop.model.view.CategoryView;
import com.webshop.repositories.CategoryRepository;
import com.webshop.model.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapping mapping;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return Streams.stream(categoryRepository.findAll())
                .map(e -> mapping.fromEntity(e))
                .collect(Collectors.toList());
    }

    public Category getCategory(Long id) {
        Optional<CategoryEntity> found = categoryRepository.findById(id);
        return mapping.fromEntity(found.get());
    }

    public Category addCategory(Category category) {
        Preconditions.checkArgument(category.getId() == null, "A new product can not have a ID setup");

        CategoryEntity entity = mapping.createEntity(category);
        CategoryEntity stored = categoryRepository.save(entity);
        return mapping.fromEntity(stored);

    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Category category) {
        Preconditions.checkArgument(category.getId() != null, "Only update products already inserted with an valid ID");
        CategoryEntity oldValue = categoryRepository.findById(category.getId()).get();
        CategoryEntity updated = mapping.updateEntity(oldValue, category);
        CategoryEntity stored = categoryRepository.save(updated);
        return mapping.fromEntity(stored);
    }

}
