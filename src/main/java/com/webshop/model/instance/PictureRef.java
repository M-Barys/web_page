package com.webshop.model.instance;

import com.webshop.model.PictureFileType;
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

    //TODO add file name
    private String pictureName;
    //TODO add file type enumeration: png, jpg, gif
    private PictureFileType pictureType;
}
