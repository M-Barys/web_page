package com.webshop.model.instance.data;

import com.webshop.model.StoreLanguage;

import java.util.HashMap;
import java.util.Map;

public class PerLanguageData<T> {

    private Map<StoreLanguage, T> perLanguage = new HashMap<>();

    public void update(StoreLanguage language, T data) {
        perLanguage.put(language, data);
    }

}
