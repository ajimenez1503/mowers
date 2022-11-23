package com.example.mowers.core;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import com.example.mowers.core.impl.PlateauServiceImpl;
import com.example.mowers.port.PlateauRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlateauServiceImplTest {
    @Mock
    private PlateauRepo repo;

    @InjectMocks
    private PlateauServiceImpl service;

    private int sizeX = 10;
    private int upperRightXCoordinate = sizeX - 1;
    private int sizeY = 22;
    private int upperRightYCoordinate = sizeY - 1;

    @Test
    void givenService_thenCreatePlateau() {
        PlateauDto plateauDto = new PlateauDto(upperRightXCoordinate, upperRightYCoordinate);
        Plateau plateau = new Plateau(plateauDto);
        when(repo.createPlateau(plateauDto)).thenReturn(plateau);

        Optional<Plateau> plateauResult = service.createPlateau(plateauDto);
        assertTrue(plateauResult.isPresent());
        assertEquals(plateau, plateauResult.get());
    }

    @Test
    void givenService_thenGetPlateau() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        when(repo.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        Optional<Plateau> plateauResult = service.getPlateau(plateau.getId());
        assertEquals(Optional.of(plateau), plateauResult);
    }
}
