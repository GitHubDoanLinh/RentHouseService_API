package com.example.renthouseweb_be.service.impl;

import com.example.renthouseweb_be.model.House;
import com.example.renthouseweb_be.repository.HouseRepository;
import com.example.renthouseweb_be.service.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService{

    private final HouseRepository houseRepository;

    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
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
    public Iterable<House> findAllByDeleteFlag(boolean deleteFlag) {
        return houseRepository.findAllByDeleteFlag(false);
    }

    @Override
    public Optional<House> findOneById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Override
    public void delete(Long id) {
        Optional<House> house= houseRepository.findById(id);
        if(house.isPresent()){
            house.get().setDeleteFlag(true);
            houseRepository.save(house.get());
        }
    }
}
