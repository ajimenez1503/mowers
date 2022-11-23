package com.example.mowers.adapter;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import com.example.mowers.port.PlateauService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlateauControllerImplTest {
    @Mock
    private PlateauService service;

    @InjectMocks
    private PlateauControllerImpl controller;
    private int sizeX = 10;
    private int upperRightXCoordinate = sizeX - 1;
    private int sizeY = 22;
    private int upperRightYCoordinate = sizeY - 1;

    @BeforeEach
    void setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void givenController_thenCreatePlateau() {
        PlateauDto plateauDto = new PlateauDto(upperRightXCoordinate, upperRightYCoordinate);
        Plateau plateauResult = new Plateau(plateauDto);
        when(service.createPlateau(plateauDto)).thenReturn(Optional.of(plateauResult));

        ResponseEntity<PlateauDto> result = controller.createPlateau(plateauDto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.getHeaders().getLocation().getPath().contains(plateauResult.getId()));
        assertEquals(plateauDto, result.getBody());
    }

    @Test
    void givenController_whenServiceCreatePlateauFail_thenCreatePlateauConflict() {
        PlateauDto plateauDto = new PlateauDto(upperRightXCoordinate, upperRightYCoordinate);
        when(service.createPlateau(plateauDto)).thenReturn(Optional.empty());

        ResponseEntity<PlateauDto> result = controller.createPlateau(plateauDto);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    void givenController_whenPlateauExist_thenGetPlateau() {
        Plateau plateau = new Plateau(sizeX, sizeY);

        when(service.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        ResponseEntity<PlateauDto> result = controller.getPlateau(plateau.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(upperRightXCoordinate, result.getBody().getUpperRightXCoordinate());
        assertEquals(upperRightYCoordinate, result.getBody().getUpperRightYCoordinate());
    }

    @Test
    void givenController_whenPlateauDoesNotExist_thenGetPlateauNotFound() {
        String invalidId = "invalidId";
        when(service.getPlateau(invalidId)).thenReturn(Optional.empty());

        ResponseEntity<PlateauDto> result = controller.getPlateau(invalidId);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

}
