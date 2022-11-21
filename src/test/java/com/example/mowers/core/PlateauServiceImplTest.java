package com.example.mowers.core;

import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.impl.PlateauServiceImpl;
import com.example.mowers.port.PlateauRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlateauServiceImplTest {
    @Mock
    private PlateauRepo repo;

    @InjectMocks
    private PlateauServiceImpl service;

    @Test
    public void givenService_thenCreatePlateau() {
        Plateau plateau = new Plateau("ID", 10, 22);
        when(repo.createPlateau(any())).thenReturn(plateau);

        Plateau plateauResult = service.createPlateau(plateau);
        assertEquals(plateau, plateauResult);
    }

    @Test
    public void givenService_thenGetPlateau() {
        Optional<Plateau> plateau = Optional.of(new Plateau("ID", 10, 22));
        when(repo.getPlateau(anyString())).thenReturn(plateau);

        Optional<Plateau> plateauResult = service.getPlateau("ID");
        assertEquals(plateau, plateauResult);
    }
}
