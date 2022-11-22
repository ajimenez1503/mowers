package com.example.mowers.core.domain;

import com.example.mowers.core.dto.MowerDto;
import lombok.*;

import java.awt.*;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Mower {
    private final String id;
    private final String plateauId;
    private Point position;
    private Orientation orientation;

    private Mower(String mowerId, String plateauId, Point position, Orientation orientation) {
        this.id = mowerId;
        this.plateauId = plateauId;
        this.position = position;
        this.orientation = orientation;
    }

    public Mower(String plateauId, Point position, Orientation orientation) {
        this(UUID.randomUUID().toString(), plateauId, position, orientation);
    }

    public Mower(Mower mower) {
        this(mower.getId(), mower.getPlateauId(), mower.getPosition(), mower.getOrientation());
    }

    public Mower(MowerDto mowerDto) {
        this(mowerDto.getPlateauId(), mowerDto.getPosition(), mowerDto.getOrientation());
    }

    public MowerDto getDto() {
        return new MowerDto(this.getPlateauId(), this.getPosition(), this.getOrientation());
    }

    public Point getNextPosition(Command command) {
        Point position = new Point(this.getPosition());
        if (command == Command.M) {
            switch (this.orientation) {
                case N:
                    position.setLocation(position.getX(), position.getY() + 1);
                    break;
                case S:
                    position.setLocation(position.getX(), position.getY() - 1);
                    break;
                case E:
                    position.setLocation(position.getX() + 1, position.getY());
                    break;
                case W:
                    position.setLocation(position.getX() - 1, position.getY());
                    break;
            }
        }
        return position;
    }

    public void execute(Command command) {
        if (command.equals(Command.R) || command.equals(Command.L)) {
            this.setOrientation(this.getOrientation().getNextOrientation(command));
        } else if (command.equals(Command.M)) {
            this.setPosition(this.getNextPosition(command));
        }
    }
}
