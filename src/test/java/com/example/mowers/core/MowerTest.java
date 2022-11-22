package com.example.mowers.core;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.dto.MowerDto;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MowerTest {

    private String plateauId = "plateauId";
    private Point position = new Point(10, 22);
    private Orientation orientation = Orientation.N;

    @Test
    void thenCreateMower() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        assertNotNull(mower.getId());
        assertEquals(plateauId, mower.getPlateauId());
        assertEquals(position, mower.getPosition());
        assertEquals(orientation, mower.getOrientation());
    }

    @Test
    void givenMowerDto_thenCreateMower() {
        MowerDto mowerDto = new MowerDto(plateauId, position, orientation);
        assertNotNull(mowerDto);
        assertEquals(plateauId, mowerDto.getPlateauId());
        assertEquals(position, mowerDto.getPosition());
        assertEquals(orientation, mowerDto.getOrientation());

        Mower mower = new Mower(mowerDto);
        assertNotNull(mower.getId());
        assertEquals(plateauId, mower.getPlateauId());
        assertEquals(position, mower.getPosition());
        assertEquals(orientation, mower.getOrientation());
    }

    @Test
    void givenMower_getMowerDto() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);

        MowerDto mowerDto = mower.getDto();
        assertEquals(mowerDto.getPlateauId(), mower.getPlateauId());
        assertEquals(mowerDto.getPosition(), mower.getPosition());
        assertEquals(mowerDto.getOrientation(), mower.getOrientation());
    }
}
