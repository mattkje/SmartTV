package no.gruppe15.message;

import no.gruppe15.command.ChannelCountCommand;
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
    char firstS = s.charAt(0);
    switch (firstS) {
      case 'n':
        m = new ChannelCountCommand();
        break;
      case 'c':
        m = new SetChannelCommand(s);
        break;
      case '1':
        m = new TurnOnCommand();
        break;
      case '0':
        m = new TurnOffCommand();
        break;
      default:
        m = null;
    }
    return m;
  }

  public static String toString(Message response) {

    return response.getMessage();

  }
}
