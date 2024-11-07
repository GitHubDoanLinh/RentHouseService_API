package com.example.renthouseweb_be.service;

import com.example.renthouseweb_be.model.Image;

import java.util.List;

public interface ImageService extends IGenerateService<Image> {
    Iterable<Image> findAllByHouseIdAndDeleteFlag(Long houseId, boolean deleteFlag);
    Iterable<Image> addImagesToHouse(Long houseId, List<String> imageUrls);
}
