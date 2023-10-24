package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;

public class IgnoreCommand extends Command{
    @Override
    public Message execute(TvLogic logic) {
        return new ErrorMessage("Invalid command");
    }
    public String getMessage(){
        return "";
        //TODO: DOUBLE CHECK THIS LATER ON
    }
}
