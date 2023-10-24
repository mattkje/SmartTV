package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;

/**
 * Command that sets the channel to a certain number.
 * Checks if the channel exists and handles exceptions if needed
 */
public class SetChannelCommand extends Command {

  private String channel;

  public SetChannelCommand(String channel) {
    this.channel = channel;
  }

  @Override
  public Message execute(TvLogic logic) {
    try {
      logic.setChannel(channelToInt());
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
   * Converts a message "C4" (Channel change command + channel number) into the correct format.
   * The user inputs C to indicate the action followed by a number to indicate a channel.
   * Handles exceptions when the format is of the wrong type.
   * Converts the number from string to integer.
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
