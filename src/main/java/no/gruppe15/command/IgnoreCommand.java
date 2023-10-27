package no.gruppe15.command;

import no.gruppe15.tv.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.tv.gui.SmartTVController;

/**
 * Command that is sent when user input is to be ignored.
 * Returns "invalid command"
 */
public class IgnoreCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    return new ErrorMessage("Invalid command");
  }
}
