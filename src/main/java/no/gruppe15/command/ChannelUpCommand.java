package no.gruppe15.command;

import no.gruppe15.tv.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;

public class ChannelUpCommand extends Command {

  @Override
  public Message execute(TvLogic logic) {
    try {
      logic.setChannel(logic.getCurrentChannel() + 1);
      return new OkMessage("Channel now set to " + logic.getCurrentChannel());
    } catch (IllegalStateException e) {
      return new ErrorMessage("The TV must be turned on first");
    } catch (IllegalArgumentException e2) {
      return new ErrorMessage("This channel does not exist");
    }
  }
}
