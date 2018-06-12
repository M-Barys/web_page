package com.webshop.model.entity;

import javax.persistence.*;
import java.sql.Blob;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long pictureID;
    private Blob imageData;
}
