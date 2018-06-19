package com.webshop.model.mapping;

import com.webshop.model.entity.PictureEntity;
import com.webshop.model.instance.PictureRef;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PictureMapping {

    public PictureRef loadFromEntity(PictureEntity pictureEntity) {
        return PictureRef.builder()
                .pictureID(pictureEntity.getPictureID())
                .build();
    }

    public PictureEntity createEntity(PictureRef pictureRef) {
        try {
            return PictureEntity.builder()
                    .pictureID(pictureRef.getPictureID())
                    .imageData(
                            new SerialBlob(pictureRef.getPictureData())
                    )
                    .build();
        } catch (SQLException e) {
            //TODO error handle
            e.printStackTrace();
            throw new IllegalStateException("TODO");
        }
    }

    public List<PictureRef> mapToRef(List<PictureEntity> pictureEntities) {
        return pictureEntities.stream().map(this::loadFromEntity).collect(Collectors.toList());
    }

    public List<PictureEntity> mapToEntity(List<PictureRef> pictures) {
        return pictures.stream().map(this::createEntity).collect(Collectors.toList());
    }
}
