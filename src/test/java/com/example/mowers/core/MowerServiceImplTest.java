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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MowerServiceImplTest {

    @Mock
    private MowerRepo repo;

    @Mock
    private PlateauService plateauService;

    @InjectMocks
    private MowerServiceImpl service;

    private String plateauId = "plateauId";
    private Orientation orientation = Orientation.N;

    private int sizeX = 10, sizeY = 25;
    Point positionOutside = new Point(sizeX + 10, sizeY + 2);
    private Point point = new Point(sizeX - 1, sizeY - 2);

    @Test
    public void givenService_thenCreateMower() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        MowerDto mowerDto = new MowerDto(plateau.getId(), point, orientation);
        Mower mower = new Mower(mowerDto);

        when(plateauService.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));
        when(repo.createMower(mowerDto)).thenReturn(mower);

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isPresent());
        assertEquals(mower, mowerResult.get());
    }

    @Test
    public void givenService_whenPlateauDoesNotExist_thenCreateMower() {
        MowerDto mowerDto = new MowerDto(plateauId, point, orientation);
        when(plateauService.getPlateau(plateauId)).thenReturn(Optional.empty());

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isEmpty());
    }

    @Test
    public void givenService_thenGetMower() {
        Optional<Mower> mower = Optional.of(new Mower(plateauId, point, orientation));
        when(repo.getMower(any())).thenReturn(mower);

        Optional<Mower> mowerResult = service.getMower("IdMower");
        assertEquals(mower, mowerResult);
    }

    @Test
    public void givenService_whenMowerPositionOutside_thenCreateMowerFailure() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        MowerDto mowerDto = new MowerDto(plateau.getId(), positionOutside, orientation);

        when(plateauService.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isEmpty());
    }

    @Test
    public void givenService_whenMowerPositionBusy_thenCreateMowerFailure() throws Exception {
        Plateau plateau = new Plateau(sizeX, sizeY);
        plateau.setPositionBusy(point);
        MowerDto mowerDto = new MowerDto(plateau.getId(), point, orientation);

        when(plateauService.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isEmpty());
    }
}
