package com.example.renthouseweb_be.controller;
import com.example.renthouseweb_be.dto.HouseDTO;
import com.example.renthouseweb_be.model.House;
import com.example.renthouseweb_be.service.impl.HouseServiceImpl;
import com.example.renthouseweb_be.utils.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/houses")
@CrossOrigin("*")
public class HouseController {
    private final HouseServiceImpl houseService;
    private final ModelMapperUtil modelMapperUtil;
    public HouseController(HouseServiceImpl houseService, ModelMapperUtil modelMapperUtil) {
        this.houseService = houseService;
        this.modelMapperUtil = modelMapperUtil;
    }
    @GetMapping("")
    public ResponseEntity<List<HouseDTO>> showAll() {
        List<House> house = (List<House>) houseService.findAllByDeleteFlag(false);
        if (house.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(modelMapperUtil.mapList(house, HouseDTO.class), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<House> save(@RequestBody House house) {
        return new ResponseEntity<>(houseService.save(house), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<House> delete(@PathVariable Long id){
        houseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<House> update(@PathVariable Long id, @RequestBody House house) {
        house.setId(id);
        houseService.save(house);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<House>> findById(@PathVariable Long id) {
        Optional<House> house = houseService.findOneById(id);
        if(house.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @GetMapping("/showAll")
    public ResponseEntity<Page<House>> getAllHouse(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam String sortOrder
    ) {
        Pageable pageable;
        if (sortOrder.isEmpty()) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), "price");
            pageable = PageRequest.of(page, size, sort);
        }
        Page<House> houses = houseService.findAll(pageable);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @PostMapping("/searchByCate")
    public ResponseEntity<Page<House>> searchByCategory(@PageableDefault(value = 12)
        Pageable pageable, @RequestBody Long categoriesId) {
        if (categoriesId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Page<House> houses = houseService.findAllByCategoryId(categoriesId, pageable);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @PostMapping("/{houseId}/addConvenient")
    public ResponseEntity<String> addConvenientToHouse(@PathVariable Long houseId, @RequestBody List<Long> convenientIds) {
        try {
            houseService.addConvenientsToHouse(houseId, convenientIds);
            return ResponseEntity.ok("Convenients added to house successfully");
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error when adding convenients to house:" +e.getMessage());
        }
    }

}
