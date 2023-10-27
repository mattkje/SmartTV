package no.gruppe15.command;

import no.gruppe15.tv.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;
import no.gruppe15.tv.gui.SmartTVController;

/**
 * A command requesting to turn on the TV when executed.
 * Handles exceptions when TV is already turned on
 */
public class TurnOnCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    try {
      logic.turnOn();
      //controller.setChannelMedia("1");
      return new OkMessage("TV turned on successfully");
    } catch (IllegalStateException e) {
      return new ErrorMessage("The TV is already on");
    }

  }

}
