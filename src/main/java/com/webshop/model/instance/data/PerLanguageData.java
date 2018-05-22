package com.webshop.model.instance.data;

import com.webshop.model.StoreLanguage;

import java.util.Map;

public class PerLanguageData<T> {

    private Map<StoreLanguage, T> perLanguage;

    public void update(StoreLanguage language, T data) {
        perLanguage.put(language, data);
    }

    public Map<StoreLanguage, T> getPerLanguage() {
        return perLanguage;
    }

    public void setPerLanguage(Map<StoreLanguage, T> perLanguage) {
        this.perLanguage = perLanguage;
    }
}
