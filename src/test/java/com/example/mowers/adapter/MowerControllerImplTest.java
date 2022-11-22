package com.example.mowers.adapter;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.port.MowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.awt.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MowerControllerImplTest {

    private MowerService service;

    private ModelMapper modelMapper;
    private MowerControllerImpl controller;

    private String plateauId = "plateauId";
    private Point point = new Point(10, 22);
    private Orientation orientation = Orientation.N;

    private int sizeX = 10, sizeY = 25;

    @BeforeEach
    public void setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        service = mock(MowerService.class);
        modelMapper = new ModelMapper();

        controller = new MowerControllerImpl(service, modelMapper);
    }

    @Test
    public void givenController_thenCreateMower() {
        MowerDto mowerDto = new MowerDto(plateauId, point, orientation);
        Mower mowerResult = new Mower(mowerDto);
        when(service.createMower(mowerDto)).thenReturn(Optional.of(mowerResult));

        ResponseEntity<String> result = controller.createMower(mowerDto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.getHeaders().getLocation().getPath().contains(mowerResult.getId()));
    }

    @Test
    public void givenController_whenServiceCannotCreateMower_thenCreateMowerConflict() {
        MowerDto mowerDto = new MowerDto(plateauId, point, orientation);
        when(service.createMower(mowerDto)).thenReturn(Optional.empty());

        ResponseEntity<String> result = controller.createMower(mowerDto);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    public void givenController_whenMowerExist_thenGetMower() {
        Mower mower = new Mower(plateauId, point, orientation);

        when(service.getMower(mower.getId())).thenReturn(Optional.of(mower));

        ResponseEntity<MowerDto> result = controller.getMower(mower.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mower.getPlateauId(), result.getBody().getPlateauId());
        assertEquals(mower.getPosition(), result.getBody().getPosition());
        assertEquals(mower.getOrientation(), result.getBody().getOrientation());
    }

    @Test
    public void givenController_whenMowerDoesNotExist_thenGetMowerNotFound() {
        String invalidID = "invalidId";
        when(service.getMower(invalidID)).thenReturn(Optional.empty());

        ResponseEntity<MowerDto> result = controller.getMower(invalidID);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
