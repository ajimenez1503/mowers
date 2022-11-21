package com.example.mowers.core.domain;

import lombok.*;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class Mower {
    private String id;
    private String plateauId;
    private Point pos;
    private Orientation orientation;
}
