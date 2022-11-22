package com.example.mowers.adapter;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import com.example.mowers.port.PlateauService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlateauControllerImplTest {

    private PlateauService service;

    private ModelMapper modelMapper;
    private PlateauControllerImpl controller;
    private int sizeX = 10, sizeY = 25;

    @BeforeEach
    public void setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        service = mock(PlateauService.class);
        modelMapper = new ModelMapper();

        controller = new PlateauControllerImpl(service, modelMapper);
    }

    @Test
    public void givenController_thenCreatePlateau() {
        PlateauDto plateauDto = new PlateauDto(sizeX, sizeY);
        Plateau plateauResult = new Plateau(plateauDto);
        when(service.createPlateau(plateauDto)).thenReturn(Optional.of(plateauResult));

        ResponseEntity<String> result = controller.createPlateau(plateauDto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.getHeaders().getLocation().getPath().contains(plateauResult.getId()));
    }

    @Test
    public void givenController_whenPlateauExist_thenGetPlateau() {
        Plateau plateau = new Plateau(sizeX, sizeY);

        when(service.getPlateau(plateau.getId())).thenReturn(Optional.of(plateau));

        ResponseEntity<PlateauDto> result = controller.getPlateau(plateau.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(plateau.getSizeX(), result.getBody().getSizeX());
        assertEquals(plateau.getSizeY(), result.getBody().getSizeY());
    }

    @Test
    public void givenController_whenPlateauDoesNotExist_thenGetPlateauNotFound() {
        String invalidId = "invalidId";
        when(service.getPlateau(invalidId)).thenReturn(Optional.empty());

        ResponseEntity<PlateauDto> result = controller.getPlateau(invalidId);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

}