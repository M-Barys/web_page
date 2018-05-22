package com.webshop.model.entity;

import com.webshop.model.StoreLanguage;

import javax.validation.constraints.NotNull;

public abstract class LanguageAwareEntity {
    @NotNull
    private StoreLanguage language;

    public StoreLanguage getLanguage() {
        return language;
    }

    public void setLanguage(StoreLanguage language) {
        this.language = language;
    }
}
