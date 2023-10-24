package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;

/**
 * Command that turns the TV off when executed.
 * Handles exceptions when TV is already off.
 */
public class TurnOffCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    try {
      logic.turnOff();
      return new OkMessage("TV turned off successfully");
    } catch (IllegalStateException e) {
      return new ErrorMessage("The TV must be turned on first");
    }

  }

  public String getMessage() {
    return "This command turns off the TV";
    //TODO: DOUBLECHECK THIS LATER ON
  }
}