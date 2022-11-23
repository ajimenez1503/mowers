package com.example.mowers.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PlateauDto {
    @NotNull
    @Positive
    @NotEmpty
    @Schema(example = "20")
    private int sizeX;
    @NotNull
    @Positive
    @NotEmpty
    @Schema(example = "25")
    private int sizeY;
}