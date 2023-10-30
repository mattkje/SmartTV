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
      logic.toggleMute();
      return new OkMessage("Sound muted");
    } catch (IllegalStateException e) {
      return new ErrorMessage("The TV must be turned on");
    }
  }
}
