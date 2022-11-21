package com.example.mowers.port;

import com.example.mowers.core.domain.Mower;

import java.util.Optional;

public interface MowerService {
    Optional<Mower> createMower(Mower mower);

    Optional<Mower> getMower(String id);
}

