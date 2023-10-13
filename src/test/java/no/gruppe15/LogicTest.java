package no.gruppe15;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogicTest {

    @Test
    public void testTurnOn(){
        TVLogic logic = new TVLogic(10);
        logic.turnOn();
        assertTrue(logic.isTvOn());
    }

    @Test
    public void testTurnOffWhenTvIsOn(){
        TVLogic logic = new TVLogic(10);
        logic.turnOn();
        logic.turnOff();
        assertFalse(logic.isTvOn());
    }

    @Test
    public void testSetChannel(){
        //TODO
    }

    @Test
    public void testSetChannelFailsWhenTvIsOff(){
        //TODO
    }

    @Test
    public void testTrySetChannel(){
        //TODO
    }

    @Test
    public void something(){
        //TODO
    }
}
