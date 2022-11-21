package com.example.mowers.core;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.core.impl.MowerServiceImpl;
import com.example.mowers.port.MowerRepo;
import com.example.mowers.port.PlateauService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MowerServiceImplTest {

    @Mock
    private MowerRepo repo;

    @Mock
    private PlateauService plateauService;

    @InjectMocks
    private MowerServiceImpl service;

    @Test
    public void givenService_thenCreateMower() {
        Plateau plateau = new Plateau(20, 25);
        MowerDto mowerDto = new MowerDto(plateau.getId(), new Point(10, 22), Orientation.N);
        Mower mower = new Mower(mowerDto);

        when(plateauService.getPlateau(anyString())).thenReturn(Optional.of(plateau));
        when(repo.createMower(any())).thenReturn(mower);

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isPresent());
        assertEquals(mower, mowerResult.get());
    }

    @Test
    public void givenService_whenPlateauDoesNotExist_thenCreateMower() {
        MowerDto mowerDto = new MowerDto("IdPlateau", new Point(10, 22), Orientation.N);
        when(plateauService.getPlateau(anyString())).thenReturn(Optional.empty());

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isEmpty());
    }

    @Test
    public void givenService_thenGetMower() {
        Optional<Mower> mower = Optional.of(new Mower("IdPlateau", new Point(10, 22), Orientation.N));
        when(repo.getMower(anyString())).thenReturn(mower);

        Optional<Mower> mowerResult = service.getMower("IdMower");
        assertEquals(mower, mowerResult);
    }
}
