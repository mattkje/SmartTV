package no.gruppe15.message;

import no.gruppe15.TvLogic;

/**
 * A command requesting to turn on the TV.
 */
public class TurnOnCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    logic.turnOn();
    return new OkMessage();
  }
}
