package com.example.mowers.port;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.dto.MowerDto;

import java.util.Optional;

public interface MowerRepo {
    Mower createMower(MowerDto mower);

    Optional<Mower> getMower(String id);
}
