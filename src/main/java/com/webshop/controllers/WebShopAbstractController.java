package com.webshop.controllers;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

public abstract class WebShopAbstractController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {EmptyResultDataAccessException.class, EntityNotFoundException.class, NoSuchElementException.class})
    public void handleNotFound() {
    }
}
