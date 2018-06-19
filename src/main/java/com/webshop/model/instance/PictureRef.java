package com.webshop.model.instance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.activation.MimeType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureRef {

    private Long pictureID;
    //Can be null if data was not loaded
    private byte[] pictureData;
}
