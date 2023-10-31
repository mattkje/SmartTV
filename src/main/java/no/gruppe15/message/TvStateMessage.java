package no.gruppe15.message;

/**
 * A message telling whether the TV is ON or off.
 */
public record TvStateMessage(boolean isOn) implements Message {
  /**
   * Create a TV state message.
   *
   * @param isOn The TV is ON if this is true, the TV is off if this is false.
   */
  public TvStateMessage {
  }

  /**
   * Check whether the TV is ON.
   *
   * @return ON if true, OFF if false
   */
  @Override
  public boolean isOn() {
    return isOn;
  }

}
