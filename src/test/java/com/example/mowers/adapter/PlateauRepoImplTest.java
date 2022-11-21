package com.example.mowers.adapter;

import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.domain.Plateau;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PlateauRepoImplTest {

    private PlateauRepoImpl repo = new PlateauRepoImpl();

    @Test
    void givenRepo_thenCreatePlateau() {
        Plateau plateau = new Plateau("", 10, 22);
        Plateau plateauResult = repo.createPlateau(plateau);
        assertNotNull(plateauResult);
        assertNotEquals(plateau.getId(), plateauResult.getId());
        assertEquals(plateau.getX(), plateauResult.getX());
        assertEquals(plateau.getY(), plateauResult.getY());
    }

    @Test
    void givenRepo_whenCreatePlateau_thenGetThePlateau() {
        Plateau plateau = new Plateau("", 10, 22);
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
