package no.gruppe15.command;

import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;
import no.gruppe15.tv.TvLogic;

/**
 * Command that mutes the media.
 * Handles exceptions and sends out the correct message
 */
public class ToggleMuteCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    try {
      if (logic.toggleMute()) {
        return new OkMessage("Sound muted");
      } else {
        return new OkMessage("Sound on");
      }

    } catch (IllegalStateException e) {
      return new ErrorMessage("The TV must be turned on");
    }
  }
}
