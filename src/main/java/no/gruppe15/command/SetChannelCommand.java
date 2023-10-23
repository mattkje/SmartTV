package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.Message;

public class SetChannelCommand extends Command{

    private String channel;
    public SetChannelCommand(String channel){
        this.channel = channel;

        //TODO Implement channel functionality
    }
    @Override
    public Message execute(TvLogic logic) {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
