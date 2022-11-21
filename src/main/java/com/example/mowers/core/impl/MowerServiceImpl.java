package com.example.mowers.core.impl;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.port.MowerRepo;
import com.example.mowers.port.MowerService;
import com.example.mowers.port.PlateauService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MowerServiceImpl implements MowerService {

    final private MowerRepo mowerRepo;
    final private PlateauService plateauService;


    @Override
    public Optional<Mower> createMower(Mower mower) {
        // Ensure the plateau ID is valid
        if (plateauService.getPlateau(mower.getPlateauId()).isEmpty()) {
            log.warn("Plateau ID {} does not exits", mower.getPlateauId());
            return Optional.empty();
        }

        return Optional.of(mowerRepo.createMower(mower));
    }

    @Override
    public Optional<Mower> getMower(String id) {
        return mowerRepo.getMower(id);
    }
}
