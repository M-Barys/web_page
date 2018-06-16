package com.webshop.controllers;

import com.webshop.model.entity.Picture;
import com.webshop.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pictures")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Picture> getAllPicture() {
        return pictureService.getAllPicture();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Picture addPicture(@RequestBody Picture picture) {
        return pictureService.addPicture(picture);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Picture getPicture(@PathVariable Long id) {
        return pictureService.getPicture(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePicture(@PathVariable Long id) {
        pictureService.deletePicture(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {EmptyResultDataAccessException.class, EntityNotFoundException.class,
            NoSuchElementException.class})
    public void handleNotFound() {
    }

}
