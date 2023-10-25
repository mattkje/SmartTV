package no.gruppe15;

/**
 * Smart TV - the logic.
 */
public class TvLogic {
  private boolean isTvOn;
  private final int numberOfChannels;
  private int currentChannel;

  /**
   * Create a new Smart TV.
   *
   * @param numberOfChannels The total number of channels the TV has
   */
  public TvLogic(int numberOfChannels) {
    if (numberOfChannels < 1) {
      throw new IllegalArgumentException("Number of channels must be positive");
    }

    this.numberOfChannels = numberOfChannels;
    isTvOn = false;
    currentChannel = 1;
  }

  /**
   * Turn ON the TV.
   */
  public void turnOn() {
    if (isTvOn) {
      throw new IllegalStateException("The TV is already on");
    }
    isTvOn = true;
  }

  /**
   * Turn OFF the TV.
   */
  public void turnOff() {
    if (!isTvOn) {
      throw new IllegalStateException("The TV must be turned on first");
    }
    isTvOn = false;
  }

  /**
   * Check whether the TV is ON or OFF.
   *
   * @return True when the TV is ON, false when OFF.
   */
  public boolean isTvOn() {
    return isTvOn;
  }

  /**
   * Get the number of channels this TV has.
   *
   * @return The total number of channels
   * @throws IllegalStateException When the TV is OFF
   */
  public int getNumberOfChannels() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("The TV must be turned on first");
    }
    return numberOfChannels;
  }

  /**
   * Get the current channel of the TV.
   *
   * @return The current channel
   * @throws IllegalStateException When the TV is OFF
   */
  public int getCurrentChannel() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("The TV must be turned on first");
    }
    return currentChannel;
  }

  /**
   * Set the channel for the TV.
   *
   * @param channel The desired channel
   * @throws IllegalArgumentException When the channel number is invalid
   * @throws IllegalStateException    When the TV is OFF
   */
  public void setChannel(int channel) throws IllegalArgumentException, IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("The TV must be turned on first");
    }
    if (channel <= 0 || channel > numberOfChannels) {
      throw new IllegalArgumentException("Invalid channel number");
    }
    currentChannel = channel;
  }

  /**
   * Changes the channel by x amount.
   * Receives a number and changes the channel if it exists
   * Negative numbers move the channel down
   *
   * @param amount the amount of channels to move up or down
   * @throws IllegalStateException    When the TV is OFF
   * @throws IllegalArgumentException When the channel number is invalid
   */

  public void nextChannel(int amount) throws IllegalStateException, IllegalArgumentException {
    if (!isTvOn) {
      throw new IllegalStateException("The TV must be turned on first");
    }
    if (currentChannel + amount <= 0 || currentChannel + amount > numberOfChannels) {
      throw new IllegalArgumentException("Invalid channel when skipping " + amount + " channels.");
    }
    currentChannel = currentChannel + amount;
  }
}
