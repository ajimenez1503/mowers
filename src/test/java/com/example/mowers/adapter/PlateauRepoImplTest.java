package com.example.mowers.adapter;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlateauRepoImplTest {

    private PlateauRepoImpl repo = new PlateauRepoImpl();

    private int sizeX = 10;
    private int upperRightXCoordinate = sizeX - 1;
    private int sizeY = 22;
    private int upperRightYCoordinate = sizeY - 1;

    private String invalidId = "InvalidId";

    @Test
    void givenRepo_thenCreatePlateau() {
        PlateauDto plateau = new PlateauDto(upperRightXCoordinate, upperRightYCoordinate);
        Plateau plateauResult = repo.createPlateau(plateau);
        assertNotNull(plateauResult);
        assertNotNull(plateauResult.getId());
        assertEquals(sizeX, plateauResult.getSizeX());
        assertEquals(sizeY, plateauResult.getSizeY());
    }

    @Test
    void givenRepo_whenCreatePlateau_thenGetThePlateau() {
        PlateauDto plateau = new PlateauDto(upperRightXCoordinate, upperRightYCoordinate);
        Plateau plateauCreated = repo.createPlateau(plateau);
        assertNotNull(plateauCreated);

        Optional<Plateau> plateauResult = repo.getPlateau(plateauCreated.getId());
        assertTrue(plateauResult.isPresent());
        assertEquals(plateauCreated, plateauResult.get());
    }

    @Test
    void givenRepo_whenGetInvalidPlateau_thenOptionalIsEmpty() {
        Optional<Plateau> plateauResult = repo.getPlateau(invalidId);
        assertTrue(plateauResult.isEmpty());
    }

}
