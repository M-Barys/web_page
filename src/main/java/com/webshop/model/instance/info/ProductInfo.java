package com.webshop.model.instance.info;

import com.webshop.model.entity.Picture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    //TODO Delete jpa annotations from model
    @Lob
    private String description;
    private String name;

    //TODO Do not use JPA entities on model.
    @OneToMany
    private List<Picture> pictures;
}
