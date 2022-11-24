package com.example.mowers.core.domain;

import java.util.Map;

public enum Orientation {
    N,
    W,
    S,
    E;

    private static final Map<Integer, Orientation> map =
            Map.of(N.ordinal(), N, W.ordinal(), W, S.ordinal(), S, E.ordinal(), E);

    public Orientation getNextOrientation(Command command) {
        if (command == Command.L) {
            return map.get((this.ordinal() + 1) % Orientation.values().length);
        }
        if (command == Command.R) {
            return map.get((this.ordinal() - 1 + Orientation.values().length) % Orientation.values().length);
        }
        return this;
    }

}
