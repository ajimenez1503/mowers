package com.example.mowers.port;

import com.example.mowers.core.dto.PlateauDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequestMapping(value = "/plateau")
@Tag(name = "Plateau API", description = "Manage the plateaus")
@Validated
public interface PlateauController {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a plateau")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<PlateauDto> createPlateau(@RequestBody @Valid PlateauDto plateauRequest);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a plateau by ID")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PlateauDto> getPlateau(@PathVariable(value = "id") @Valid @NotBlank String plateauId);

}
