package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.ChannelCountMessage;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;

/**
 * A command asking for the number of the channels.
 */
public class ChannelCountCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    Message response;
    try {
      int channelCount = logic.getNumberOfChannels();
      response = new ChannelCountMessage(channelCount);
    } catch (IllegalStateException e) {
      response = new ErrorMessage(e.getMessage());
    }
    return response;
  }
  public String getMessage(){
    return "";
    //TODO: IMPLEMENT THIS
  }
}