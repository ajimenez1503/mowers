package com.example.mowers.core;

import com.example.mowers.core.domain.Availability;
import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PlateauTest {

    private int sizeX = 10;
    private int sizeY = 22;
    Point positionInside = new Point(sizeX, sizeY);
    Point positionOutside = new Point(sizeX + 2, sizeY + 3);


    @Test
    void thenCreatePlateau() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);
        assertNotNull(plateau.getId());
        assertEquals(sizeX + 1, plateau.getSizeX());
        assertEquals(sizeY + 1, plateau.getSizeY());
        assertNotNull(plateau.getAvailability());
        assertEquals(sizeX + 1, plateau.getAvailability().length);
        Arrays.stream(plateau.getAvailability()).forEach(elements -> assertEquals(sizeY + 1, elements.length));
        Arrays.stream(plateau.getAvailability()).forEach(elements -> Arrays.stream(elements).forEach(element -> assertEquals(Availability.FREE, element)));
        assertTrue(plateau.toString().contains("Plateau("));

        Plateau plateau2 = new Plateau(sizeX, sizeY);
        assertNotEquals(plateau2, plateau); // Different ID
    }

    @Test
    void givenPlateauDto_thenCreatePlateau() {
        PlateauDto plateauDto = new PlateauDto(sizeX, sizeY);
        assertNotNull(plateauDto);
        assertEquals(sizeX, plateauDto.getSizeX());
        assertEquals(sizeY, plateauDto.getSizeY());
        PlateauDto plateauDto2 = new PlateauDto(sizeX, sizeY);
        assertEquals(plateauDto2, plateauDto);

        Plateau plateau = new Plateau(plateauDto);
        assertNotNull(plateau);
        assertNotNull(plateau.getId());
        assertEquals(sizeX + 1, plateau.getSizeX());
        assertEquals(sizeY + 1, plateau.getSizeY());
        assertNotNull(plateau.getAvailability());
    }

    @Test
    void givenPlateau_getPlateauDto() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        PlateauDto plateauDtoCopy = plateau.getDto();
        assertEquals(plateauDtoCopy.getSizeX(), plateau.getSizeX());
        assertEquals(plateauDtoCopy.getSizeY(), plateau.getSizeY());
    }

    @Test
    void givenPlateau_thenSetPositionBusy_andSetPositionFree() throws Exception {
        Plateau plateau = new Plateau(sizeX, sizeY);
        assertNotNull(plateau);

        Point position = new Point(sizeX, sizeY);
        assertTrue(plateau.isPositionAvailable(position));
        plateau.setPositionBusy(position);
        assertFalse(plateau.isPositionAvailable(position));
        plateau.setPositionFree(position);
        assertTrue(plateau.isPositionAvailable(position));
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

        assertFalse(plateau.isValidPosition(positionOutside));
    }

}
