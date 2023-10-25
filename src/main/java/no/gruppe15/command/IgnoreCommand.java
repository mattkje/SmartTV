package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;
import no.gruppe15.ui.SmartTVController;

/**
 * Command that is sent when user input is to be ignored.
 * Returns "invalid command"
 */
public class IgnoreCommand extends Command {
  @Override
  public Message execute(TvLogic logic, SmartTVController controller) {
    return new ErrorMessage("Invalid command");
  }

  public String getMessage() {
    return "";
    //TODO: DOUBLE CHECK THIS LATER ON
  }
}
