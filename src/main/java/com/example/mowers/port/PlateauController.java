package com.example.mowers.port;

import com.example.mowers.core.dto.PlateauDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/plateau")
public interface PlateauController {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> createPlateau(@RequestBody PlateauDto plateau);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PlateauDto> getPlateau(@PathVariable String id);

}
