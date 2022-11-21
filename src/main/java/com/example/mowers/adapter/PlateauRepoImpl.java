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

    private Map<String, Plateau> plateauMap = new HashMap<String, Plateau>();

    @Override
    public Plateau createPlateau(PlateauDto plateau) {
        Plateau plateauToSave = new Plateau(plateau);

        plateauMap.put(plateauToSave.getId(), plateauToSave);
        return plateauToSave;
    }

    @Override
    public Optional<Plateau> getPlateau(String id) {
        return Optional.ofNullable(plateauMap.get(id));
    }
}
