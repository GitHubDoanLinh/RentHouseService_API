package com.example.renthouseweb_be.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapperUtil {
    private final ModelMapper modelMapper;
    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
    public <S, T> T map(S source, Class<T> targetClass){
        return modelMapper.map(source,targetClass);
    }
}
