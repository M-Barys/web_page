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
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Lob
    private String categoryData;

    //Internal Map<StoreLanguage, CategoryInfo> blob store
    @Lob
    private String categoryInfoBlob;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ProductEntity> productEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    private PictureEntity pictureEntity;

}
