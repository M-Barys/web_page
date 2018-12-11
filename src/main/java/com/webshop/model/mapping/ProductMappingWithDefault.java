package com.webshop.model.mapping;

import com.webshop.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ProductMappingWithDefault extends ProductMapping {

    @Autowired
    public ProductMappingWithDefault(ConfigurationService configuration, PictureMapping pictureMapping) {
        super(null, configuration, pictureMapping);
    }
}
