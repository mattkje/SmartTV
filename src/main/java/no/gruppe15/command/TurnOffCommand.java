package no.gruppe15.command;

import no.gruppe15.tv.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;
import no.gruppe15.tv.gui.SmartTVController;

/**
 * Command that turns of the TV when executed.
 * Handles exceptions and provides feedback
 */
public class TurnOffCommand extends Command {
  @Override
  public Message execute(TvLogic logic, SmartTVController controller) {
    try {
      logic.turnOff();
      controller.setChannelMedia("0");
      return new OkMessage("TV turned off successfully");
    } catch (IllegalStateException e) {
      return new ErrorMessage("The TV must be turned on first");
    }

  }

  @Override
  public String getMessage() {
    return "This command turns off the TV";
  }
}