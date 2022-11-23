package com.example.mowers.core;

import com.example.mowers.core.domain.Command;
import com.example.mowers.core.domain.Orientation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrientationTest {

    @Test
    void givenOrientationN_thenChangeOrientation() {
        Orientation orientation = Orientation.N;
        assertEquals(Orientation.W, orientation.getNextOrientation(Command.L));
        assertEquals(Orientation.E, orientation.getNextOrientation(Command.R));
    }

    @Test
    void givenOrientationW_thenChangeOrientation() {
        Orientation orientation = Orientation.W;
        assertEquals(Orientation.S, orientation.getNextOrientation(Command.L));
        assertEquals(Orientation.N, orientation.getNextOrientation(Command.R));
    }

    @Test
    void givenOrientationS_thenChangeOrientation() {
        Orientation orientation = Orientation.S;
        assertEquals(Orientation.E, orientation.getNextOrientation(Command.L));
        assertEquals(Orientation.W, orientation.getNextOrientation(Command.R));
    }

    @Test
    void givenOrientationE_thenChangeOrientation() {
        Orientation orientation = Orientation.E;
        assertEquals(Orientation.N, orientation.getNextOrientation(Command.L));
        assertEquals(Orientation.S, orientation.getNextOrientation(Command.R));
    }

    @Test
    void givenOrientationN_whenCommandM_thenChangeOrientation() {
        Orientation orientation = Orientation.N;
        assertEquals(orientation, orientation.getNextOrientation(Command.M));
    }
}
