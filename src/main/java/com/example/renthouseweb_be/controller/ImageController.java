package com.example.renthouseweb_be.controller;

import com.example.renthouseweb_be.model.Image;
import com.example.renthouseweb_be.service.impl.ImageServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin("*")
public class ImageController {
    private final ImageServiceImpl imageService;

    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @GetMapping("")
    public ResponseEntity<List<Image>> showAllImage() {
        List<Image> productImages = (List<Image>) imageService.findAllByDeleteFlag(false);
        if (productImages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productImages, HttpStatus.OK);
    }

    @PostMapping("/create/{houseId}")
    public ResponseEntity<Iterable<Image>> saveImage(@PathVariable Long houseId,
                                                     @RequestBody List<String> productImages) {
        return new ResponseEntity<>(imageService.addImagesToHouse(houseId, productImages), HttpStatus.OK);
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<List<Image>> getAllByHouseId(@PathVariable Long houseId) {
        List<Image> images = (List<Image>) imageService.findAllByHouseId(houseId);
        if (images.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Image> deleteImg(@PathVariable Long id) {
        imageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
