package com.example.mowers.adapter;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import com.example.mowers.port.PlateauController;
import com.example.mowers.port.PlateauService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class PlateauControllerImpl implements PlateauController {

    private final PlateauService plateauService;

    @Override
    public ResponseEntity<PlateauDto> createPlateau(PlateauDto plateauRequest) {
        Optional<Plateau> plateauResult = plateauService.createPlateau(plateauRequest);
        if (plateauResult.isPresent()) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(plateauResult.get().getId()).toUri();
            log.info("Created Plateau with ID {}, accessible by URL {}", plateauResult.get().getId(), location);
            return ResponseEntity.created(location).body(plateauResult.get().getDto());
        } else {
            log.warn("The Plateau {} is not valid", plateauRequest);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Override
    public ResponseEntity<PlateauDto> getPlateau(String plateauId) {
        Optional<Plateau> plateau = plateauService.getPlateau(plateauId);
        if (plateau.isPresent()) {
            PlateauDto plateauResult = plateau.get().getDto();
            log.info("Get Plateau {}", plateauResult);
            return new ResponseEntity<>(plateauResult, HttpStatus.OK);
        } else {
            log.info("Plateau with ID {} not found", plateauId);
            return ResponseEntity.notFound().build();
        }
    }
}
