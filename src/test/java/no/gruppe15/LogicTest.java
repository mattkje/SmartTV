package no.gruppe15;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import no.gruppe15.tv.TvLogic;
import org.junit.Test;

/**
 * Test class for the TVLogic class.
 *
 */

public class LogicTest {


  /**
   * Ensures that the tv is off by default and test the turnOn() functionality.
   */
  @Test
  public void tvIsOffByDefaultThenOnWhenTurnedOn(){
    TvLogic logic = new TvLogic(1);
    assertFalse(logic.isTvOn());
    logic.turnOn();
    assertTrue(logic.isTvOn());
  }

  /**
   * Ensures that the user can not interact with the channel list when the TV is off.
   */
  @Test
  public void cantGetListOffChannelsWhenTvOffButCanWhenTvOn(){
    TvLogic logic = new TvLogic(2);
    assertThrows(IllegalStateException.class, logic::getNumberOfChannels);

    logic.turnOn();
    assertEquals(2,logic.getNumberOfChannels());

    // Trying to see if it changes after we turn it off again
    logic.turnOff();
    assertThrows(IllegalStateException.class, logic::getNumberOfChannels);
  }

  /**
   * Ensures that a TV can´t be created with zero channels.
   */
  @Test
  public void throwsExceptionWhenCreatingZeroChannels(){
    assertThrows(IllegalArgumentException.class, () -> new TvLogic(0));
  }

  /**
   * Ensures that the user can not change to a channel that does not exist.
   * This includes higher channel numbers and negative channel numbers
   */
  @Test
  public void changingToACorrectAndFalseChannel(){
    TvLogic logic = new TvLogic(2);
    logic.turnOn();

    logic.setChannel(2);
    assertEquals(2,logic.getCurrentChannel());


    assertThrows(IllegalArgumentException.class, () -> logic.setChannel(3));

    assertThrows(IllegalArgumentException.class, () -> logic.setChannel(-1));
  }
}
