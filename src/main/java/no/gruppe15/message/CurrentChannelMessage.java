package no.gruppe15.message;

/**
 * A message reporting the current channel of a TV.
 */
public record CurrentChannelMessage(int channel) implements Message {
  /**
   * Create a message which reports the current channel of a TV.
   *
   * @param channel The current channel of the TV
   */
  public CurrentChannelMessage {
  }

  /**
   * Get the current channel of the TV.
   *
   * @return The current channel of the TV
   */
  @Override
  public int channel() {
    return channel;
  }
}
