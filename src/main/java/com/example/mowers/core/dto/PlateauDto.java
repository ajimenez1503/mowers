package com.example.mowers.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PlateauDto {
    @NotNull
    @Min(0)
    @Schema(example = "20")
    private int upperRightXCoordinate;
    @NotNull
    @Min(0)
    @Schema(example = "25")
    private int upperRightYCoordinate;
}