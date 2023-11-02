package no.gruppe15.message;

import no.gruppe15.command.ChannelCountCommand;
import no.gruppe15.command.ChannelDownCommand;
import no.gruppe15.command.ChannelUpCommand;
import no.gruppe15.command.IgnoreCommand;
import no.gruppe15.command.SetChannelCommand;
import no.gruppe15.command.ToggleMuteCommand;
import no.gruppe15.command.TurnOffCommand;
import no.gruppe15.command.TurnOnCommand;

/**
 * Serializes messages to protocol-defined strings and vice versa.
 *
 * @author Girts Strazdins
 * @see <a href="https://github.com/strazdinsg/datakomm-tools/tree/master" target="_blank">External Repository</a>
 */
public class MessageSerializer {

  public static final String CHANNEL_COUNT_COMMAND = "n";
  public static final String CHANNEL_UP_COMMAND = "+";
  public static final String CHANNEL_DOWN_COMMAND = "-";
  public static final String TURN_ON_COMMAND = "1";
  public static final String TURN_OFF_COMMAND = "0";
  public static final String SET_CHANNEL_COMMAND = "c";
  public static final String TOGGLE_MUTE_COMMAND = "m";

  /**
   * Create message from a string, according to the communication protocol.
   *
   * @param s The string sent over the communication channel
   * @return The logical message, as interpreted according to the protocol
   */
  public static Message fromString(String s) {
    if (s.isEmpty() || s.equals("null")) {
      return new IgnoreCommand();
    }
    char firstS = s.charAt(0);
    return switch (firstS) {
      case 'n' -> new ChannelCountCommand();
      case 'c' -> new SetChannelCommand(s);
      case '1' -> new TurnOnCommand();
      case '0' -> new TurnOffCommand();
      case '+' -> new ChannelUpCommand();
      case '-' -> new ChannelDownCommand();
      case 'm' -> new ToggleMuteCommand();
      default -> new IgnoreCommand();
    };
  }

  /**
   * Returns the command as a string.
   *
   * @param m message to be sent
   * @return command as message
   */
  public static String toString(Message m) {
    String s = null;
    if (m instanceof TurnOffCommand) {
      s = TURN_OFF_COMMAND;
    } else if (m instanceof TurnOnCommand) {
      s = TURN_ON_COMMAND;
    } else if (m instanceof ChannelCountCommand) {
      s = CHANNEL_COUNT_COMMAND;
    } else if (m instanceof ChannelUpCommand) {
      s = CHANNEL_UP_COMMAND;
    } else if (m instanceof ChannelDownCommand) {
      s = CHANNEL_DOWN_COMMAND;
    } else if (m instanceof ToggleMuteCommand) {
      s = TOGGLE_MUTE_COMMAND;
    } else if (m instanceof SetChannelCommand setChannelCommand) {
      s = SET_CHANNEL_COMMAND + setChannelCommand.getChannel();
    }
    return s;
  }
}
