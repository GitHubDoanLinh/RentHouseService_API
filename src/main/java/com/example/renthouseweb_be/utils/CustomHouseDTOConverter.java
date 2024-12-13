package com.example.renthouseweb_be.utils;

import com.example.renthouseweb_be.dto.HouseDTO;
import com.example.renthouseweb_be.dto.UserDTO;
import com.example.renthouseweb_be.model.House;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

public class CustomHouseDTOConverter implements Converter<House, HouseDTO> {
    @Override
    public HouseDTO convert(MappingContext<House, HouseDTO> mappingContext) {
        ModelMapper modelMapper = new ModelMapper();
        House house = mappingContext.getSource();
        HouseDTO houseDTO = modelMapper.map(house,HouseDTO.class);
        houseDTO.setUserDTO(modelMapper.map(house.getUser(), UserDTO.class));
        return houseDTO;
    }
}
