package com.example.mowers.core.impl;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.port.PlateauRepo;
import com.example.mowers.port.PlateauService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlateauServiceImpl implements PlateauService {

    final private PlateauRepo plateauRepo;

    @Override
    public Plateau createPlateau(Plateau plateau) {
        return plateauRepo.createPlateau(plateau);
    }

    @Override
    public Optional<Plateau> getPlateau(String id) {
        return plateauRepo.getPlateau(id);
    }
}
