package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.webshop.model.entity.CategoryEntity;
import com.webshop.model.entity.Picture;
import com.webshop.model.instance.Category;
import com.webshop.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    public List<Picture> getAllPicture() {
        return Lists.newArrayList(pictureRepository.findAll());
    }

    public Picture getPicture(Long id) {
        return pictureRepository.findById(id).get();
    }

    public Picture addPicture(Picture picture) {
        Preconditions.checkArgument(picture.getPictureID() == null, "A new picture can not have a ID setup");
        return pictureRepository.save(picture);
    }

    public void deletePicture(Long id) {
        pictureRepository.deleteById(id);
    }

}
