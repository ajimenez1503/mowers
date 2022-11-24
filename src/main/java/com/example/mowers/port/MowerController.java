package com.example.mowers.port;

import com.example.mowers.core.dto.MowerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RequestMapping(value = "/mower")
@Tag(name = "Mower API", description = "Manage the mowers")
@Validated
public interface MowerController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a mower")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<MowerDto> createMower(@RequestBody @Valid MowerDto mowerRequest);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a mower by ID")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<MowerDto> getMower(@PathVariable(value = "id") @NotBlank String mowerId);

    /**
     * Navigate the mower in the plateau.
     *
     * @param mowerId  The ID of the mower to move
     * @param commands A series of instructions telling the mower how to explore the plateau
     * @return The current status of the mower
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Move a mower with ID")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<MowerDto> moveMower(
            @PathVariable(value = "id") @NotBlank String mowerId,
            @RequestBody @NotBlank @Pattern(regexp = "[LMR]*") String commands);
}
