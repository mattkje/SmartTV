package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;

/**
 * A command requesting to turn on the TV.
 */
public class TurnOnCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    logic.turnOn();
    return new OkMessage("TV turned on successfully");
  }
  public String getMessage(){
    return "";
    //TODO: IMPLEMENT THIS
  }
}
