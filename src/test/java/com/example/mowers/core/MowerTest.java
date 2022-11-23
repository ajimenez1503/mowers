package com.example.mowers.core;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.dto.MowerDto;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MowerTest {

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

        Mower mower2 = new Mower(plateauId, position, orientation);
        assertNotEquals(mower2, mower); // Different ID
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
        MowerDto mowerDto = new MowerDto(plateauId, position, orientation);
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);

        MowerDto mowerDtoCopy = mower.getDto();
        assertEquals(mowerDtoCopy.getPlateauId(), mower.getPlateauId());
        assertEquals(mowerDtoCopy.getPosition(), mower.getPosition());
        assertEquals(mowerDtoCopy.getOrientation(), mower.getOrientation());
        assertEquals(mowerDto, mowerDtoCopy);
    }
}
