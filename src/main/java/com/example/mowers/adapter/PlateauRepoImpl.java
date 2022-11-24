package com.example.mowers.adapter;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import com.example.mowers.port.PlateauRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PlateauRepoImpl implements PlateauRepo {

    private Map<String, Plateau> plateauMap = new HashMap<>();

    @Override
    public Plateau createPlateau(PlateauDto plateauRequest) {
        Plateau newPlateau = new Plateau(plateauRequest);

        plateauMap.put(newPlateau.getId(), newPlateau);
        return newPlateau;
    }

    @Override
    public Optional<Plateau> getPlateau(String plateauId) {
        return Optional.ofNullable(plateauMap.get(plateauId));
    }
}
