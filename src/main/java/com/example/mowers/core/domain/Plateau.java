package com.example.mowers.core.domain;

import com.example.mowers.core.dto.PlateauDto;
import lombok.*;

import java.awt.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Plateau {
    private String id;

    private boolean availability[][];
    private int sizeX;
    private int sizeY;

    public Plateau(int sizeX, int sizeY) {
        this.id = UUID.randomUUID().toString();
        this.availability = new boolean[this.sizeX][this.sizeY];
        this.sizeX = sizeX + 1;
        this.sizeY = sizeY + 1;
    }

    public Plateau(PlateauDto plateauDto) {
        this(plateauDto.getSizeX(), plateauDto.getSizeY());
    }

    public boolean isValidPosition(Point pos) {
        if (pos.getX() > this.sizeX || pos.getY() > this.sizeY) {
            return false;
        }
        return true;
    }
}
