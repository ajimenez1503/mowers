package com.example.mowers.core.dto;

import lombok.*;

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
    private int sizeX;
    @NotNull
    @Positive
    private int sizeY;
}