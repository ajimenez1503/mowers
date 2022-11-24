package com.example.mowers.core.impl;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import com.example.mowers.port.PlateauRepo;
import com.example.mowers.port.PlateauService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PlateauServiceImpl implements PlateauService {

    private final PlateauRepo plateauRepo;

    @Override
    public Optional<Plateau> createPlateau(PlateauDto plateauRequest) {
        return Optional.ofNullable(plateauRepo.createPlateau(plateauRequest));
    }

    @Override
    public Optional<Plateau> getPlateau(String plateauId) {
        return plateauRepo.getPlateau(plateauId);
    }
}
