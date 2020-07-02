package com.webshop.model.mapping;


import com.webshop.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;

@Component
public class CategoryMappingWithDefault extends CategoryMapping {

    @Autowired
    public CategoryMappingWithDefault(HttpServletRequest request, ConfigurationService configuration, ProductMappingWithDefault productMapping, PictureMapping pictureMapping) {
        super(null, configuration, productMapping, pictureMapping);
    }
}
