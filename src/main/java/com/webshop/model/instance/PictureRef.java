package com.webshop.model.instance;

import com.webshop.model.PictureFileType;
import lombok.*;

import javax.activation.MimeType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureRef {

    private Long pictureID;

    private String pictureName;
    private PictureFileType pictureType;

}
