package no.gruppe15.message;


import no.gruppe15.command.*;

/**
 * Serializes messages to protocol-defined strings and vice versa.
 *
 * TODO: Rewrite as to not be a "copy"
 */
public class MessageSerializer {

  public static final String CHANNEL_COUNT_COMMAND = "c";
  public static final String TURN_ON_COMMAND = "1";
  public static final String TURN_OFF_COMMAND = "0";
  public static final String GET_CHANNEL_COMMAND = "g";
  public static final String SET_CHANNEL_COMMAND = "c";
  public static final String CHANNEL_COUNT_MESSAGE = "N";
  public static final String ERROR_MESSAGE = "e";
  public static final String CURRENT_CHANNEL_MESSAGE = "s";
  public static final String TV_STATE_ON_MESSAGE = "TVON";
  public static final String TV_STATE_OFF_MESSAGE = "TVoff";
  /**
   * Create message from a string, according to the communication protocol.
   *
   * @param s The string sent over the communication channel
   * @return The logical message, as interpreted according to the protocol
   */
  public static Message fromString(String s) {
    Message m;
    if (s.isEmpty()) {
      return new IgnoreCommand();
    }
    char firstS = s.charAt(0);
    m = switch (firstS) {
      case 'n' -> new ChannelCountCommand();
      case 'c' -> new SetChannelCommand(s);
      case '1' -> new TurnOnCommand();
      case '0' -> new TurnOffCommand();
      case '+' -> new ChannelUpCommand();
      case '-' -> new ChannelDownCommand();
      default -> new IgnoreCommand();
    };
    return m;
  }

  public static String toString(Message m) {
    String s = null;
    if (m instanceof TurnOffCommand) {
      s = TURN_OFF_COMMAND;
    } else if (m instanceof TurnOnCommand) {
      s = TURN_ON_COMMAND;
    } else if (m instanceof ChannelCountCommand) {
      s = CHANNEL_COUNT_COMMAND;
    } else if (m instanceof GetChannelCommand) {
      s = GET_CHANNEL_COMMAND;
    } else if (m instanceof ChannelCountMessage channelCountMessage) {
      s = CHANNEL_COUNT_MESSAGE + channelCountMessage.getChannelCount();
    } else if (m instanceof CurrentChannelMessage currentChannelMessage) {
      s = CURRENT_CHANNEL_MESSAGE + currentChannelMessage.getChannel();
    } else if (m instanceof ErrorMessage errorMessage) {
      s = ERROR_MESSAGE + errorMessage.getMessage();
    } else if (m instanceof TvStateMessage tvStateMessage) {
      s = tvStateMessage.isOn() ? TV_STATE_ON_MESSAGE : TV_STATE_OFF_MESSAGE;
    } else if (m instanceof SetChannelCommand setChannelCommand) {
      s = SET_CHANNEL_COMMAND + setChannelCommand.getChannel();
    }
    return s;
  }
}
