package com.webshop.repositories;

import com.webshop.model.StoreLanguage;
import com.webshop.model.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Iterable<Category> findByLanguage(StoreLanguage language);
}
