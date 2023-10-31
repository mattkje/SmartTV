package no.gruppe15.command;

import no.gruppe15.message.CurrentChannelMessage;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.tv.TvLogic;

/**
 * A command requesting to know the current channel of a TV.
 */
public class GetChannelCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    Message response;
    try {
      int channel = logic.getCurrentChannel();
      response = new CurrentChannelMessage(channel);
    } catch (Exception e) {
      response = new ErrorMessage(e.getMessage());
    }
    return response;
  }
}
