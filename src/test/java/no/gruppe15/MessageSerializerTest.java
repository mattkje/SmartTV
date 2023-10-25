package no.gruppe15;

import static junit.framework.Assert.assertTrue;

import no.gruppe15.command.ChannelCountCommand;
import no.gruppe15.command.IgnoreCommand;
import no.gruppe15.command.SetChannelCommand;
import no.gruppe15.command.TurnOffCommand;
import no.gruppe15.command.TurnOnCommand;
import no.gruppe15.message.Message;
import no.gruppe15.message.MessageSerializer;
import org.junit.Test;

/**
 * Test class for the MessageSerializer class
 */
public class MessageSerializerTest {

  /**
   * Ensure that each available input corresponds to the correct command
   *
   * n = ChannelCountCommand
   * c = SetChannelCommand
   * 1 = TurnOnCommand
   * 0 = TurnOffCommand
   * Default = IgnoreCommand
   *
   */
  @Test
  public void testInputIsOfCorrectCommandType(){
    Message message;

    message = MessageSerializer.fromString("n");
    assertTrue(message instanceof ChannelCountCommand);

    message = MessageSerializer.fromString("c");
    assertTrue(message instanceof SetChannelCommand);

    message = MessageSerializer.fromString("1");
    assertTrue(message instanceof TurnOnCommand);

    message = MessageSerializer.fromString("0");
    assertTrue(message instanceof TurnOffCommand);

    //Testing a random and not active command
    message = MessageSerializer.fromString("Chuck is da best");
    assertTrue(message instanceof IgnoreCommand);
  }
}

