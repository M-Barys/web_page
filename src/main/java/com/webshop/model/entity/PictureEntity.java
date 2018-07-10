package com.webshop.model.entity;

import com.webshop.model.PictureFileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String pictureName;
    private PictureFileType pictureType;
    private Blob imageData;
}
