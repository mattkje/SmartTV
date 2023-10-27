package no.gruppe15.command;

import no.gruppe15.tv.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;
import no.gruppe15.tv.gui.SmartTVController;

public class ChannelUpCommand extends Command {

  @Override
  public Message execute(TvLogic logic, SmartTVController controller) {
    try {
      logic.setChannel(logic.getCurrentChannel() + 1);
      controller.setChannelMedia(logic.getCurrentChannel() + "");
      return new OkMessage("Channel now set to " + logic.getCurrentChannel());
    } catch (IllegalStateException e) {
      return new ErrorMessage("The TV must be turned on first");
    } catch (IllegalArgumentException e2) {
      return new ErrorMessage("This channel does not exist");
    }
  }

  @Override
  public String getMessage() {
    return "This command changes the channel by 1";
  }
}
