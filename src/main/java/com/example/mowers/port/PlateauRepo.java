package com.example.mowers.port;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;

import java.util.Optional;

public interface PlateauRepo {
    Plateau createPlateau(PlateauDto plateau);

    Optional<Plateau> getPlateau(String id);
}
