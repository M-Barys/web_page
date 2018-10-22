package com.webshop.controllers;

import com.webshop.model.instance.PictureRef;
import com.webshop.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pictures")

public class PictureController extends AbstractControllerExceptionHandler {

    @Autowired
    private PictureService pictureService;

    @RequestMapping(method = RequestMethod.GET)
    public List<PictureRef> getAllPicture() {
        return pictureService.getAllPicture();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    PictureRef addPicture(@RequestParam("file") MultipartFile file) throws IOException {
        return pictureService.addPicture(file);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PictureRef getPicture(@PathVariable Long id) {
        return pictureService.getPicture(id);
    }


    @GetMapping(
            value = "/{id}/image.jpg",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public Resource serveFile(@PathVariable("id") Long id) {
        return new InputStreamResource(pictureService.getPictureContent(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePicture(@PathVariable Long id) {
        pictureService.deletePicture(id);
    }

}

