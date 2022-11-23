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

    private int sizeX = 10, sizeY = 22;

    @Test
    void givenService_thenCreatePlateau() {
        PlateauDto plateauDto = new PlateauDto(sizeX, sizeY);
        Plateau plateau = new Plateau(plateauDto);
        when(repo.createPlateau(plateauDto)).thenReturn(plateau);

        Optional<Plateau> plateauResult = service.createPlateau(plateauDto);
        assertTrue(plateauResult.isPresent());
        assertEquals(plateau, plateauResult.get());
    }

    @Test
    void givenService_thenGetPlateau() {
        Optional<Plateau> plateau = Optional.of(new Plateau(sizeX, sizeY));
        when(repo.getPlateau(plateau.get().getId())).thenReturn(plateau);

        Optional<Plateau> plateauResult = service.getPlateau(plateau.get().getId());
        assertEquals(plateau, plateauResult);
    }
}
