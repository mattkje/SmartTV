package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;
import no.gruppe15.ui.SmartTVController;

/**
 * A command requesting to turn on the TV.
 */
public class TurnOnCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    try {
      logic.turnOn();
      return new OkMessage("TV turned on successfully");
    } catch (IllegalStateException e){
      return new ErrorMessage("The TV is already on");
    }

  }
  public String getMessage(){
    return "This command turns the TV on";
    //TODO: DOUBLE CHECK THIS LATER ON
  }

}
