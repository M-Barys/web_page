package com.webshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

//TODO Names on entities must be XXEntity
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pictureID")
    private Long pictureID;

    @Column(name="product_id")
    private long productId;

    //TODO research for storage schema for pictures.
    private Blob imageData;
}
