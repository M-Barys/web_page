package com.webshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Lob
    private String productData;

    //Internal Map<StoreLanguage, ProductInfo> blob store
    @Lob
    private String productInfoBlob;

    @ManyToMany()
    private List<PictureEntity> pictureEntities;

}
