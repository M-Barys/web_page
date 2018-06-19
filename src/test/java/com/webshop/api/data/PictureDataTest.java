package com.webshop.api.data;

import com.webshop.model.entity.Picture;

public class PictureDataTest {

    public Picture createRandomPicture() {
        return generate().build();
    }

    public Picture createRandomPictureWithID(Long id) {
        return generate().pictureID(id).build();
    }

    private Picture.PictureBuilder generate() {
       return Picture.builder()
                .imageData(null);
    }
}
