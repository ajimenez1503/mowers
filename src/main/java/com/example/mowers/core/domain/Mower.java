package com.example.mowers.core.domain;

import com.example.mowers.core.dto.MowerDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.Point;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Mower {
    private final String id;
    private final String plateauId;
    private Point position;
    private Orientation orientation;

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
