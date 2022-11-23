package com.example.mowers.adapter;

import com.example.mowers.core.domain.Command;
import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.port.MowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MowerControllerImplTest {

    String invalidID = "invalidId";
    @Mock
    private MowerService service;
    @InjectMocks
    private MowerControllerImpl controller;
    private String plateauId = "plateauId";
    private Point position = new Point(10, 22);
    private Orientation orientation = Orientation.N;

    private int sizeX = 10, sizeY = 25;
    private String commands = "LLMRM";
    private List<Command> commandsList = Arrays.asList(Command.L, Command.L, Command.M, Command.R, Command.M);


    @BeforeEach
    void setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void givenController_thenCreateMower() {
        MowerDto mowerDto = new MowerDto(plateauId, position, orientation);
        Mower mowerResult = new Mower(mowerDto);
        when(service.createMower(mowerDto)).thenReturn(Optional.of(mowerResult));

        ResponseEntity<MowerDto> result = controller.createMower(mowerDto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.getHeaders().getLocation().getPath().contains(mowerResult.getId()));
        assertEquals(mowerDto, result.getBody());
    }

    @Test
    void givenController_whenServiceCannotCreateMower_thenCreateMowerConflict() {
        MowerDto mowerDto = new MowerDto(plateauId, position, orientation);
        when(service.createMower(mowerDto)).thenReturn(Optional.empty());

        ResponseEntity<MowerDto> result = controller.createMower(mowerDto);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    void givenController_whenMowerExists_thenGetMower() {
        Mower mower = new Mower(plateauId, position, orientation);

        when(service.getMower(mower.getId())).thenReturn(Optional.of(mower));

        ResponseEntity<MowerDto> result = controller.getMower(mower.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mower.getPlateauId(), result.getBody().getPlateauId());
        assertEquals(mower.getPosition(), result.getBody().getPosition());
        assertEquals(mower.getOrientation(), result.getBody().getOrientation());
    }

    @Test
    void givenController_whenMowerDoesNotExist_thenGetMowerNotFound() {
        when(service.getMower(invalidID)).thenReturn(Optional.empty());

        ResponseEntity<MowerDto> result = controller.getMower(invalidID);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void givenController_whenMowerDoesNotExist_thenMoveMowerNotFound() {
        when(service.getMower(invalidID)).thenReturn(Optional.empty());

        ResponseEntity<MowerDto> result = controller.moveMower(invalidID, commands);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void givenController_whenMowerDoesNotExist_thenMoveMowerOk() {
        Mower mower = new Mower(plateauId, position, orientation);

        when(service.getMower(mower.getId())).thenReturn(Optional.of(mower));
        when(service.moveMower(mower, commandsList)).thenReturn(Optional.of(mower));

        ResponseEntity<MowerDto> result = controller.moveMower(mower.getId(), commands);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mower.getPlateauId(), result.getBody().getPlateauId());
        assertEquals(mower.getPosition(), result.getBody().getPosition());
        assertEquals(mower.getOrientation(), result.getBody().getOrientation());
    }

    @Test
    void givenController_whenMowerFailsMoving_thenMoveMowerConflict() {
        Mower mower = new Mower(plateauId, position, orientation);

        when(service.getMower(mower.getId())).thenReturn(Optional.of(mower));
        when(service.moveMower(mower, commandsList)).thenReturn(Optional.empty());

        ResponseEntity<MowerDto> result = controller.moveMower(mower.getId(), commands);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    void givenController_whenMowerExist_thenMoveMowerOk() {
        Mower mower = new Mower(plateauId, position, orientation);

        when(service.getMower(mower.getId())).thenReturn(Optional.of(mower));
        when(service.moveMower(mower, commandsList)).thenReturn(Optional.of(mower));

        ResponseEntity<MowerDto> result = controller.moveMower(mower.getId(), commands);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mower.getPlateauId(), result.getBody().getPlateauId());
        assertEquals(mower.getPosition(), result.getBody().getPosition());
        assertEquals(mower.getOrientation(), result.getBody().getOrientation());
    }
}
