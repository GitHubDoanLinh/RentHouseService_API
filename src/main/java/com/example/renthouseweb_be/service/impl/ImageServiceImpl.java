package com.example.renthouseweb_be.service.impl;

import com.example.renthouseweb_be.model.House;
import com.example.renthouseweb_be.model.Image;
import com.example.renthouseweb_be.repository.HouseRepository;
import com.example.renthouseweb_be.repository.ImageRepository;
import com.example.renthouseweb_be.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    private final HouseRepository houseRepository;

    public ImageServiceImpl(ImageRepository imageRepository, HouseRepository houseRepository) {
        this.imageRepository = imageRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Iterable<Image> findAllByDeleteFlag(boolean deleteFlag) {
        return imageRepository.findAllByDeleteFlag(false);
    }

    @Override
    public Optional<Image> findOneById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void delete(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            image.get().setDeleteFlag(true);
            imageRepository.save(image.get());
        }
    }

    @Override
    public Iterable<Image> findAllByHouseIdAndDeleteFlag(Long houseId,boolean deleteFlag) {
        return imageRepository.findAllByHouseIdAndDeleteFlag(houseId,false);
    }

    @Override
    @Transactional
    public Iterable<Image> addImagesToHouse(Long houseId, List<String> imageUrls) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new RuntimeException("House not found"));

        for (String imageUrl : imageUrls) {
            Image image = new Image();
            image.setImage(imageUrl);
            image.setHouse(house);
            imageRepository.save(image);
        }
        return imageRepository.findAllByHouseIdAndDeleteFlag(houseId,false);
    }

    @Override
    public Image getImageByHouseId(Long houseId) {
        List<Image> images = (List<Image>) findAllByHouseIdAndDeleteFlag(houseId,false);
        return images.get(0);
    }


}
