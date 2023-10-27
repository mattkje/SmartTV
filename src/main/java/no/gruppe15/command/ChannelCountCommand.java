package no.gruppe15.command;

import no.gruppe15.tv.TvLogic;
import no.gruppe15.message.ChannelCountMessage;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.tv.gui.SmartTVController;

/**
 * A command asking for the number of the channels.
 */
public class ChannelCountCommand extends Command {
  @Override
  public Message execute(TvLogic logic, SmartTVController controller) {
    Message response;
    try {
      int channelCount = logic.getNumberOfChannels();
      controller.displayNumberOfChannels(channelCount);
      response = new ChannelCountMessage(channelCount);
    } catch (IllegalStateException e) {
      response = new ErrorMessage(e.getMessage());
    }
    return response;
  }

  @Override
  public String getMessage() {
    return "This command gets the amount of channels of a given SmartTV";
  }
}
