package com.webshop.model.mapping;

import com.google.common.collect.ImmutableList;
import com.webshop.model.entity.PictureEntity;
import com.webshop.model.instance.PictureRef;
import com.webshop.model.instance.PictureUrlInfo;
import org.springframework.stereotype.Component;

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

    public PictureUrlInfo urlInfoFromEntity(PictureEntity pictureEntity) {
        if (pictureEntity == null) {
            return null;
        }
        return PictureUrlInfo.builder()
                .alternative("TODO")
                .pictureUrlPath("/api/pictures/" + pictureEntity.getPictureID() + "/image.jpg")
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


}
