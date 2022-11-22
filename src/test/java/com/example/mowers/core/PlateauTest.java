package com.example.mowers.core;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlateauTest {

    private int sizeX = 10;
    private int sizeY = 22;


    @Test
    void thenCreatePlateau() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);
        assertNotNull(plateau.getId());
        assertEquals(sizeX + 1, plateau.getSizeX());
        assertEquals(sizeY + 1, plateau.getSizeY());
        assertNotNull(plateau.getAvailability());
    }

    @Test
    void givenPlateauDto_thenCreatePlateau() {
        PlateauDto plateauDto = new PlateauDto(sizeX, sizeY);
        assertNotNull(plateauDto);
        assertEquals(sizeX, plateauDto.getSizeX());
        assertEquals(sizeY, plateauDto.getSizeY());

        Plateau plateau = new Plateau(plateauDto);
        assertNotNull(plateau);
        assertNotNull(plateau.getId());
        assertEquals(sizeX + 1, plateau.getSizeX());
        assertEquals(sizeY + 1, plateau.getSizeY());
        assertNotNull(plateau.getAvailability());
    }

    @Test
    void givenPlateau_whenPosInside_thenCheckPosition() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        Point pos = new Point(sizeX, sizeY);
        assertTrue(plateau.isValidPosition(pos));
    }

    @Test
    void givenPlateau_whenPosOut_thenCheckPosition() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        Point pos = new Point(sizeX + 5, sizeY);
        assertFalse(plateau.isValidPosition(pos));
    }

}
