package com.example.renthouseweb_be.service.impl;

import com.example.renthouseweb_be.model.Image;
import com.example.renthouseweb_be.repository.ImageRepository;
import com.example.renthouseweb_be.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }
    @Override
    public Iterable<Image> findAllByDeleteFlag(boolean deleteFlag) {
        return null;
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
        image.ifPresent(value -> value.setDeleteFlag(true));
    }
}
