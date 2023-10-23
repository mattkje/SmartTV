package no.gruppe15.command;

import no.gruppe15.TvLogic;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.OkMessage;

public class SetChannelCommand extends Command{

    private String channel;
    public SetChannelCommand(String channel){
        this.channel = channel;
    }
    @Override
    public Message execute(TvLogic logic) {
        try {
            logic.setChannel(channelToInt());
            return new OkMessage("Channel now set to " + channelToInt());
        } catch (IllegalStateException e){
            return new ErrorMessage("The TV must be turned on first");
        } catch (IllegalArgumentException e2){
            return new ErrorMessage("This channel does not exist");
        }
    }

    @Override
    public String getMessage() {
        return null;
    }

    public int channelToInt(){
        if (channel.matches("c\\d+")) {
            return Integer.parseInt(channel.substring(1));
        } else {
            throw new IllegalArgumentException("Invalid channel format: " + channel);
        }
    }
}
