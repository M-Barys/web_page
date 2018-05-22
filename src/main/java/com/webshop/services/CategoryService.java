package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.webshop.repositories.CategoryRepository;
import com.webshop.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return Lists.newArrayList(categoryRepository.findAll());
    }

    public Category getCategory(Long id){
        Optional<Category> found = categoryRepository.findById(id);
        return found.get();
    }

    public Category addCategory(Category category){
        Preconditions.checkArgument(category.getId() == null, "A new product can not have a ID setup");
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
   }

    public Category updateCategory(Category category) {
        Preconditions.checkArgument(category.getId() != null, "Only update products already inserted with an valid ID");
        return categoryRepository.save(category);
    }

}
