package no.gruppe15.message;

/**
 * A message containing the number of channels for the TV.
 */
public class ChannelCountMessage extends Message {
  private final int channelCount;

  public ChannelCountMessage(int channelCount) {
    this.channelCount = channelCount;
  }


  @Override
  public String getMessage() {
    return "Number of channels: " + this.channelCount;
  }
}
