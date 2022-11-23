package com.example.mowers.core;

import com.example.mowers.core.domain.Command;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MowerServiceImplTest {

    @Mock
    private MowerRepo repo;

    @Mock
    private PlateauService plateauService;

    @InjectMocks
    private MowerServiceImpl service;

    private String plateauId = "plateauId";
    private String invalidId = "invalidId";

    private Orientation orientation = Orientation.N;

    private int sizeX = 10, sizeY = 25;
    Point positionOutside = new Point(sizeX + 10, sizeY + 2);
    private Point position = new Point(sizeX - 5, sizeY - 6);

    private List<Command> commands = Arrays.asList(Command.L, Command.L, Command.M, Command.R, Command.M);

    @Test
    void givenService_thenCreateMower() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        MowerDto mowerDto = new MowerDto(plateau.getId(), position, orientation);
        Mower mower = new Mower(mowerDto);

        when(plateauService.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));
        when(repo.createMower(mowerDto)).thenReturn(mower);

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isPresent());
        assertEquals(mower, mowerResult.get());
    }

    @Test
    void givenService_whenPlateauDoesNotExist_thenCreateMower() {
        MowerDto mowerDto = new MowerDto(invalidId, position, orientation);
        when(plateauService.getPlateau(invalidId)).thenReturn(Optional.empty());

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isEmpty());
    }

    @Test
    void givenService_thenGetMower() {
        Optional<Mower> mower = Optional.of(new Mower(plateauId, position, orientation));
        when(repo.getMower(any())).thenReturn(mower);

        Optional<Mower> mowerResult = service.getMower("IdMower");
        assertEquals(mower, mowerResult);
    }

    @Test
    void givenService_whenMowerPositionOutside_thenCreateMowerFails() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        MowerDto mowerDto = new MowerDto(plateau.getId(), positionOutside, orientation);

        when(plateauService.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isEmpty());
    }

    @Test
    void givenService_whenMowerPositionBusy_thenCreateMowerFails() throws Exception {
        Plateau plateau = new Plateau(sizeX, sizeY);
        plateau.setPositionBusy(position);
        MowerDto mowerDto = new MowerDto(plateau.getId(), position, orientation);

        when(plateauService.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        Optional<Mower> mowerResult = service.createMower(mowerDto);
        assertTrue(mowerResult.isEmpty());
    }

    @Test
    void givenService_whenListCommandNull_thenMoveMowerFails() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        Mower mower = new Mower(plateau.getId(), position, orientation);
        Optional<Mower> mowerResult = service.moveMower(new Mower(mower), null);
        assertTrue(mowerResult.isEmpty());
    }

    @Test
    void givenService_whenPlateauDoesNotExist_thenCreateMowerFails() {
        Mower mower = new Mower(invalidId, position, orientation);
        when(plateauService.getPlateau(invalidId)).thenReturn(Optional.empty());

        Optional<Mower> mowerResult = service.moveMower(new Mower(mower), commands);
        assertTrue(mowerResult.isEmpty());
    }

    @Test
    void givenService_whenEmptyCommands_thenCreateMower() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        Mower mower = new Mower(plateau.getId(), position, orientation);
        when(plateauService.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        Optional<Mower> mowerResult = service.moveMower(new Mower(mower), new ArrayList<>());
        assertTrue(mowerResult.isPresent());
        assertEquals(mower, mowerResult.get());
    }

    @Test
    void givenService_whenListCommands_thenCreateMower() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        Mower mower = new Mower(plateau.getId(), position, orientation);
        when(plateauService.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        Optional<Mower> mowerResult = service.moveMower(new Mower(mower), commands);
        assertTrue(mowerResult.isPresent());
        mower.setOrientation(Orientation.W);
        mower.setPosition(new Point((int) (position.getX() - 1), (int) (position.getY() - 1)));
        assertEquals(mower, mowerResult.get());
    }

    @Test
    void givenService_whenMowerInTheBoundariesOfThePlateau_thenCreateMower() {
        Plateau plateau = new Plateau(sizeX, sizeY);
        Mower mower = new Mower(plateau.getId(), new Point(sizeX, sizeY), orientation);
        when(plateauService.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        Optional<Mower> mowerResult = service.moveMower(new Mower(mower), Arrays.asList(Command.M, Command.M));
        assertTrue(mowerResult.isPresent());
        assertEquals(mower, mowerResult.get());
    }

    @Test
    void givenMower_whenCommandL_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        assertEquals(mower.getPosition(), service.getNextPosition(mower, Command.L));
    }

    @Test
    void givenMower_whenCommandR_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        assertEquals(mower.getPosition(), service.getNextPosition(mower, Command.R));
    }

    @Test
    void givenMowerWithOrientationN_whenCommandM_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, Orientation.N);
        assertNotNull(mower);
        assertEquals(new Point((int) position.getX(), (int) (position.getY() + 1)), service.getNextPosition(mower, Command.M));
    }

    @Test
    void givenMowerWithOrientationW_whenCommandM_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, Orientation.W);
        assertNotNull(mower);
        assertEquals(new Point((int) position.getX() - 1, (int) (position.getY())), service.getNextPosition(mower, Command.M));
    }

    @Test
    void givenMowerWithOrientationS_whenCommandM_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, Orientation.S);
        assertNotNull(mower);
        assertEquals(new Point((int) position.getX(), (int) (position.getY() - 1)), service.getNextPosition(mower, Command.M));
    }

    @Test
    void givenMowerWithOrientationE_whenCommandM_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, Orientation.E);
        assertNotNull(mower);
        assertEquals(new Point((int) position.getX() + 1, (int) (position.getY())), service.getNextPosition(mower, Command.M));
    }

    @Test
    void givenMower_whenCommandM_thenExecute() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        service.execute(mower, Command.M);
        assertEquals(new Point((int) position.getX(), (int) (position.getY() + 1)), mower.getPosition());
        assertEquals(orientation, mower.getOrientation());
    }

    @Test
    void givenMower_whenCommandR_thenExecute() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        service.execute(mower, Command.R);
        assertEquals(position, mower.getPosition());
        assertEquals(Orientation.E, mower.getOrientation());
    }

    @Test
    void givenMower_whenCommandL_thenExecute() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        service.execute(mower, Command.L);
        assertEquals(position, mower.getPosition());
        assertEquals(Orientation.W, mower.getOrientation());
    }

}
