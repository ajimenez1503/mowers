package com.example.mowers.core.domain;

import com.example.mowers.core.dto.MowerDto;
import lombok.*;

import java.awt.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Mower {
    private String id;
    private String plateauId;
    private Point position;
    private Orientation orientation;

    public Mower(String plateauId, Point position, Orientation orientation) {
        this.id = UUID.randomUUID().toString();
        this.plateauId = plateauId;
        this.position = position;
        this.orientation = orientation;
    }

    public Mower(MowerDto mowerDto) {
        this(mowerDto.getPlateauId(), mowerDto.getPosition(), mowerDto.getOrientation());
    }

    public MowerDto getDto() {
        return new MowerDto(this.getPlateauId(), this.getPosition(), this.getOrientation());
    }
}
