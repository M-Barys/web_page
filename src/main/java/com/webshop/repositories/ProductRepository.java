package com.webshop.repositories;

import com.webshop.model.StoreLanguage;
import com.webshop.model.entity.Category;
import com.webshop.model.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Iterable<Product> findByLanguage(StoreLanguage language);

}

