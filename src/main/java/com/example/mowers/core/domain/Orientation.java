package com.example.mowers.core.domain;

import java.util.Map;

public enum Orientation {
    N,
    W,
    S,
    E;

    private static final Map<Integer, Orientation> map = Map.of(0, N, 1, W, 2, S, 3, E);

    public Orientation getNextOrientation(Command command) {
        if (command == Command.L) {
            return map.get((this.ordinal() + 1) % Orientation.values().length);
        } if (command == Command.R) {
            return map.get((this.ordinal() - 1 + Orientation.values().length) % Orientation.values().length);
        }
        return this;
    }

}
