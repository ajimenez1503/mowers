package com.example.mowers.port;

import com.example.mowers.core.domain.Command;
import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.dto.MowerDto;

import java.util.List;
import java.util.Optional;

public interface MowerService {
    Optional<Mower> createMower(MowerDto mower);

    Optional<Mower> getMower(String id);

    public Optional<Mower> moveMower(Mower mower, List<Command> commands);
}
