package com.example.renthouseweb_be.service.impl;

import com.example.renthouseweb_be.model.Convenient;
import com.example.renthouseweb_be.repository.ConvenientRepository;
import com.example.renthouseweb_be.service.ConvenientService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConvenientServiceImpl implements ConvenientService {
    private final ConvenientRepository convenientRepository;
    public ConvenientServiceImpl(ConvenientRepository convenientRepository) {
        this.convenientRepository = convenientRepository;
    }

    @Override
    public Iterable<Convenient> findAll() {
        return convenientRepository.findAll();
    }

    @Override
    public Iterable<Convenient> findAllByDeleteFlag(boolean deleteFlag) {
        return null;
    }

    @Override
    public Optional<Convenient> findOneById(Long id) {
        return convenientRepository.findById(id);
    }

    @Override
    public Convenient save(Convenient convenient) {
        return convenientRepository.save(convenient);
    }

    @Override
    public void delete(Long id) {
        Optional<Convenient> convenient = convenientRepository.findById(id);
        convenient.ifPresent(value -> value.setDeleteFlag(true));
        convenientRepository.save(convenient.get());
    }
}
