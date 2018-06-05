package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.webshop.model.StoreLanguage;
import com.webshop.model.instance.Category;
import com.webshop.model.instance.data.CategoryData;
import com.webshop.model.instance.data.PerLanguageData;
import com.webshop.model.view.CategoryView;
import com.webshop.repositories.CategoryRepository;
import com.webshop.model.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryView> getAllCategories() {
//        return Lists.newArrayList(categoryRepository.findAll());//TODO
        return Lists.newArrayList();
    }

    public CategoryEntity getCategory(Long id) {
        Optional<CategoryEntity> found = categoryRepository.findById(id);
        return found.get();
    }

    public CategoryView addCategory(CategoryView categoryView, StoreLanguage language) {
        Preconditions.checkArgument(categoryView.getId() == null, "A new product can not have a ID setup");

        PerLanguageData<CategoryData> data = new PerLanguageData<>();
        data.update(language, categoryView.getData());
        Category category = Category.builder()
                .slug(categoryView.getSlug())
                .status(categoryView.getStatus())
                .data(data)
                .build();

        String jsonCategory = new Gson().toJson(category);
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryData(jsonCategory).build();
        CategoryEntity updated = categoryRepository.save(categoryEntity);

        category.setId(updated.getId());
        return CategoryView.fromCategory(category, language);

    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryEntity updateCategory(CategoryEntity categoryEntity) {
        Preconditions.checkArgument(categoryEntity.getId() != null, "Only update products already inserted with an valid ID");
        return categoryRepository.save(categoryEntity);
    }

}
