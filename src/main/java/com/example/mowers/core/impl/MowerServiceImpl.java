package com.example.mowers.core.impl;

import com.example.mowers.core.domain.Command;
import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.port.MowerRepo;
import com.example.mowers.port.MowerService;
import com.example.mowers.port.PlateauService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class MowerServiceImpl implements MowerService {

    private final MowerRepo mowerRepo;
    private final PlateauService plateauService;


    @Override
    public Optional<Mower> createMower(MowerDto mowerRequest) {
        // Ensure the plateau ID is valid
        Optional<Plateau> plateauOptional = plateauService.getPlateau(mowerRequest.getPlateauId());
        if (plateauOptional.isEmpty()) {
            log.warn("Plateau Id {} does not exits", mowerRequest.getPlateauId());
            return Optional.empty();
        }
        Plateau plateau = plateauOptional.get();
        if (!plateau.isValidPosition(mowerRequest.getPosition())) {
            log.warn("The new mower position {} is not valid", mowerRequest.getPosition());
            return Optional.empty();
        }
        if (!plateau.isPositionAvailable(mowerRequest.getPosition())) {
            log.warn("The new mower position {} is not available", mowerRequest.getPosition());
            return Optional.empty();
        }
        try {
            plateau.setPositionBusy(mowerRequest.getPosition());
        } catch (Exception e) {
            log.warn("The mower position {} is not available. Exception {}", mowerRequest.getPosition(), e.getMessage());
            return Optional.empty();
        }

        return Optional.of(mowerRepo.createMower(mowerRequest));
    }

    @Override
    public Optional<Mower> getMower(String mowerId) {
        return mowerRepo.getMower(mowerId);
    }

    @Override
    public Optional<Mower> moveMower(Mower mower, List<Command> commands) {
        Optional<Plateau> plateauOptional = plateauService.getPlateau(mower.getPlateauId());
        if (plateauOptional.isEmpty()) {
            log.warn("Plateau Id {} does not exits", mower.getPlateauId());
            return Optional.empty();
        }
        Plateau plateau = plateauOptional.get();
        for (Command c : commands) {
            Point nextPosition = getNextPosition(mower, c);
            if (nextPosition.equals(mower.getPosition())) {
                execute(mower, c);
            } else if (plateau.isValidPosition(nextPosition) && plateau.isPositionAvailable(nextPosition)) {
                // nextPosition is available
                try {
                    plateau.setPositionFree(mower.getPosition());
                    execute(mower, c);
                    plateau.setPositionBusy(mower.getPosition());
                } catch (Exception e) {
                    log.warn("The mower position {} is not available. Exception {}", nextPosition, e.getMessage());
                    return Optional.empty();
                }
            }
        }

        return Optional.of(mower);
    }

    public Point getNextPosition(Mower mower, Command command) {
        if (command == Command.M) {
            switch (mower.getOrientation()) {
                case N:
                    return new Point((int) mower.getPosition().getX(), (int) (mower.getPosition().getY() + 1));
                case S:
                    return new Point((int) mower.getPosition().getX(), (int) (mower.getPosition().getY() - 1));
                case E:
                    return new Point((int) (mower.getPosition().getX() + 1), (int) mower.getPosition().getY());
                case W:
                    return new Point((int) (mower.getPosition().getX() - 1), (int) mower.getPosition().getY());
            }
        }
        return mower.getPosition();
    }

    public void execute(Mower mower, Command command) {
        if (command.equals(Command.R) || command.equals(Command.L)) {
            mower.setOrientation(mower.getOrientation().getNextOrientation(command));
        }
        if (command.equals(Command.M)) {
            mower.setPosition(getNextPosition(mower, command));
        }
    }
}
