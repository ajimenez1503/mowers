package com.example.mowers.core.dto;

import com.example.mowers.core.domain.Orientation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.Point;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MowerDto {
    @NotEmpty
    @Schema(example = "b9406067-4d3e-4210-93d1-5db05e9b939d")
    private String plateauId;
    @NotNull
    private Point position;
    @NotNull
    @Schema(example = "N")
    private Orientation orientation;
}
