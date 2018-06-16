package com.webshop.repositories;

import com.webshop.model.entity.Picture;
import org.springframework.data.repository.CrudRepository;

public interface PictureRepository extends CrudRepository<Picture, Long> {
}
