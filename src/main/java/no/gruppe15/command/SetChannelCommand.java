package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;
import no.gruppe15.ui.SmartTVController;

/**
 * Command that changes the channel to a certain number.
 * Handles exceptions and sends out the correct message
 */
public class SetChannelCommand extends Command {

  private final String channel;

  public SetChannelCommand(String channel) {
    this.channel = channel;
  }

  @Override
  public Message execute(TvLogic logic, SmartTVController controller) {
    try {
      logic.setChannel(channelToInt());
      controller.setChannelMedia(channelToInt() + "");
      return new OkMessage("Channel now set to " + channelToInt());
    } catch (IllegalStateException e) {
      return new ErrorMessage("The TV must be turned on first");
    } catch (IllegalArgumentException e2) {
      return new ErrorMessage("This channel does not exist");
    }
  }

  @Override
  public String getMessage() {
    return null;
  }

  /**
   * Reformats the users input to a readable format.
   * Format: C (Change channel command) + # (Channel number to change to)
   * Handles wrong formats and gives feedback
   *
   * @return channel number as int
   */
  public int channelToInt() {
    if (channel.matches("c\\d+")) {
      return Integer.parseInt(channel.substring(1));
    } else {
      throw new IllegalArgumentException("Invalid channel format: " + channel);
    }
  }
}
