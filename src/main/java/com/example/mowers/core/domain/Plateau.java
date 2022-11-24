package com.example.mowers.core.domain;

import com.example.mowers.core.dto.PlateauDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.awt.Point;
import java.util.Arrays;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class Plateau {
    private final String id;

    private final int sizeX;
    private final int sizeY;
    private Availability[][] availability;

    public Plateau(int sizeX, int sizeY) {
        this.id = UUID.randomUUID().toString();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.availability = new Availability[this.sizeX][this.sizeY];
        Arrays.stream(this.availability).forEach(a -> Arrays.fill(a, Availability.FREE));
    }

    public Plateau(PlateauDto plateauDto) {
        this(plateauDto.getUpperRightXCoordinate() + 1, plateauDto.getUpperRightYCoordinate() + 1);
    }

    public PlateauDto getDto() {
        return new PlateauDto(this.sizeX - 1, this.sizeY - 1);
    }

    public void setPositionFree(Point position) throws Exception {
        if (isValidPosition(position)) {
            this.availability[position.x][position.y] = Availability.FREE;
        } else {
            throw new Exception("Position (" + position.getX() + ", " + position.getY() + ") is not valid");
        }
    }

    public void setPositionBusy(Point position) throws Exception {
        if (isValidPosition(position)) {
            this.availability[position.x][position.y] = Availability.BUSY;
        } else {
            throw new Exception("Position (" + position.getX() + ", " + position.getY() + ") is not valid");
        }
    }

    public boolean isValidPosition(Point position) {
        return position.getX() < this.sizeX
                && position.getY() < this.sizeY
                && position.getX() >= 0
                && position.getY() >= 0;
    }

    public boolean isPositionAvailable(Point position) {
        return this.availability[position.x][position.y] == Availability.FREE;
    }
}
