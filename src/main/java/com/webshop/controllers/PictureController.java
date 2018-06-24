package com.webshop.controllers;

import com.google.common.base.Preconditions;
import com.webshop.model.instance.PictureRef;
import com.webshop.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pictures")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping(method = RequestMethod.GET)
    public List<PictureRef> getAllPicture() {
        return pictureService.getAllPicture();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    PictureRef addPicture(@RequestParam("file") MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        //Preconditions.checkState(contentType.compareTo("image/jpg") == 0);
        //TODO use file information to extract filename, file type
        return pictureService.addPicture(file);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PictureRef getPicture(@PathVariable Long id) {
        return pictureService.getPicture(id);
    }


    //TODO test file retrieval
    @GetMapping(
            value = "/{id}/image.jpg",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public ByteArrayResource serveFile(@PathVariable("id") Long id) {
        return new ByteArrayResource(pictureService.getPictureContent(id));
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