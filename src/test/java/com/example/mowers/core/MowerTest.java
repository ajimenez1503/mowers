package com.example.mowers.core;

import com.example.mowers.core.domain.Command;
import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.dto.MowerDto;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MowerTest {

    private String plateauId = "plateauId";
    private Point position = new Point(10, 22);
    private Orientation orientation = Orientation.N;

    @Test
    void thenCreateMower() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        assertNotNull(mower.getId());
        assertEquals(plateauId, mower.getPlateauId());
        assertEquals(position, mower.getPosition());
        assertEquals(orientation, mower.getOrientation());
        assertEquals(mower, mower); // Different ID

        Mower mower2 = new Mower(plateauId, position, orientation);
        assertNotEquals(mower2, mower); // Different ID
    }

    @Test
    void givenMowerDto_thenCreateMower() {
        MowerDto mowerDto = new MowerDto(plateauId, position, orientation);
        assertNotNull(mowerDto);
        assertEquals(plateauId, mowerDto.getPlateauId());
        assertEquals(position, mowerDto.getPosition());
        assertEquals(orientation, mowerDto.getOrientation());

        Mower mower = new Mower(mowerDto);
        assertNotNull(mower.getId());
        assertEquals(plateauId, mower.getPlateauId());
        assertEquals(position, mower.getPosition());
        assertEquals(orientation, mower.getOrientation());
    }

    @Test
    void givenMower_getMowerDto() {
        MowerDto mowerDto = new MowerDto(plateauId, position, orientation);
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);

        MowerDto mowerDtoCopy = mower.getDto();
        assertEquals(mowerDtoCopy.getPlateauId(), mower.getPlateauId());
        assertEquals(mowerDtoCopy.getPosition(), mower.getPosition());
        assertEquals(mowerDtoCopy.getOrientation(), mower.getOrientation());
        assertEquals(mowerDto, mowerDtoCopy);
    }

    @Test
    void givenMower_whenCommandL_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        assertEquals(mower.getPosition(), mower.getNextPosition(Command.L));
    }

    @Test
    void givenMower_whenCommandR_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        assertEquals(mower.getPosition(), mower.getNextPosition(Command.R));
    }

    @Test
    void givenMowerWithOrientationN_whenCommandM_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, Orientation.N);
        assertNotNull(mower);
        assertEquals(new Point((int) position.getX(), (int) (position.getY() + 1)), mower.getNextPosition(Command.M));
    }

    @Test
    void givenMowerWithOrientationW_whenCommandM_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, Orientation.W);
        assertNotNull(mower);
        assertEquals(new Point((int) position.getX() - 1, (int) (position.getY())), mower.getNextPosition(Command.M));
    }

    @Test
    void givenMowerWithOrientationS_whenCommandM_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, Orientation.S);
        assertNotNull(mower);
        assertEquals(new Point((int) position.getX(), (int) (position.getY() - 1)), mower.getNextPosition(Command.M));
    }

    @Test
    void givenMowerWithOrientationE_whenCommandM_thenGetNextPosition() {
        Mower mower = new Mower(plateauId, position, Orientation.E);
        assertNotNull(mower);
        assertEquals(new Point((int) position.getX() + 1, (int) (position.getY())), mower.getNextPosition(Command.M));
    }

    @Test
    void givenMower_whenCommandM_thenExecute() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        mower.execute(Command.M);
        assertEquals(new Point((int) position.getX(), (int) (position.getY() + 1)), mower.getPosition());
        assertEquals(orientation, mower.getOrientation());
    }

    @Test
    void givenMower_whenCommandR_thenExecute() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        mower.execute(Command.R);
        assertEquals(position, mower.getPosition());
        assertEquals(Orientation.E, mower.getOrientation());
    }

    @Test
    void givenMower_whenCommandL_thenExecute() {
        Mower mower = new Mower(plateauId, position, orientation);
        assertNotNull(mower);
        mower.execute(Command.L);
        assertEquals(position, mower.getPosition());
        assertEquals(Orientation.W, mower.getOrientation());
    }
}
