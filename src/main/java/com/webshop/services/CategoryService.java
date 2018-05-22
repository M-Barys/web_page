package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
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

    public List<CategoryEntity> getAllCategories() {
        return Lists.newArrayList(categoryRepository.findAll());
    }

    public CategoryEntity getCategory(Long id){
        Optional<CategoryEntity> found = categoryRepository.findById(id);
        return found.get();
    }

    public CategoryEntity addCategory(CategoryEntity categoryEntity){
        Preconditions.checkArgument(categoryEntity.getId() == null, "A new product can not have a ID setup");
        return categoryRepository.save(categoryEntity);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
   }

    public CategoryEntity updateCategory(CategoryEntity categoryEntity) {
        Preconditions.checkArgument(categoryEntity.getId() != null, "Only update products already inserted with an valid ID");
        return categoryRepository.save(categoryEntity);
    }

}
