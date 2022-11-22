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
    private Point point = new Point(10, 22);
    private Orientation orientation = Orientation.N;

    @Test
    void thenCreateMower() {
        Mower mower = new Mower(plateauId, point, orientation);
        assertNotNull(mower);
        assertNotNull(mower.getId());
        assertEquals(plateauId, mower.getPlateauId());
        assertEquals(point, mower.getPosition());
        assertEquals(orientation, mower.getOrientation());
    }

    @Test
    void givenMowerDto_thenCreateMower() {
        MowerDto mowerDto = new MowerDto(plateauId, point, orientation);
        assertNotNull(mowerDto);
        assertEquals(plateauId, mowerDto.getPlateauId());
        assertEquals(point, mowerDto.getPosition());
        assertEquals(orientation, mowerDto.getOrientation());

        Mower mower = new Mower(mowerDto);
        assertNotNull(mower.getId());
        assertEquals(plateauId, mower.getPlateauId());
        assertEquals(point, mower.getPosition());
        assertEquals(orientation, mower.getOrientation());
    }
}
