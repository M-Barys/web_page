package com.webshop.services;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private final Gson gson = new Gson();

    public Gson getGson() {
        return gson;
    }
}
