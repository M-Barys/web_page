package com.webshop.model.entity;

import com.webshop.model.PictureFileType;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pictureID")
    private Long pictureID;


    //TODO add file name
    private String pictureName;
    //TODO add file type enumeration: png, jpg, gif
    private PictureFileType pictureType;


    private Blob imageData;
}
