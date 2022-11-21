package com.example.mowers.port;

import com.example.mowers.core.dto.PlateauDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/plateau")
@Tag(name = "Plateau API", description = "Manage the plateaus")
public interface PlateauController {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a plateau")
    ResponseEntity<String> createPlateau(@RequestBody PlateauDto plateau);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a plateau by ID")
    ResponseEntity<PlateauDto> getPlateau(@PathVariable String id);

}
