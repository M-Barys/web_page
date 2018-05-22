package com.webshop.repositories;

import com.webshop.model.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
