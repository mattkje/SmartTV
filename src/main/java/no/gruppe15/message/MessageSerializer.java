package no.gruppe15.message;


import no.gruppe15.command.ChannelCountCommand;
import no.gruppe15.command.IgnoreCommand;
import no.gruppe15.command.SetChannelCommand;
import no.gruppe15.command.TurnOffCommand;
import no.gruppe15.command.TurnOnCommand;

/**
 * Serializes messages to protocol-defined strings and vice versa.
 */
public class MessageSerializer {
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
      default -> new IgnoreCommand(); //TODO Håkon fix
    };
    return m;
  }

  /**
   * returns the message received as a string.
   *
   * @param response Message object to be converted
   * @return converted Message object
   */
  public static String toString(Message response) {

    return response.getMessage();

  }
}
