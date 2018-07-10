package com.webshop.model.instance;

import com.webshop.model.PictureFileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureRef {

    private Long pictureID;

    private String pictureName;
    private PictureFileType pictureType;

}
