package com.example.mowers.core.dto;

import com.example.mowers.core.domain.Orientation;
import lombok.*;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MowerDto {
    private String plateauId;
    private Point pos;
    private Orientation orientation;
}
