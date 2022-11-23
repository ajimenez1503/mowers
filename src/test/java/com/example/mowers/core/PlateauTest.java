package com.example.mowers.core;

import com.example.mowers.core.domain.Availability;
import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlateauTest {

    private int sizeX = 10;
    private int upperRightXCoordinate = sizeX - 1;
    private int sizeY = 22;
    Point positionInside = new Point(sizeX - 2, sizeY - 3);
    Point positionOutside = new Point(sizeX + 2, sizeY + 3);
    private int upperRightYCoordinate = sizeY - 1;

    @Test
    void thenCreatePlateau() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);
        assertNotNull(plateau.getId());
        assertEquals(sizeX, plateau.getSizeX());
        assertEquals(sizeY, plateau.getSizeY());
        assertNotNull(plateau.getAvailability());
        assertEquals(sizeX, plateau.getAvailability().length);
        Arrays.stream(plateau.getAvailability()).forEach(elements -> assertEquals(sizeY, elements.length));
        Arrays.stream(plateau.getAvailability()).forEach(elements -> Arrays.stream(elements).forEach(element -> assertEquals(Availability.FREE, element)));
        assertTrue(plateau.toString().contains("Plateau("));

        Plateau plateau2 = new Plateau(sizeX, sizeY);
        assertNotEquals(plateau2, plateau); // Different ID
    }

    @Test
    void givenPlateauDto_thenCreatePlateau() {
        PlateauDto plateauDto = new PlateauDto(upperRightXCoordinate, upperRightYCoordinate);
        assertNotNull(plateauDto);
        assertEquals(upperRightXCoordinate, plateauDto.getUpperRightXCoordinate());
        assertEquals(upperRightYCoordinate, plateauDto.getUpperRightYCoordinate());

        PlateauDto plateauDto2 = new PlateauDto(upperRightXCoordinate, upperRightYCoordinate);
        assertEquals(plateauDto2, plateauDto);

        Plateau plateau = new Plateau(plateauDto);
        assertNotNull(plateau);
        assertNotNull(plateau.getId());
        assertEquals(sizeX, plateau.getSizeX());
        assertEquals(sizeY, plateau.getSizeY());
        assertNotNull(plateau.getAvailability());
    }

    @Test
    void givenPlateau_getPlateauDto() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        PlateauDto plateauDtoCopy = plateau.getDto();
        assertEquals(plateauDtoCopy.getUpperRightXCoordinate() + 1, plateau.getSizeX());
        assertEquals(plateauDtoCopy.getUpperRightYCoordinate() + 1, plateau.getSizeY());
    }

    @Test
    void givenPlateau_thenSetPositionBusy_andSetPositionFree() throws Exception {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        assertTrue(plateau.isPositionAvailable(positionInside));
        plateau.setPositionBusy(positionInside);
        assertFalse(plateau.isPositionAvailable(positionInside));
        plateau.setPositionFree(positionInside);
        assertTrue(plateau.isPositionAvailable(positionInside));
    }

    @Test
    void givenPlateau_whenPositionOutside_thenSetPositionBusyFail() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        Exception thrown = assertThrows(
                Exception.class,
                () -> plateau.setPositionBusy(positionOutside));
        assertTrue(thrown.getMessage().contains("Position (" + positionOutside.getX() + ", " + positionOutside.getY() + ") is not valid"));
    }

    @Test
    void givenPlateau_whenPositionOutside_thenSetPositionFreeFail() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        Exception thrown = assertThrows(
                Exception.class,
                () -> plateau.setPositionFree(positionOutside));
        assertTrue(thrown.getMessage().contains("Position (" + positionOutside.getX() + ", " + positionOutside.getY() + ") is not valid"));
    }

    @Test
    void givenPlateau_whenPositionInside_thenIsValidPositionSuccess() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        assertTrue(plateau.isValidPosition(positionInside));
    }

    @Test
    void givenPlateau_whenPositionOutside_thenIsValidPositionFail() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        assertFalse(plateau.isValidPosition(new Point(sizeX, sizeY - 1)));
        assertFalse(plateau.isValidPosition(new Point(sizeX - 1, sizeY)));
    }

}
