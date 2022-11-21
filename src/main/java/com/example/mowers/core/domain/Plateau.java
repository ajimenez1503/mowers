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
    private int sizeX;
    private int sizeY;

    private boolean availability[][];

    public Plateau(int sizeX, int sizeY) {
        this.id = UUID.randomUUID().toString();
        this.sizeX = sizeX + 1;
        this.sizeY = sizeY + 1;
        this.availability = new boolean[this.sizeX][this.sizeY];
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
