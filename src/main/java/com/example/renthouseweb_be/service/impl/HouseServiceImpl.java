package com.example.renthouseweb_be.service.impl;

import com.example.renthouseweb_be.model.Convenient;
import com.example.renthouseweb_be.model.House;
import com.example.renthouseweb_be.model.Image;
import com.example.renthouseweb_be.repository.ConvenientRepository;
import com.example.renthouseweb_be.repository.HouseRepository;
import com.example.renthouseweb_be.requests.CreateHouseRequest;
import com.example.renthouseweb_be.service.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final ConvenientRepository convenient;

    private final ImageServiceImpl imageService;

    public HouseServiceImpl(HouseRepository houseRepository, ConvenientRepository convenient, ImageServiceImpl imageService) {
        this.houseRepository = houseRepository;
        this.convenient = convenient;
        this.imageService = imageService;
    }

    @Override
    public Iterable<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public Page<House> findAll(Pageable pageable) {
        return houseRepository.findAll(pageable);
    }

    @Override
    public Page<House> getAllHousesSortedByPriceUp(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("price").ascending());
        return houseRepository.findAll(pageRequest);
    }

    @Override
    public Page<House> findAllByCategoryId(Long categoriesId, Pageable pageable) {
        return houseRepository.findAllByCategoryId(categoriesId, pageable);
    }

    @Override
    public Page<House> findAllByLocation(String location, Pageable pageable) {
        return houseRepository.findAllByLocation(location, pageable);
    }

    @Override
    public Iterable<House> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag) {
        return houseRepository.findAllByUserIdAndDeleteFlag(userId, deleteFlag);
    }

    @Override
    public Iterable<House> findByNameContainsIgnoreCaseAndDeleteFlag(String name, boolean deleteFlag) {
        return houseRepository.findByNameContainsIgnoreCaseAndAndDeleteFlag(name, deleteFlag);
    }

    @Override
    public Iterable<House> findAllByDeleteFlag(boolean deleteFlag) {
        return houseRepository.findAllByDeleteFlag(false);
    }

    @Override
    public Optional<House> findOneById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public House save(House house) {
        return null;
    }
    public House save(CreateHouseRequest request) {
        House house = new House();
        house.setName(request.getName());
        house.setCategory(request.getCategory());
        house.setBedRoom(request.getBedRoom());
        house.setDescription(request.getDescription());
        house.setKitchen(request.getKitchen());
        house.setPrice(request.getPrice());
        house.setUser(request.getUser());
        house.setLocation(request.getLocation());
        house.setBathRoom(request.getBathRoom());
        house.setLivingRoom(request.getLivingRoom());
        return houseRepository.save(house);
    }
    @Async
    public CompletableFuture<Void> saveImageListAsync(House house, List<String> imageList) {
        imageList.forEach(image -> {
            Image imageEntity = new Image();
            imageEntity.setHouse(house);
            imageEntity.setImage(image);
            imageService.save(imageEntity);
        });
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void delete(Long id) {
        Optional<House> house = houseRepository.findById(id);
        if (house.isPresent()) {
            house.get().setDeleteFlag(true);
            houseRepository.save(house.get());
        }
    }

    @Transactional
    public void addConvenientsToHouse(Long houseId, List<Long> convenientIds) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isPresent()) {
            House house = houseOptional.get();
            Set<Convenient> convenients = house.getConvenients();
            convenients.addAll(convenient.findAllById(convenientIds));
            houseRepository.save(house);
        } else {
            throw new RuntimeException("House not found");
        }
    }

}
