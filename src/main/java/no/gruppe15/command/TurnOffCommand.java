package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;

public class TurnOffCommand extends Command {
    @Override
    public Message execute(TvLogic logic) {
        logic.turnOff();
        return new OkMessage("TV turned off successfully");
    }
    public String getMessage(){
        return "This command turns off the TV";
        //TODO: DOUBLECHECK THIS LATER ON
    }
}