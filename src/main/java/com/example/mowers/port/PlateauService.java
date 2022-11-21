package com.example.mowers.port;

import com.example.mowers.core.domain.Plateau;

import java.util.Optional;

public interface PlateauService {
    Plateau createPlateau(Plateau plateau);

    Optional<Plateau> getPlateau(String id);
}
