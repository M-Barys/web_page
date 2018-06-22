package com.webshop.model.mapping;

import com.google.common.collect.ImmutableList;
import com.webshop.model.entity.PictureEntity;
import com.webshop.model.instance.PictureRef;
import com.webshop.model.instance.PictureUrlInfo;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PictureMapping {

    public PictureRef loadFromEntity(PictureEntity pictureEntity) {
        return PictureRef.builder()
                .pictureID(pictureEntity.getPictureID())
                .pictureName(pictureEntity.getPictureName())
                .pictureType(pictureEntity.getPictureType())
                .build();
    }

//    public PictureEntity createEntity(PictureRef pictureRef) {
//        try {
//            return PictureEntity.builder()
//                    .pictureID(pictureRef.getPictureID())
//                    .imageData(
//                            new SerialBlob(pictureRef.getPictureData())
//                    )
//                    .build();
//        } catch (SQLException e) {
//            //TODO error handle
//            e.printStackTrace();
//            throw new IllegalStateException("TODO");
//        }
//    }

    public PictureUrlInfo urlInfoFromEntity(PictureEntity pictureEntity) {
        return PictureUrlInfo.builder()
                .alternative("TODO")
                .pictureUrlPath("/pictures/image" + pictureEntity.getPictureID() + ".jpg")
                .build();
    }

    public List<PictureRef> mapToRef(List<PictureEntity> pictureEntities) {
        return pictureEntities.stream()
                .map(this::loadFromEntity)
                .collect(Collectors.toList());
    }

    public List<PictureUrlInfo> mapToUrlInfo(List<PictureEntity> pictureEntities) {
        if (pictureEntities == null) {
            return ImmutableList.of();
        }
        return pictureEntities.stream()
                .map(this::urlInfoFromEntity)
                .collect(Collectors.toList());
    }

//    public List<PictureEntity> mapToEntity(List<PictureRef> pictures) {
//        return pictures.stream().map(this::createEntity).collect(Collectors.toList());
//    }
}
