package com.webshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

//TODO Names on entities must be XXEntity
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long pictureID;

    //TODO research for storage schema for pictures.
    private Blob imageData;
}
