package com.example.mowers.core.impl;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import com.example.mowers.port.PlateauRepo;
import com.example.mowers.port.PlateauService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlateauServiceImpl implements PlateauService {

    private final PlateauRepo plateauRepo;

    @Override
    public Optional<Plateau> createPlateau(PlateauDto plateau) {
        return Optional.ofNullable(plateauRepo.createPlateau(plateau));
    }

    @Override
    public Optional<Plateau> getPlateau(String id) {
        return plateauRepo.getPlateau(id);
    }
}
