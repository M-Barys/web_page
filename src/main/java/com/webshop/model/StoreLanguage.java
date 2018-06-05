package com.webshop.model;

public enum StoreLanguage {
    EN, PL;

    public static final String languageHeader = "X-API-Lang";

    public static StoreLanguage fromHeader(String header) {
        StoreLanguage defaultLang = PL;
        if (header == null) {
            return defaultLang;
        }
        try {
            return StoreLanguage.valueOf(header);
        } catch (IllegalArgumentException e) {
            return defaultLang;
        }

    }
}
