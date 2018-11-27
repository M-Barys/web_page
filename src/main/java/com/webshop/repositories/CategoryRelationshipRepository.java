package com.webshop.repositories;

import com.webshop.model.entity.CategoryRelationship;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRelationshipRepository extends CrudRepository<CategoryRelationship, Long> {

    void deleteByCategoryId(Long cid);
}
