package com.webshop.model.mapping;

import com.webshop.model.StoreLanguage;
import com.webshop.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;

@Component
@RequestScope
public class ProductMappingWithLanguage extends ProductMapping {

    @Autowired
    public ProductMappingWithLanguage(HttpServletRequest request, ConfigurationService configuration, PictureMapping pictureMapping) {
        super(StoreLanguage.fromHeader(request.getHeader("X-API-Lang")), configuration, pictureMapping);
    }
}
