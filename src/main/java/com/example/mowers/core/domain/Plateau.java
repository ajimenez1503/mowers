package com.example.mowers.core.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Plateau {

    private String id;
    private int X;
    private int Y;
    private Orientation orientation;
}
