package com.example.mowers.adapter;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.dto.MowerDto;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MowerRepoImplTest {
    private MowerRepoImpl repo = new MowerRepoImpl();
    private String plateauId = "plateauId";
    private Point position = new Point(10, 22);
    private Orientation orientation = Orientation.N;

    private int sizeX = 10, sizeY = 25;

    @Test
    void givenRepo_thenCreateMower() {
        MowerDto mower = new MowerDto(plateauId, position, orientation);
        Mower mowerResult = repo.createMower(mower);
        assertNotNull(mowerResult);
        assertNotNull(mowerResult.getId());
        assertEquals(mower.getPlateauId(), mowerResult.getPlateauId());
        assertEquals(mower.getPosition(), mowerResult.getPosition());
        assertEquals(mower.getOrientation(), mowerResult.getOrientation());
    }

    @Test
    void givenRepo_whenCreateMower_thenGetTheMower() {
        MowerDto mower = new MowerDto(plateauId, position, orientation);
        Mower mowerCreated = repo.createMower(mower);
        assertNotNull(mowerCreated);

        Optional<Mower> mowerResult = repo.getMower(mowerCreated.getId());
        assertTrue(mowerResult.isPresent());
        assertEquals(mowerCreated, mowerResult.get());
    }

    @Test
    void givenRepo_whenGetInvalidMower_thenOptionalIsEmpty() {
        Optional<Mower> mowerResult = repo.getMower("InvalidID");
        assertTrue(mowerResult.isEmpty());
    }
}
