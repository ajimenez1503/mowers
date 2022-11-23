package com.example.mowers.core.domain;

import com.example.mowers.core.dto.MowerDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
