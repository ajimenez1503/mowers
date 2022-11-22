package com.example.mowers.core.impl;

import com.example.mowers.core.domain.Command;
import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.port.MowerRepo;
import com.example.mowers.port.MowerService;
import com.example.mowers.port.PlateauService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MowerServiceImpl implements MowerService {

    final private MowerRepo mowerRepo;
    final private PlateauService plateauService;


    @Override
    public Optional<Mower> createMower(MowerDto mower) {
        // Ensure the plateau ID is valid
        Optional<Plateau> plateauOptional = plateauService.getPlateau(mower.getPlateauId());
        if (plateauOptional.isEmpty()) {
            log.warn("Plateau Id {} does not exits", mower.getPlateauId());
            return Optional.empty();
        }
        Plateau plateau = plateauOptional.get();
        if (!plateau.isValidPosition(mower.getPosition())) {
            log.warn("The mower position {} is not valid", mower.getPosition());
            return Optional.empty();
        }
        if (!plateau.isPositionAvailable(mower.getPosition())) {
            log.warn("The mower position {} is not available", mower.getPosition());
            return Optional.empty();
        }
        try {
            plateau.setPositionBusy(mower.getPosition());
        } catch (Exception e) {
            log.warn("The mower position {} is not available. Exception {}", mower.getPosition(), e.getMessage());
            return Optional.empty();
        }

        return Optional.of(mowerRepo.createMower(mower));
    }

    @Override
    public Optional<Mower> getMower(String id) {
        return mowerRepo.getMower(id);
    }

    @Override
    public Optional<Mower> moveMower(Mower mower, List<Command> commands) {
        if (commands == null) {
            log.warn("Commands {} is not valid");
            return Optional.empty();
        }
        Optional<Plateau> plateauOptional = plateauService.getPlateau(mower.getPlateauId());
        if (plateauOptional.isEmpty()) {
            log.warn("Plateau Id {} does not exits", mower.getPlateauId());
            return Optional.empty();
        }
        Plateau plateau = plateauOptional.get();
        Point nextPosition;
        for (Command c : commands) {
            nextPosition = mower.getNextPosition(c);
            if (nextPosition.equals(mower.getPosition())) {
                mower.execute(c);
            } else if (plateau.isValidPosition(nextPosition) && plateau.isPositionAvailable(nextPosition)) {
                try {
                    plateau.setPositionFree(mower.getPosition());
                    mower.execute(c);
                    plateau.setPositionBusy(mower.getPosition());
                } catch (Exception e) {
                    log.warn("The mower position {} is not available. Exception {}", nextPosition, e.getMessage());
                    return Optional.empty();
                }
            }
        }

        return Optional.of(mower);
    }
}
