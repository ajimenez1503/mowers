package com.example.mowers.adapter;

import com.example.mowers.core.domain.Plateau;
import com.example.mowers.core.dto.PlateauDto;
import com.example.mowers.port.PlateauController;
import com.example.mowers.port.PlateauService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    final private PlateauService plateauService;
    final private ModelMapper modelMapper;

    @Override
    public ResponseEntity<String> createPlateau(PlateauDto plateauRequest) {
        Plateau plateau = modelMapper.map(plateauRequest, Plateau.class);
        Plateau plateauResult = plateauService.createPlateau(plateau);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(plateauResult.getId()).toUri();
        log.info("Created Plateau with ID {} and URL {} ", plateau.getId(), location);
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<PlateauDto> getPlateau(String id) {
        Optional<Plateau> plateau = plateauService.getPlateau(id);
        if (plateau.isPresent()) {
            PlateauDto plateauResult = modelMapper.map(plateau.get(), PlateauDto.class);
            log.info("Get Plateau {} ", plateauResult);
            return new ResponseEntity<>(plateauResult, HttpStatus.OK);
        } else {
            log.info("Plateau with ID {} not found ", id);
            return ResponseEntity.notFound().build();
        }
    }
}
