package com.example.mowers.adapter;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.port.PlateauRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlateauRepoImpl implements PlateauRepo {

    private Map<String, Plateau> plateauMap = new HashMap<String, Plateau>();

    @Override
    public Plateau createPlateau(Plateau plateau) {
        plateau.setId(UUID.randomUUID().toString());

        plateauMap.put(plateau.getId(), plateau);
        return plateau;
    }

    @Override
    public Optional<Plateau> getPlateau(String id) {
        if (plateauMap.containsKey(id)) {
            return Optional.of(plateauMap.get(id));
        } else {
            return Optional.empty();
        }
    }
}
