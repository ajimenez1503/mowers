package com.example.mowers.core.dto;

import com.example.mowers.core.domain.Orientation;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MowerDto {
    @NotEmpty
    private String plateauId;
    @NotNull
    private Point position;
    @NotNull
    private Orientation orientation;
}
