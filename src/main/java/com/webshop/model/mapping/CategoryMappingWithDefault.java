package com.webshop.model.mapping;

import com.webshop.model.StoreLanguage;
import com.webshop.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;

@Component
public class CategoryMappingWithDefault extends CategoryMapping {

    @Autowired
    public CategoryMappingWithDefault(HttpServletRequest request, ConfigurationService configuration, ProductMapping productMapping, PictureMapping pictureMapping) {
        super(null, configuration, productMapping, pictureMapping);
    }
}
