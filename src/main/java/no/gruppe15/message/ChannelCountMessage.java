package no.gruppe15.message;

/**
 * A message containing the number of channels for the TV.
 */
public class ChannelCountMessage extends Message {
  private final int channelCount;

  public ChannelCountMessage(int channelCount) {
    this.channelCount = channelCount;
  }

  public int getChannelCount() {
    return channelCount;
  }
}
