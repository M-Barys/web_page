package com.webshop.model.entity;

import javax.persistence.*;
import java.sql.Blob;

//TODO Names on entities must be XXEntity
@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long pictureID;

    //TODO research for storage schema for pictures.
    private Blob imageData;
}
