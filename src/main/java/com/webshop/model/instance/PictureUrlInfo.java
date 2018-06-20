package com.webshop.model.instance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureUrlInfo {
    //  "/pictures/{id}/image.jpg"
    private String pictureUrlPath;
    private String alternative;
}
