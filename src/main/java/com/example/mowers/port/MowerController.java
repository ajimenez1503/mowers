package com.example.mowers.port;

import com.example.mowers.core.dto.MowerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/mower")
@Tag(name = "Mower API", description = "Manage the mowers")
public interface MowerController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a mower")
    ResponseEntity<String> createMower(@RequestBody MowerDto mowerRequest);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a mower by ID")
    ResponseEntity<MowerDto> getMower(@PathVariable String id);
}
