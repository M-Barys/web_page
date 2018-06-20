package com.webshop.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.webshop.model.entity.PictureEntity;
import com.webshop.model.instance.PictureRef;
import com.webshop.model.mapping.PictureMapping;
import com.webshop.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private PictureMapping pictureMapping;

    public List<PictureRef> getAllPicture() {
        return pictureMapping.mapToRef(
                Lists.newArrayList(pictureRepository.findAll())
        );
    }

    public PictureRef getPicture(Long id) {
        return pictureMapping.loadFromEntity(
                pictureRepository.findById(id).get()
        );
    }

    //TODO migrate to streams
    public byte[] getPictureContent(Long id) {
        PictureEntity pictureEntity = pictureRepository.findById(id).get();
        Blob imageData = pictureEntity.getImageData();
        try {
            return imageData.getBytes(1, (int) imageData.length());
        } catch (SQLException e) {
            //TODO handle
            e.printStackTrace();
            throw new IllegalStateException("TODO");
        }
    }

    public PictureRef addPicture(byte[] imageData) {
        try {
            PictureEntity pictureEntity = PictureEntity.builder()
                    .imageData(new SerialBlob(imageData))
                    .build();
            return pictureMapping.loadFromEntity(
                    pictureRepository.save(pictureEntity)
            );
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO
            throw new IllegalStateException("TODO");
        }
    }

    public void deletePicture(Long id) {
        pictureRepository.deleteById(id);
    }

}
