package com.example.mowers.adapter;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlateauRepoImplTest {

    private PlateauRepoImpl repo = new PlateauRepoImpl();

    private int sizeX = 10, sizeY = 22;

    @Test
    void givenRepo_thenCreatePlateau() {
        PlateauDto plateau = new PlateauDto(sizeX, sizeY);
        Plateau plateauResult = repo.createPlateau(plateau);
        assertNotNull(plateauResult);
        assertNotNull(plateauResult.getId());
        assertEquals(plateau.getSizeX() + 1, plateauResult.getSizeX());
        assertEquals(plateau.getSizeY() + 1, plateauResult.getSizeY());
    }

    @Test
    void givenRepo_whenCreatePlateau_thenGetThePlateau() {
        PlateauDto plateau = new PlateauDto(sizeX, sizeY);
        Plateau plateauCreated = repo.createPlateau(plateau);
        assertNotNull(plateauCreated);

        Optional<Plateau> plateauResult = repo.getPlateau(plateauCreated.getId());
        assertTrue(plateauResult.isPresent());
        assertEquals(plateauCreated, plateauResult.get());
    }

    @Test
    void givenRepo_whenGetInvalidPlateau_thenOptionalIsEmpty() {
        Optional<Plateau> plateauResult = repo.getPlateau("InvalidID");
        assertTrue(plateauResult.isEmpty());
    }

}
